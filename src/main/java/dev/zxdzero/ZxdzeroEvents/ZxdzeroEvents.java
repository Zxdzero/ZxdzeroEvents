package dev.zxdzero.ZxdzeroEvents;

import dev.zxdzero.ZxdzeroEvents.commands.ItemsCommand;
import dev.zxdzero.ZxdzeroEvents.commands.PedestalCommand;
import dev.zxdzero.ZxdzeroEvents.commands.ResetCommand;
import dev.zxdzero.ZxdzeroEvents.listeners.PlayerInteractListener;
import dev.zxdzero.ZxdzeroEvents.registries.CooldownRegistry;
import dev.zxdzero.ZxdzeroEvents.registries.ItemMenuRegistry;
import dev.zxdzero.ZxdzeroEvents.registries.RecipeManager;
import dev.zxdzero.ZxdzeroEvents.registries.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZxdzeroEvents extends JavaPlugin {

    private static ZxdzeroEvents plugin;

    public static ZxdzeroEvents getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        if (!Util.sessionCheck()) return;

        plugin = this;
        PedestalManager pedestalManager = new PedestalManager();
        ItemsCommand itemsCommand = new ItemsCommand();
        CooldownRegistry.initialize(plugin);


        getServer().getPluginManager().registerEvents(pedestalManager, this);
        getServer().getPluginManager().registerEvents(itemsCommand, this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);

        if (!Util.sessionCheck()) return;
        getCommand("pedestal").setExecutor(new PedestalCommand(pedestalManager));
        getCommand("items").setExecutor(itemsCommand);
        getCommand("reset").setExecutor(new ResetCommand());

        RecipeManager.initDefaultRecipes(this);
        getLogger().info("Zxdzero Events Core initialized");
    }

    @Override
    public void onDisable() {
        ItemMenuRegistry.clearAllMenus();
        RecipeManager.clearAllRecipes();

        getLogger().info("PedestalPlugin (API) has been disabled.");
    }
}
