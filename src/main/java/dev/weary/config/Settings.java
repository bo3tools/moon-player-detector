package dev.weary.config;

import dev.weary.PlayerDetectorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class Settings {
    private static String CONFIG_FILE = "config.yml";
    private static YamlConfiguration config;

    private static Object getPropertyOrDefault(Setting setting) {
        Object property = config.get(setting.getYamlName());
        if (property == null) {
            return setting.getDefaultValue();
        }

        return property;
    }

    public static String getString(Setting setting) {
        return (String) getPropertyOrDefault(setting);
    }

    public static Double getDouble(Setting setting) {
        return ((Number) getPropertyOrDefault(setting)).doubleValue();
    }

    public static Integer getInteger(Setting setting) {
        return ((Number) getPropertyOrDefault(setting)).intValue();
    }

    public static String getColoredString(Setting setting) {
        String formattedString = getString(setting);
        return ChatColor.RESET + formattedString.replaceAll("&([a-f0-9klmnor])", "§$1");
    }

    public static List<String> getLore(Setting setting) {
        String coloredString = getColoredString(setting);
        return Arrays.asList(coloredString.split("\n"));
    }

    public static Material getMaterial(Setting setting) {
        String materialName = getString(setting);
        return Material.matchMaterial(materialName);
    }

    public static void initializeSettings() {
        PlayerDetectorPlugin plugin = PlayerDetectorPlugin.instance;
        File configFile = new File(plugin.getDataFolder(), CONFIG_FILE);
        config = new YamlConfiguration().options().indent(4).configuration();

        try {
            if (!configFile.exists()) {
                config.save(configFile);
            }

            config.load(configFile);

            for (Setting setting: Setting.values()) {
                if (config.get(setting.getYamlName()) == null && setting.getDefaultValue() != null) {
                    config.set(setting.getYamlName(), setting.getDefaultValue());
                }
            }

            saveConfig();
        }
        catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        PlayerDetectorPlugin plugin = PlayerDetectorPlugin.instance;
        File configFile = new File(plugin.getDataFolder(), CONFIG_FILE);

        try {
            config.save(configFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
