package dev.weary;

import co.aikar.commands.PaperCommandManager;
import dev.weary.commands.DetectorCommand;
import dev.weary.config.Settings;
import dev.weary.items.CustomItemManager;
import dev.weary.listeners.ItemCraftListener;
import dev.weary.listeners.PlayerInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDetectorPlugin extends JavaPlugin {

    public static PlayerDetectorPlugin instance;

    public PlayerDetectorPlugin() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Settings.initializeSettings();
        CustomItemManager.initializeItems();
        this.getServer().getPluginManager().registerEvents(new ItemCraftListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new DetectorCommand());
    }
}
