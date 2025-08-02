package dev.zxdzero;

import dev.zxdzero.commands.PedestalCommand;
import dev.zxdzero.pedestal.PedestalManager;
import dev.zxdzero.pedestal.RecipeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZxdzeroEvents extends JavaPlugin {

    private static ZxdzeroEvents plugin;

    public static ZxdzeroEvents getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        plugin = this;
        PedestalManager pedestalManager = new PedestalManager();


        getServer().getPluginManager().registerEvents(pedestalManager, this);

        getCommand("pedestal").setExecutor(new PedestalCommand(pedestalManager));


        RecipeManager.initDefaultRecipes(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
