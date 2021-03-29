package com.github.md5sha256.spigotutils;

import com.github.md5sha256.spigotutils.builder.ItemSelector;
import com.github.md5sha256.spigotutils.builder.StagedBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.github.md5sha256.spigotutils.concurrent.BukkitTaskSynchronizer;
import com.github.md5sha256.spigotutils.concurrent.TaskSynchronizer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class UtilModule extends AbstractModule {

    private static final Collection<Class<? extends ConfigurationSerializable>> toRegister =
            Arrays.asList(ItemSelector.class, StagedBuilder.InputData.class);

    private final Plugin plugin;

    public UtilModule(final Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin);
        for (Class<? extends ConfigurationSerializable> clazz : toRegister) {
            ConfigurationSerialization.registerClass(clazz);
        }
    }

    @Override
    protected void configure() {
        bind(TaskSynchronizer.class).toInstance(BukkitTaskSynchronizer.create(plugin));
        bind(BukkitAudiences.class).toInstance(BukkitAudiences.create(plugin));
        bind(BungeeComponentSerializer.class).toProvider(BungeeComponentSerializer::get);
        bind(LegacyComponentSerializer.class).toProvider(LegacyComponentSerializer::legacyAmpersand);

        install(new FactoryModuleBuilder().build(UtilityFactory.class));
    }

}
