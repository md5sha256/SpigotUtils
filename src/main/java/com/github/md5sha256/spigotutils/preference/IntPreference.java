package com.github.md5sha256.spigotutils.preference;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class IntPreference {

    private final NamespacedKey key;
    private final int defaultValue;

    protected IntPreference(@NotNull Plugin plugin, @NotNull String name, int defaultValue) {
        this.key = new NamespacedKey(plugin, name);
        this.defaultValue = defaultValue;
    }

    protected boolean isSet(@NotNull PersistentDataContainer container) {
        return container.has(this.key, PersistentDataType.INTEGER);
    }

    public void setPreference(@NotNull PersistentDataContainer container, int value) {
        container.set(this.key, PersistentDataType.INTEGER, value);
    }

    public int getPreference(@NotNull PersistentDataContainer container) {
        return container.getOrDefault(this.key, PersistentDataType.INTEGER, this.defaultValue);
    }

}
