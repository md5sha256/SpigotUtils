package com.github.md5sha256.spigotutils;


import com.github.md5sha256.spigotutils.serial.SerializableEnum;
import com.github.md5sha256.spigotutils.serial.SerializableStopwatch;
import com.github.md5sha256.spigotutils.serial.SerializableUUID;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.Arrays;
import java.util.Collection;

public class MccitiesLib {

    private static final Collection<Class<? extends ConfigurationSerializable>> classes =
            Arrays.asList(SerializableEnum.class, SerializableUUID.class, SerializableStopwatch.class);

    private static void registerSerialization() {
        for (final Class<? extends ConfigurationSerializable> clazz : classes) {
            ConfigurationSerialization.registerClass(clazz);
        }
    }

    public static void onDisable() {
    }


    public static void onEnable() {
        registerSerialization();
    }
}
