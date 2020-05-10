package dev.weary.items;

import dev.weary.PlayerDetectorPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class CustomItem {
    private static final String CUSTOM_ITEM_TAG = "custom_item";
    private static final NamespacedKey customItemKey = new NamespacedKey(PlayerDetectorPlugin.instance, CUSTOM_ITEM_TAG);

    private String itemIdentifier;
    private Material material;
    private String displayName;
    private Integer customModelData;
    private List<String> lore;

    void setMaterial(Material material) {
        this.material = material;
    }

    String getDisplayName() {
        return displayName;
    }

    void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    Integer getCustomModelData() {
        return customModelData;
    }

    void setCustomModelData(Integer customModelData) {
        this.customModelData = customModelData;
    }

    List<String> getLore() {
        return lore;
    }

    void setLore(List<String> lore) {
        this.lore = lore;
    }

    String getCustomId() {
        return this.itemIdentifier;
    }

    void setCustomId(String itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    Material getMaterial() {
        return this.material;
    }

    public ItemStack createNewItem() {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.displayName);
        itemMeta.setLore(this.lore);
        itemMeta.setCustomModelData(this.customModelData);
        itemMeta.getPersistentDataContainer().set(customItemKey, PersistentDataType.STRING, this.itemIdentifier);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    protected abstract void onActivate(PlayerInteractEvent event);

    public void activateItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasCooldown(this.getMaterial())) {
            this.onActivate(event);
        }
    }

    public static boolean hasCustomIdentifier(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().has(customItemKey, PersistentDataType.STRING);
    }

    public static String getCustomIdentifier(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(customItemKey, PersistentDataType.STRING);
    }
}