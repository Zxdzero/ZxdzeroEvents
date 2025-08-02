package dev.zxdzero.ZxdzeroEvents.commands;

import dev.zxdzero.ZxdzeroEvents.registries.ItemMenuRegistry;
import dev.zxdzero.ZxdzeroEvents.registries.ItemMenuRegistry.ItemMenuEntry;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class ItemsCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command!"));
            return true;
        }

        player.openInventory(ItemMenuRegistry.createMainItemsMenu());
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        // Handle main items menu
        if (event.getView().title().equals(Component.text("Items Menu"))) {
            event.setCancelled(true); // Prevent taking items

            ItemMenuEntry menuEntry = ItemMenuRegistry.findMenuByButton(event.getCurrentItem());
            if (menuEntry != null) {
                // Open the plugin's specific item menu
                player.openInventory(menuEntry.createInventory());
            }
        }
    }
}