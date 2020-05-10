package dev.weary.items;

import dev.weary.PlayerDetectorPlugin;
import dev.weary.config.Settings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static dev.weary.config.Setting.*;

class ScoutingDeviceItem extends CustomItem {
    protected double maxRadius;
    protected double minRadius;
    protected double nearbyRadius;
    protected String peopleDetectedMessage;
    protected String nobodyDetectedMessage;
    protected String notifyRevealedMessage;
    protected String[] cardinalDirections;

    ScoutingDeviceItem() {
        this.setCustomId("scouting_device");
        this.setMaterial(Settings.getMaterial(SCOUTING_DEVICE_MATERIAL));
        this.setDisplayName(Settings.getColoredString(SCOUTING_DEVICE_NAME));
        this.setLore(Settings.getLore(SCOUTING_DEVICE_LORE));
        this.setCustomModelData(Settings.getInteger(SCOUTING_DEVICE_MODEL));

        this.maxRadius = Settings.getDouble(SCOUTING_DEVICE_MAX_RADIUS);
        this.minRadius = Settings.getDouble(SCOUTING_DEVICE_MIN_RADIUS);
        this.nearbyRadius = Settings.getDouble(SCOUTING_DEVICE_NEARBY_RADIUS);

        this.peopleDetectedMessage = Settings.getColoredString(SCOUTING_DEVICE_PEOPLE_DETECTED);
        this.nobodyDetectedMessage = Settings.getColoredString(SCOUTING_DEVICE_NOBODY_DETECTED);
        this.notifyRevealedMessage = Settings.getColoredString(SCOUTING_DEVICE_NOTIFY_REVEALED);

        this.cardinalDirections = new String[] {
            Settings.getString(DIRECTION_N),
            Settings.getString(DIRECTION_NE),
            Settings.getString(DIRECTION_E),
            Settings.getString(DIRECTION_SE),
            Settings.getString(DIRECTION_S),
            Settings.getString(DIRECTION_SW),
            Settings.getString(DIRECTION_W),
            Settings.getString(DIRECTION_NW)
        };
    }

    Player getClosestPlayer(Player player, double minRadius, double maxRadius) {
        Server server = PlayerDetectorPlugin.instance.getServer();
        Location playerLocation = player.getLocation();
        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        for (Player otherPlayer: server.getOnlinePlayers()) {
            if (!player.equals(otherPlayer) && player.getWorld().equals(otherPlayer.getWorld())) {
                double distance = playerLocation.distance(otherPlayer.getLocation());
                if (distance >= minRadius && distance <= maxRadius && distance < closestDistance) {
                    closestDistance = distance;
                    closestPlayer = otherPlayer;
                }
            }
        }

        return closestPlayer;
    }

    ArrayList<Player> getNearbyPlayers(Player player, double maxRadius) {
        Server server = PlayerDetectorPlugin.instance.getServer();
        Location playerLocation = player.getLocation();
        ArrayList<Player> nearbyPlayers = new ArrayList<>();

        for (Player otherPlayer: server.getOnlinePlayers()) {
            if (player.getWorld().equals(otherPlayer.getWorld())) {
                double distance = playerLocation.distance(otherPlayer.getLocation());
                if (distance <= maxRadius) {
                    nearbyPlayers.add(otherPlayer);
                }
            }
        }

        return nearbyPlayers;
    }

    void depleteHeldStackSize(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        int heldSlot = player.getInventory().getHeldItemSlot();
        ItemStack heldItem = player.getInventory().getItem(heldSlot);

        if (heldItem != null) {
            if (heldItem.getAmount() > 1) {
                heldItem.setAmount(heldItem.getAmount() - 1);
                player.getInventory().setItem(heldSlot, heldItem);
            }
            else {
                player.getInventory().setItem(heldSlot, null);
            }

            Location location = player.getEyeLocation();
            player.getWorld().playSound(location, Sound.ENTITY_ITEM_BREAK, 0.5F, 1.0F);
            player.getWorld().spawnParticle(Particle.ITEM_CRACK, location.add(location.getDirection()), 50, 0.3, 0.5, 0.3, 0, heldItem);
        }
    }

    void putHeldMaterialOnCooldown(Player player, int ticks) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        ItemStack heldItem = player.getInventory().getItem(heldSlot);

        if (heldItem != null) {
            player.setCooldown(heldItem.getType(), ticks);
        }
    }

    double angleBetween(Location a, Location b) {
        final double deltaX = (b.getX() - a.getX());
        final double deltaZ = (a.getZ() - b.getZ());
        final double result = Math.toDegrees(Math.atan2(deltaX, deltaZ));
        return (result < 0) ? (360d + result) : result;
    }

    String getCardinalDirection(double angle) {
        return cardinalDirections[(int)Math.round(((angle % 360) / 45)) % 8];
    }

    void detectAndNotifyPlayers(Player player) {
        Player closestPlayer = getClosestPlayer(player, this.minRadius, this.maxRadius);
        if (closestPlayer == null) {
            player.sendMessage(this.nobodyDetectedMessage);
        }
        else {
            ArrayList<Player> revealedPlayers = getNearbyPlayers(closestPlayer, this.nearbyRadius);

            int numberOfPeople = revealedPlayers.size();
            if (numberOfPeople > 0) {
                for (Player revealedPlayer: revealedPlayers) {
                    revealedPlayer.sendMessage(this.notifyRevealedMessage);
                }

                double angleBetweenPlayers = angleBetween(player.getLocation(), closestPlayer.getLocation());
                String cardinalDirection = getCardinalDirection(angleBetweenPlayers);
                String formattedMessage = String.format(this.peopleDetectedMessage, numberOfPeople, cardinalDirection);
                player.sendMessage(formattedMessage);
            }
            else {
                player.sendMessage(this.nobodyDetectedMessage);
            }
        }
    }

    @Override
    protected void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        detectAndNotifyPlayers(player);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0f, 0.85f);
        putHeldMaterialOnCooldown(player, 40);
        depleteHeldStackSize(player);
    }
}
