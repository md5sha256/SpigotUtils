package com.github.md5sha256.spigotutils.sql;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BoundedNumberQuery implements NumberQuery {

    public final @NotNull Number min;
    public final @NotNull Number max;
    public final boolean inclusive;
    public final boolean exclusive;
    public final boolean and;

    private final String constraints;

    public BoundedNumberQuery(@NotNull Number min, @NotNull Number max, boolean inclusive, boolean exclusive, boolean and) {
        this.min = Objects.requireNonNull(min);
        this.max = Objects.requireNonNull(max);
        this.and = and;
        this.inclusive = inclusive;
        this.exclusive = exclusive;
        final String raw = "%s$1 %f$2 %s$3 %s$4 %f$5";
        final String operand1 = inclusive ? ">=" : ">";
        final String operand2 = exclusive ? "<=" : "<";
        final String joiner = and ? "AND" : "OR";
        this.constraints = String.format(raw, operand1, min.doubleValue(), joiner, operand2, max.doubleValue());
    }

    @Override
    public @NotNull String generateConstraints() {
        return this.constraints;
    }

    @Override
    public boolean test(final @NotNull Number number) {
        boolean minTest;
        if (inclusive) {
            minTest = min.doubleValue() >= number.doubleValue();
        } else {
            minTest = min.doubleValue() > number.doubleValue();
        }
        boolean maxTest;
        if (exclusive) {
            maxTest = number.doubleValue() <= max.doubleValue();
        } else {
            maxTest = number.doubleValue() < max.doubleValue();
        }
        if (and) {
            return minTest && maxTest;
        } else {
            return minTest || maxTest;
        }
    }

    @Override
    public @NotNull NumberQuery negate() {
        return new BoundedNumberQuery(max, min, this.inclusive, this.exclusive, !this.and);
    }

}
