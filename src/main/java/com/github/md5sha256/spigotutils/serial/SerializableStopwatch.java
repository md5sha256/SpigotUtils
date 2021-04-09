package com.github.md5sha256.spigotutils.serial;

import com.google.common.base.Stopwatch;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SerializableStopwatch implements ConfigurationSerializable {

    private static final String ELAPSED_KEY = "elapsed", RUNNING_KEY = "is-running";

    private long elapsed = 0;
    private final Stopwatch stopwatch = Stopwatch.createUnstarted();

    public SerializableStopwatch() {
    }

    public SerializableStopwatch(long elapsed, @NotNull TimeUnit timeUnit) {
        this.elapsed = timeUnit.toMillis(elapsed);
    }

    public SerializableStopwatch(@NotNull final Stopwatch stopwatch) {
        this.elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

    public SerializableStopwatch(@NotNull final Map<String, Object> serial) {
        checkKeys(serial);
        this.elapsed = ((Number) serial.get(ELAPSED_KEY)).longValue();
        boolean running = (boolean) serial.get(RUNNING_KEY);
        if (running) {
            stopwatch.start();
        }
    }

    public SerializableStopwatch(final SerializableStopwatch other) {
        this.elapsed = other.elapsed;
        if (other.stopwatch.isRunning()) {
            this.stopwatch.start();
        }
    }

    public SerializableStopwatch start() {
        if (!this.stopwatch.isRunning()) {
            this.stopwatch.start();
        }
        return this;
    }

    public boolean isRunning() {
        return this.stopwatch.isRunning();
    }

    public SerializableStopwatch stop() {
        this.elapsed = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.stopwatch.reset();
        return this;
    }

    public long elapsed(final TimeUnit timeUnit) {
        return timeUnit.convert(elapsedMillis(), TimeUnit.MILLISECONDS);
    }

    public long elapsedMillis() {
        return this.elapsed + this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

    public SerializableStopwatch reset() {
        this.elapsed = 0;
        this.stopwatch.reset();
        return this;
    }

    private static void checkKeys(@NotNull final Map<String, Object> serial) {
        if (!serial.containsKey(ELAPSED_KEY) || !serial.containsKey(RUNNING_KEY)) {
            throw new IllegalArgumentException("Invalid Serial!");
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ELAPSED_KEY, elapsedMillis());
        map.put(RUNNING_KEY, stopwatch.isRunning());
        return map;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerializableStopwatch that = (SerializableStopwatch) o;

        return this.elapsedMillis() == that.elapsedMillis();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.elapsedMillis());
    }

}
