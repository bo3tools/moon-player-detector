package dev.weary.listeners;

import dev.weary.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class ItemCraftListener implements Listener {

    private boolean hasCustomItem(ItemStack[] items) {
        for (ItemStack item: items) {
            if (item != null && CustomItem.hasCustomIdentifier(item)) {
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onItemCraftPrepared(PrepareItemCraftEvent event) {
        final ItemStack[] usedItems = event.getInventory().getMatrix();
        if (hasCustomItem(usedItems)) {
            event.getInventory().setResult(null);
        }
    }

    @EventHandler
    public void onItemCrafted(CraftItemEvent event) {
        final ItemStack[] usedItems = event.getInventory().getMatrix();
        if (hasCustomItem(usedItems)) {
            event.getInventory().setResult(null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnvilPrepared(PrepareAnvilEvent event) {
        final ItemStack[] usedItems = event.getInventory().getContents();
        if (hasCustomItem(usedItems)) {
            event.getInventory().setRepairCost(event.getInventory().getMaximumRepairCost() + 1);
            event.setResult(null);
        }
    }
}