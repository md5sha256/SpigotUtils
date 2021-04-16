package com.github.md5sha256.spigotutils.timing;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTimer<K, V> implements Timer<K, V> {

    private final Map<K, TimerData<V>> entries = new ConcurrentHashMap<>();

    private final TimerListener<K, V> listener;

    protected AbstractTimer(@NotNull TimerListener<K, V> listener) {
        this.listener = Objects.requireNonNull(listener);
    }

    protected AbstractTimer(@NotNull TimerListener<K, V> listener, @NotNull Map<K, V> entries) {
        this(listener);
        submitEntries(entries);
    }

    protected abstract @NotNull TimerData<V> create(@NotNull K key, @NotNull V value);

    @Override
    public @NotNull Optional<@NotNull TimerData<V>> data(@NotNull final K key) {
        return Optional.ofNullable(this.entries.get(key));
    }

    @Override
    public @NotNull TimerData<V> submit(@NotNull final K key, @NotNull final V value) {
        remove(key);
        final TimerData<V> data = create(key, value);
        this.entries.put(key, data);
        return data;
    }

    @Override
    public @NotNull Map<@NotNull K, @NotNull TimerData<V>> submitEntries(@NotNull final Map<K, V> entries) {
        final Map<K, TimerData<V>> target = new HashMap<>(entries.size());
        for (Map.Entry<K, V> entry : entries.entrySet()) {
            target.put(entry.getKey(), create(entry.getKey(), entry.getValue()));
        }
        this.entries.putAll(target);
        return target;
    }

    @Override
    public void remove(@NotNull final K key) {
        final TimerData<V> data = this.entries.remove(key);
        if (data != null) {
            this.listener.onRemove(key, data);
        }
    }

    @Override
    public void removeEntries(@NotNull final Collection<@NotNull K> keys) {
        for (K key : keys) {
            remove(key);
        }
    }

    @Override
    public @NotNull Set<@NotNull K> keys() {
        return new HashSet<>(this.entries.keySet());
    }

    @Override
    public @NotNull Collection<V> values() {
        final Collection<V> values = new ArrayList<>(this.entries.values().size());
        for (TimerData<V> data : this.entries.values()) {
            values.add(data.data());
        }
        return values;
    }

    @Override
    public @NotNull Map<@NotNull K, @NotNull TimerData<V>> asMap() {
        return new HashMap<>(this.entries);
    }

    @Override
    public void resetValues() {
        for (TimerData<V> data : this.entries.values()) {
            data.elapsed().reset();
        }
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    @Override
    public void clear() {
        final Map<K, @NotNull TimerData<V>> copy = new HashMap<>(this.entries);
        this.entries.clear();
        for (Map.Entry<K, TimerData<V>> entry : copy.entrySet()) {
            this.listener.onRemove(entry.getKey(), entry.getValue());
        }
    }

}
