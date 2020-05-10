package dev.weary.listeners;

import dev.weary.items.CustomItem;
import dev.weary.items.CustomItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        ItemStack itemInHand = event.getItem();

        boolean hasRightClicked = (action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK);
        boolean hasCustomItem = itemInHand != null && CustomItem.hasCustomIdentifier(itemInHand);

        if (hasRightClicked && hasCustomItem) {
            CustomItem customItem = CustomItemManager.getItem(itemInHand);
            customItem.activateItem(event);
        }
    }
}
