package com.github.md5sha256.spigotutils.tuple;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Pair<K, V> {

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
