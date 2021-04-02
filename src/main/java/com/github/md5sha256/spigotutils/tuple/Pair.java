package com.github.md5sha256.spigotutils.tuple;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public final class Pair<K, V> {

    @SafeVarargs
    public static <K, V> Map<K, V> collect(@NotNull Supplier<? extends Map<K, V>> mapSupplier,
                                           @NotNull Pair<K, V>... pairs) {
        final Map<K, V> map = mapSupplier.get();
        collect(map, pairs);
        return map;
    }

    @SafeVarargs
    public static <K, V> void collect(@NotNull Map<K, V> map, @NotNull Pair<K, V>... pairs) {
        for (Pair<K, V> pair : pairs) {
            map.put(pair.primary, pair.secondary);
        }
    }

    private final @Nullable K primary;
    private final @Nullable V secondary;

    private Pair(@Nullable K primary, @Nullable V secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public static <K, V> @NotNull Pair<@Nullable K, @Nullable V> of(@Nullable K primary,
                                                                    @Nullable V secondary) {
        return new Pair<>(primary, secondary);
    }


    public @Nullable K primary() {
        return this.primary;
    }

    @Contract("!null -> !null")
    public K primary(K def) {
        return this.primary == null ? def:this.primary;
    }

    public @Nullable V secondary() {
        return this.secondary;
    }

    @Contract("!null -> !null")
    public V secondary(V def) {
        return this.secondary == null ? def:this.secondary;
    }

}
