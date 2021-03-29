package com.github.md5sha256.spigotutils.serial;

import com.google.common.base.Stopwatch;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SerializableStopwatch implements ConfigurationSerializable {

    private static final String LAST_CHECK_KEY = "last-check-millis", ELAPSED_KEY = "elapsed", RUNNING_KEY = "is-running";

    private long elapsed;
    private long lastCheckMillis = 0;
    private boolean running;

    public SerializableStopwatch() {
    }

    public SerializableStopwatch(@NotNull final Stopwatch stopwatch) {
        this.elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.running = stopwatch.isRunning();
    }

    public SerializableStopwatch(@NotNull final Map<String, Object> serial) {
        checkKeys(serial);
        this.elapsed = ((Number) serial.get(ELAPSED_KEY)).longValue();
        this.lastCheckMillis = ((Number) serial.get(LAST_CHECK_KEY)).longValue();
        this.running = (boolean) serial.get(RUNNING_KEY);
    }

    public SerializableStopwatch(final SerializableStopwatch other) {
        this.elapsed = other.elapsed;
        this.lastCheckMillis = other.lastCheckMillis;
        this.running = other.running;
    }

    public SerializableStopwatch start() {
        elapsed = 0;
        lastCheckMillis = System.currentTimeMillis();
        running = true;
        return this;
    }

    public boolean isRunning() {
        return running;
    }

    public SerializableStopwatch pause() {
        running = false;
        return this;
    }

    public long elapsed(final TimeUnit timeUnit) {
        if (isRunning()) {
           update();
        }
        return timeUnit.convert(this.elapsed, TimeUnit.MILLISECONDS);
    }

    public long rawElapsed(final TimeUnit timeUnit) {
        return timeUnit.convert(this.elapsed, TimeUnit.MILLISECONDS);
    }

    public void update() {
        this.elapsed += System.currentTimeMillis() - lastCheckMillis;
        this.lastCheckMillis = System.currentTimeMillis();
    }

    public long getMillisSinceLastUpdate() {
        return System.currentTimeMillis() - lastCheckMillis;
    }

    public SerializableStopwatch reset() {
        this.elapsed = 0;
        this.running = false;
        this.lastCheckMillis = 0;
        return this;
    }

    private static void checkKeys(@NotNull final Map<String, Object> serial) {
        if (!serial.containsKey(LAST_CHECK_KEY) || !serial.containsKey(ELAPSED_KEY) || !serial.containsKey(RUNNING_KEY)) {
            throw new IllegalArgumentException("Invalid Serial!");
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();
        map.put(LAST_CHECK_KEY, lastCheckMillis);
        map.put(ELAPSED_KEY, elapsed(TimeUnit.MILLISECONDS));
        map.put(RUNNING_KEY, running);
        return map;
    }
}
