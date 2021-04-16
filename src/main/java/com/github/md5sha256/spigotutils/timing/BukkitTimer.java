package com.github.md5sha256.spigotutils.timing;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BukkitTimer<K, V> extends AbstractTimer<K, V> {

    private final AtomicReference<BukkitTask> task = new AtomicReference<>();

    public BukkitTimer(
            @NotNull Plugin plugin,
            @NotNull BukkitScheduler scheduler,
            boolean async,
            long intervalTicks,
            @NotNull TimerListener<K, V> listener
    ) {
        this(plugin, scheduler, async, intervalTicks, listener, Collections.emptyMap());
    }

    public BukkitTimer(
            @NotNull Plugin plugin,
            @NotNull BukkitScheduler scheduler,
            boolean async,
            long intervalTicks,
            @NotNull TimerListener<K, V> listener,
            @NotNull Map<K, V> entries
    ) {
        super(listener, entries);
        final BukkitTask bukkitTask;
        if (async) {
            bukkitTask = scheduler.runTaskTimerAsynchronously(plugin, this::update, intervalTicks, intervalTicks);
        } else {
            bukkitTask = scheduler.runTaskTimer(plugin, this::update, intervalTicks, intervalTicks);
        }
        this.task.set(bukkitTask);
    }

    protected abstract void update();

    @Override
    public boolean isShutdown() {
        final BukkitTask actualTask = this.task.get();
        return actualTask != null && !actualTask.isCancelled();
    }

    @Override
    public void shutdown() {
        final BukkitTask old = this.task.getAndSet(null);
        if (old != null) {
            old.cancel();
        }
    }

}
