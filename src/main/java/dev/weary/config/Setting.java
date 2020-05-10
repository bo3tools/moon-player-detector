package dev.weary.config;

public enum Setting {
    DIRECTION_N("direction.n", "north"),
    DIRECTION_NE("direction.ne", "north-east"),
    DIRECTION_E("direction.e", "east"),
    DIRECTION_SE("direction.se", "south-east"),
    DIRECTION_S("direction.s", "south"),
    DIRECTION_SW("direction.sw", "south-west"),
    DIRECTION_W("direction.w", "west"),
    DIRECTION_NW("direction.nw", "north-west"),

    SCOUTING_DEVICE_MATERIAL("basic-scouting-device.item.material", "minecraft:brick"),
    SCOUTING_DEVICE_NAME("basic-scouting-device.item.name", "&rScouting Device"),
    SCOUTING_DEVICE_LORE("basic-scouting-device.item.lore", "&7Detects presence of\n&7nearby players."),
    SCOUTING_DEVICE_MODEL("basic-scouting-device.item.model", 1337),
    SCOUTING_DEVICE_NOTIFY_REVEALED("basic-scouting-device.message.notify-revealed", "&cYou feel as though you are being watched."),
    SCOUTING_DEVICE_PEOPLE_DETECTED("basic-scouting-device.message.people-detected", "&eYou detect %d people %s from here."),
    SCOUTING_DEVICE_NOBODY_DETECTED("basic-scouting-device.message.nobody-detected", "&eYou detect nobody nearby."),
    SCOUTING_DEVICE_MAX_RADIUS("basic-scouting-device.max-radius", 250),
    SCOUTING_DEVICE_MIN_RADIUS("basic-scouting-device.min-radius", 20),
    SCOUTING_DEVICE_NEARBY_RADIUS("basic-scouting-device.nearby-radius", 30),

    SUPER_SCOUTING_DEVICE_MATERIAL("super-scouting-device.item.material", "minecraft:nether_brick"),
    SUPER_SCOUTING_DEVICE_NAME("super-scouting-device.item.name", "&rSuper Scouting Device"),
    SUPER_SCOUTING_DEVICE_LORE("super-scouting-device.item.lore", "&7Detects presence of\n&7players far away."),
    SUPER_SCOUTING_DEVICE_MODEL("super-scouting-device.item.model", 1337),
    SUPER_SCOUTING_DEVICE_NOTIFY_REVEALED("super-scouting-device.message.notify-revealed", "&cYou feel as though you are being watched from afar."),
    SUPER_SCOUTING_DEVICE_PEOPLE_DETECTED("super-scouting-device.message.people-detected", "&eYou detect %d people far %s from here."),
    SUPER_SCOUTING_DEVICE_NOBODY_DETECTED("super-scouting-device.message.nobody-detected", "&eThere's nobody around for miles."),
    SUPER_SCOUTING_DEVICE_MAX_RADIUS("super-scouting-device.max-radius", 2000),
    SUPER_SCOUTING_DEVICE_MIN_RADIUS("super-scouting-device.min-radius", 50),
    SUPER_SCOUTING_DEVICE_NEARBY_RADIUS("super-scouting-device.nearby-radius", 100),
    SUPER_SCOUTING_DEVICE_MAX_USES("super-scouting-device.max-uses", 5),
    SUPER_SCOUTING_DEVICE_USES_SUFFIX("super-scouting-device.uses-suffix", "&r (%d/%d)");

    private String yamlName;
    private Object defaultValue;

    Setting(String yamlName, Object defaultValue) {
        this.yamlName = yamlName;
        this.defaultValue = defaultValue;
    }

    String getYamlName() {
        return yamlName;
    }

    Object getDefaultValue() {
        return defaultValue;
    }
}
