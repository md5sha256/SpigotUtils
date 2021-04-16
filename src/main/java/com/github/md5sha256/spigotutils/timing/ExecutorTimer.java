package com.github.md5sha256.spigotutils.timing;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ExecutorTimer<K, V> extends AbstractTimer<K, V> {

    private final ScheduledExecutorService executor;

    public ExecutorTimer(
            @NotNull ScheduledExecutorService executor,
            long interval,
            @NotNull TimeUnit timeUnit,
            @NotNull TimerListener<K, V> listener
    ) {
        this(executor, interval, timeUnit, listener, Collections.emptyMap());
    }

    public ExecutorTimer(
            @NotNull ScheduledExecutorService executor,
            long interval,
            @NotNull TimeUnit timeUnit,
            @NotNull TimerListener<K, V> listener,
            @NotNull Map<K, V> entries
    ) {
        super(listener, entries);
        this.executor = executor;
        this.executor.scheduleWithFixedDelay(this::update, interval, interval, timeUnit);
    }

    protected abstract void update();

    @Override
    public void shutdown() {
        this.executor.shutdownNow();
        super.clear();
    }

    @Override
    public boolean isShutdown() {
        return this.executor.isShutdown();
    }

}
