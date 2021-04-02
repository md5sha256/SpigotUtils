package com.github.md5sha256.spigotutils.sql;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ExactNumberQuery implements NumberQuery {

    public final @NotNull Number value;

    public final @NotNull ArithmeticComparator comparator;

    private final String contraint;

    public ExactNumberQuery(@NotNull Number value, @NotNull ArithmeticComparator comparator) {
        this.value = Objects.requireNonNull(value);
        this.comparator = Objects.requireNonNull(comparator);
        this.contraint = String.format("%s %f", comparator.operand, value.doubleValue());
    }

    @Override
    public @NotNull String generateConstraints() {
        return this.contraint;
    }

    @Override
    public boolean test(@NotNull Number number) {
        final double val = value.doubleValue();
        final double target = number.doubleValue();
        switch (comparator) {
            case GREATER:
                return val > target;
            case GREATER_OR_EQUAL:
                return val >= target;
            case LESS:
                return val < target;
            case LESS_OR_EQUAL:
                return val <= target;
            case EQUAL:
                return val == target;
            default:
                throw new IllegalStateException("Unknown comparator: " + comparator);
        }
    }

    @Override
    public @NotNull NumberQuery negate() {
        return new ExactNumberQuery(this.value, this.comparator.negate());
    }

}
