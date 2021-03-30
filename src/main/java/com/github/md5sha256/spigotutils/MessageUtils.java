package com.github.md5sha256.spigotutils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Singleton
public class MessageUtils {

    public final TextColor colorDebug = NamedTextColor.WHITE;
    public final TextColor colorInfo = NamedTextColor.BLUE;
    public final TextColor colorWarning = NamedTextColor.YELLOW;
    public final TextColor colorError = NamedTextColor.RED;

    @Inject
    private BukkitAudiences audiences;

    public void debug(@NotNull Audience audience, @NotNull String... messages) {
        sendMessage(colorDebug, audience, messages);
    }

    public void debug(@NotNull CommandSender sender, @NotNull String... messages) {
        debug(audiences.sender(sender), messages);
    }

    public void info(@NotNull CommandSender sender, @NotNull String... messages) {
        info(audiences.sender(sender), messages);
    }

    public void info(@NotNull Audience audience, @NotNull String... messages) {
        sendMessage(colorInfo, audience, messages);
    }

    public void warning(@NotNull Audience audience, @NotNull String... messages) {
        sendMessage(colorWarning, audience, messages);
    }

    public void warning(@NotNull CommandSender sender, @NotNull String... messages) {
        warning(audiences.sender(sender), messages);
    }

    public void error(@NotNull CommandSender sender, @NotNull String... messages) {
        error(audiences.sender(sender), messages);
    }

    public void error(@NotNull Audience audience, @NotNull String... messages) {
        sendMessage(colorError, audience, messages);
    }

    private void sendMessage(@NotNull TextColor color, @NotNull Audience audience, @NotNull String... messages) {
        for (String message : messages) {
            final Component component = Component.text(message).colorIfAbsent(color);
            audience.sendMessage(Identity.nil(), component);
        }
    }

}

