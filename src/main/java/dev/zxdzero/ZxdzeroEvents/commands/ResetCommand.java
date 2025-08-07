package dev.zxdzero.ZxdzeroEvents.commands;

import dev.zxdzero.ZxdzeroEvents.registries.CooldownRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResetCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(Component.text("Please specify a cooldown!", NamedTextColor.RED));
            return false;
        }

        String cooldown = args[0].toLowerCase();

        Player player;

        if (args.length > 1) {
            try {
                player = commandSender.getServer().getPlayer(args[1]);
            } catch (Exception ignored) {
                commandSender.sendMessage(Component.text("No such player online!", NamedTextColor.RED));
                return false;
            }
        } else if (commandSender instanceof Player) {
            player = (Player) commandSender;
        } else {
            commandSender.sendMessage(Component.text("Please specify a player!", NamedTextColor.RED));
            return false;
        }

        if (CooldownRegistry.getRegisteredCooldowns().contains(cooldown)) {
            CooldownRegistry.resetCooldown(player, cooldown);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return CooldownRegistry.getRegisteredCooldowns();
        }
        return Collections.emptyList();
    }
}
