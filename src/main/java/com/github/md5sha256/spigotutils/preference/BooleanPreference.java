package com.github.md5sha256.spigotutils.preference;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BooleanPreference {

    private final NamespacedKey key;
    private final boolean defaultValue;

    protected BooleanPreference(@NotNull Plugin plugin, @NotNull String name, boolean defaultValue) {
        this.key = new NamespacedKey(plugin, name);
        this.defaultValue = defaultValue;
    }

    protected static byte toByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }

    protected boolean isSet(@NotNull PersistentDataContainer container) {
        return container.has(this.key, PersistentDataType.BYTE);
    }

    protected boolean isSet(@NotNull PersistentDataHolder holder) {
        return isSet(holder.getPersistentDataContainer());
    }

    public void setPreference(@NotNull PersistentDataContainer container, boolean value) {
        container.set(this.key, PersistentDataType.BYTE, toByte(value));
    }

    public void setPreference(@NotNull PersistentDataHolder holder, boolean value) {
        setPreference(holder.getPersistentDataContainer(), value);
    }

    public boolean getPreference(@NotNull PersistentDataContainer container) {
        if (!isSet(container)) {
            return this.defaultValue;
        }
        return container.getOrDefault(this.key, PersistentDataType.BYTE, toByte(defaultValue)) == 1;
    }

    public boolean getPreference(@NotNull PersistentDataHolder holder) {
        return getPreference(holder.getPersistentDataContainer());
    }

}
