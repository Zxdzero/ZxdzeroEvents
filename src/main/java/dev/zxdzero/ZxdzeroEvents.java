package dev.zxdzero;

import dev.zxdzero.commands.ItemsCommand;
import dev.zxdzero.commands.PedestalCommand;
import dev.zxdzero.registries.RecipeManager;
import dev.zxdzero.registries.ItemMenuRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZxdzeroEvents extends JavaPlugin {

    private static ZxdzeroEvents plugin;

    public static ZxdzeroEvents getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        plugin = this;
        PedestalManager pedestalManager = new PedestalManager();
        ItemsCommand itemsCommand = new ItemsCommand();


        getServer().getPluginManager().registerEvents(pedestalManager, this);
        getServer().getPluginManager().registerEvents(itemsCommand, this);

        getCommand("pedestal").setExecutor(new PedestalCommand(pedestalManager));
        getCommand("items").setExecutor(itemsCommand);


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
