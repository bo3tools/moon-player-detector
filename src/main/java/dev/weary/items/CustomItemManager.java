package dev.weary.items;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public final class CustomItemManager {
    private static HashMap<String, CustomItem> customItems = new HashMap<>();

    public static void initializeItems() {
        registerItem(new ScoutingDeviceItem());
        registerItem(new SuperScoutingDeviceItem());
    }

    public static CustomItem getItem(String itemIdentifier) {
        return customItems.get(itemIdentifier);
    }

    public static CustomItem getItem(ItemStack itemStack) {
        String itemIdentifier = CustomItem.getCustomIdentifier(itemStack);
        return getItem(itemIdentifier);
    }

    private static void registerItem(CustomItem customItem) {
        String itemIdentifier = customItem.getCustomId();
        if (itemIdentifier == null) {
            throw new CustomItemException("Can't register a custom item without an identifier");
        }
        else if (customItems.containsKey(itemIdentifier)) {
            throw new CustomItemException("Custom item with identifier " + itemIdentifier + " was already registered");
        }

        customItems.put(itemIdentifier, customItem);
    }
}
