package com.github.md5sha256.spigotutils.timing;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Timer<K, V> {

    default boolean containsKey(@NotNull K key) {
        return data(key).isPresent();
    }

    @NotNull Optional<@NotNull TimerData<V>> data(@NotNull K key);

    @NotNull TimerData<V> submit(@NotNull K key, @NotNull V value);

    @NotNull Map<@NotNull K, @NotNull TimerData<V>> submitEntries(@NotNull Map<K, V> entries);

    void remove(@NotNull K key);

    void removeEntries(@NotNull Collection<@NotNull K> keys);

    @NotNull Set<@NotNull K> keys();

    @NotNull Collection<V> values();

    @NotNull Map<@NotNull K, @NotNull TimerData<V>> asMap();

    void resetValues();

    int size();

    void clear();

    void shutdown();

    boolean isShutdown();

}
