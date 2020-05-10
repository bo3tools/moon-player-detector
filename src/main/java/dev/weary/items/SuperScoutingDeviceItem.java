package dev.weary.items;

import dev.weary.PlayerDetectorPlugin;
import dev.weary.config.Settings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static dev.weary.config.Setting.*;

public class SuperScoutingDeviceItem extends ScoutingDeviceItem {
    protected static final String ITEM_CHARGES_TAG = "charges_remaining";
    protected static final NamespacedKey itemChargesKey = new NamespacedKey(PlayerDetectorPlugin.instance, ITEM_CHARGES_TAG);

    protected String displayNameTemplate;
    protected int initialCharges;

    public SuperScoutingDeviceItem() {
        this.setCustomId("super_scouting_device");
        this.setMaterial(Settings.getMaterial(SUPER_SCOUTING_DEVICE_MATERIAL));
        this.setLore(Settings.getLore(SUPER_SCOUTING_DEVICE_LORE));
        this.setCustomModelData(Settings.getInteger(SUPER_SCOUTING_DEVICE_MODEL));

        this.maxRadius = Settings.getDouble(SUPER_SCOUTING_DEVICE_MAX_RADIUS);
        this.minRadius = Settings.getDouble(SUPER_SCOUTING_DEVICE_MIN_RADIUS);
        this.nearbyRadius = Settings.getDouble(SUPER_SCOUTING_DEVICE_NEARBY_RADIUS);

        this.peopleDetectedMessage = Settings.getColoredString(SUPER_SCOUTING_DEVICE_PEOPLE_DETECTED);
        this.nobodyDetectedMessage = Settings.getColoredString(SUPER_SCOUTING_DEVICE_NOBODY_DETECTED);
        this.notifyRevealedMessage = Settings.getColoredString(SUPER_SCOUTING_DEVICE_NOTIFY_REVEALED);

        this.initialCharges = Settings.getInteger(SUPER_SCOUTING_DEVICE_MAX_USES);
        this.displayNameTemplate = Settings.getColoredString(SUPER_SCOUTING_DEVICE_NAME).replaceAll("%", "%%") +
                                   Settings.getColoredString(SUPER_SCOUTING_DEVICE_USES_SUFFIX);
    }

    private void depleteHeldCustomCharge(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        int heldSlot = player.getInventory().getHeldItemSlot();
        ItemStack heldItem = player.getInventory().getItem(heldSlot);

        if (heldItem != null) {
            if (heldItem.getAmount() > 1) {
                ItemStack remainingItems = heldItem.clone();
                remainingItems.setAmount(remainingItems.getAmount() - 1);
                heldItem.setAmount(1);

                int firstEmptySlot = player.getInventory().firstEmpty();

                if (firstEmptySlot == -1) {
                    player.getWorld().dropItem(player.getLocation().add(0, 1, 0), remainingItems);
                }
                else {
                    player.getInventory().setItem(firstEmptySlot, remainingItems);
                }
            }

            int chargesRemaining = getChargesRemaining(heldItem);
            if (chargesRemaining > 1) {
                setChargesRemaining(heldItem, chargesRemaining - 1);
            }
            else {
                player.getInventory().setItem(heldSlot, null);
                Location location = player.getEyeLocation();
                player.getWorld().playSound(location, Sound.ENTITY_ITEM_BREAK, 0.5F, 1.0F);
                player.getWorld().spawnParticle(Particle.ITEM_CRACK, location.add(location.getDirection()), 50, 0.3, 0.5, 0.3, 0, heldItem);
            }
        }
    }


    @Override
    protected void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        detectAndNotifyPlayers(player);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0f, 0.85f);
        putHeldMaterialOnCooldown(player,60);
        depleteHeldCustomCharge(player);
    }

    private void setChargesRemaining(ItemStack itemStack, int chargesRemaining) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemChargesKey, PersistentDataType.INTEGER, chargesRemaining);
        itemMeta.setDisplayName(String.format(displayNameTemplate, chargesRemaining, initialCharges));
        itemStack.setItemMeta(itemMeta);
    }

    private int getChargesRemaining(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().getOrDefault(itemChargesKey, PersistentDataType.INTEGER, initialCharges);
    }

    @Override
    public ItemStack createNewItem() {
        ItemStack customItem = super.createNewItem();
        setChargesRemaining(customItem, initialCharges);
        return customItem;
    }
}
