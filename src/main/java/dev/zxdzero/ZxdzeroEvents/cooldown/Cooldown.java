package dev.zxdzero.ZxdzeroEvents.cooldown;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {
    private static final Map<UUID, Map<NamespacedKey, Long>> COOLDOWN = new HashMap<>();

    public static void setCooldown(Player player, ItemStack item, long duration) {
        UUID uuid = player.getUniqueId();
        COOLDOWN.putIfAbsent(uuid, new HashMap<>());
        COOLDOWN.get(uuid).put(item.getType().getKey(), System.currentTimeMillis() + duration);
    }

    public static long getCooldown(Player player, ItemStack item) {
        UUID uuid = player.getUniqueId();
        COOLDOWN.putIfAbsent(uuid, new HashMap<>());
        return Math.max(0, COOLDOWN.get(uuid).getOrDefault(item.getType().getKey(), 0L) - System.currentTimeMillis());
    }

    public static boolean hasCooldown(Player player, ItemStack item) {
        UUID uuid = player.getUniqueId();
        COOLDOWN.putIfAbsent(uuid, new HashMap<>());
        return System.currentTimeMillis() < COOLDOWN.get(uuid).getOrDefault(item.getType().getKey(), 0L);
    }
}

