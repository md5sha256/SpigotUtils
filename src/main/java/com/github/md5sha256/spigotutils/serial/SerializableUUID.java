package com.github.md5sha256.spigotutils.serial;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class SerializableUUID implements ConfigurationSerializable {

    private final UUID value;

    public SerializableUUID(@NotNull final UUID uuid) {
        this.value = uuid;
    }

    public SerializableUUID(@NotNull final Map<String, Object> serial) {
        if (!serial.containsKey("UUID")) {
            throw new IllegalArgumentException("Invalid UUID!");
        }
        this.value = UUID.fromString((String) serial.get("UUID"));
    }

    public UUID getValue() {
        return value;
    }

    @Override public @NotNull Map<String, Object> serialize() {
        return Collections.singletonMap("UUID", value);
    }
}
