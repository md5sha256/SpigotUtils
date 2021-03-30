package com.github.md5sha256.spigotutils.module;

import com.github.md5sha256.spigotutils.concurrent.BukkitTaskSynchronizer;
import com.github.md5sha256.spigotutils.concurrent.TaskSynchronizer;
import com.github.md5sha256.spigotutils.logging.ILogger;
import com.github.md5sha256.spigotutils.logging.PluginLoggerAdapter;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ModuleOptionsBuilder {

    private final Plugin plugin;
    private TaskSynchronizer taskSynchronizer;
    private ILogger logger;

    ModuleOptionsBuilder(@NotNull Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin cannot be null!");
    }

    public @NotNull ModuleOptionsBuilder taskSynchronizer(@NotNull TaskSynchronizer taskSynchronizer) {
        this.taskSynchronizer = taskSynchronizer;
        return this;
    }

    public @NotNull ModuleOptionsBuilder logger(@NotNull ILogger logger) {
        this.logger = logger;
        return this;
    }

    private void validate() throws IllegalArgumentException {
        if (this.taskSynchronizer == null) {
            this.taskSynchronizer = BukkitTaskSynchronizer.create(plugin);
        }
        if (this.logger == null) {
            this.logger = PluginLoggerAdapter.adapt(this.taskSynchronizer, this.plugin.getLogger());
        }
    }

    public @NotNull ModuleOptions build() {
        validate();
        return new ModuleOptions(this.plugin, this.taskSynchronizer, this.logger);
    }

}
