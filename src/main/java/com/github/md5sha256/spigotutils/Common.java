package com.github.md5sha256.spigotutils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class Common {

    public static final Pattern HEX_PATTERN = Pattern.compile("(#)[0-9A-F]{6}");
    public static final Pattern LEGACY_PATTERN = Pattern.compile("&[0-9-A-F]}");

    public static String legacyColorise(@NotNull final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @SuppressWarnings("unchecked")
    public static <T> T unsafeCast(Object o) {
        return (T) o;
    }

    public static long toTicks(final long duration, @NotNull final TimeUnit unit) {
        return unit.toMillis(duration) / 50;
    }

    public static String capitalise(final String string) {
        if (string.length() <= 1) {
            return string.toUpperCase();
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String titleCase(final String string, final String delimiter) {
        final StringJoiner joiner = new StringJoiner(delimiter);
        for (final String s : string.split(delimiter)) {
            joiner.add(capitalise(s));
        }
        return joiner.toString();
    }

    public static boolean isLegacyText(@NotNull final String text) {
        return LEGACY_PATTERN.matcher(text).matches();
    }

    public static boolean isHexText(@NotNull final String text) {
        return HEX_PATTERN.matcher(text).matches();
    }

    /**
     * Format a string. Replacements come in pairs of two, where
     * the first value is the string to be replaced, and the second
     * value is the replacement, example:
     * %key1%, value1, %key2%, value2
     *
     * @param message      String to format
     * @param replacements Replacements, needs to be a multiple of 2
     * @return The formatted string
     */
    @NotNull
    public static String format(@NotNull final String message,
                                @NotNull final String... replacements) {
        if (replacements.length % 2!=0) {
            throw new IllegalArgumentException(
                    "Replacement length of " + replacements.length + "  is not a multiple of two");
        }
        String replacedMessage = Objects.requireNonNull(message);
        for (int i = 0; i < replacements.length; i += 2) {
            replacedMessage = replacedMessage.replace(replacements[i], replacements[i + 1]);
        }
        return replacedMessage;
    }
}
