package dev.zxdzero.registries;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RecipeManager {
    private static final Map<String, PedestalRecipe> recipes = new HashMap<>();
    private static final Logger logger = Logger.getLogger("ZxdzeroEvents-Pedestals");

    /**
     * Registers a recipe with a namespaced ID.
     *
     * @param plugin The plugin registering the recipe
     * @param recipeId The recipe ID (without namespace)
     * @param recipe The recipe to register
     * @return true if the recipe was registered successfully, false if the ID already exists
     */
    public static boolean registerRecipe(Plugin plugin, String recipeId, PedestalRecipe recipe) {
        if (plugin == null || recipeId == null || recipe == null) {
            throw new IllegalArgumentException("Plugin, recipe ID, and recipe cannot be null");
        }

        if (recipeId.contains(":")) {
            throw new IllegalArgumentException("Recipe ID cannot contain ':' - namespace is automatically added");
        }

        String namespacedId = plugin.getName().toLowerCase() + ":" + recipeId;

        if (recipes.containsKey(namespacedId)) {
            logger.warning("Recipe with ID '" + namespacedId + "' already exists. Registration failed.");
            return false;
        }

        recipes.put(namespacedId, recipe);
        logger.info("Registered recipe '" + namespacedId + "' from plugin " + plugin.getName());
        return true;
    }

    /**
     * Registers a recipe with a fully qualified namespaced ID.
     * Use this method if you need to specify a custom namespace.
     *
     * @param namespacedId The full namespaced ID (e.g., "myplugin:my_recipe")
     * @param recipe The recipe to register
     * @return true if the recipe was registered successfully, false if the ID already exists
     */
    public static boolean registerRecipe(String namespacedId, PedestalRecipe recipe) {
        if (namespacedId == null || recipe == null) {
            throw new IllegalArgumentException("Namespaced ID and recipe cannot be null");
        }

        if (!namespacedId.contains(":")) {
            throw new IllegalArgumentException("Recipe ID must be namespaced (format: 'namespace:id')");
        }

        if (recipes.containsKey(namespacedId)) {
            logger.warning("Recipe with ID '" + namespacedId + "' already exists. Registration failed.");
            return false;
        }

        recipes.put(namespacedId, recipe);
        logger.info("Registered recipe '" + namespacedId + "'");
        return true;
    }

    /**
     * Unregisters a recipe by its namespaced ID.
     *
     * @param namespacedId The namespaced ID of the recipe to remove
     * @return true if the recipe was removed, false if it didn't exist
     */
    public static boolean unregisterRecipe(String namespacedId) {
        boolean removed = recipes.remove(namespacedId) != null;
        if (removed) {
            logger.info("Unregistered recipe '" + namespacedId + "'");
        }
        return removed;
    }

    /**
     * Unregisters all recipes from a specific plugin.
     * Useful for cleanup when a plugin is disabled.
     *
     * @param plugin The plugin whose recipes should be removed
     * @return the number of recipes that were removed
     */
    public static int unregisterRecipesFromPlugin(Plugin plugin) {
        if (plugin == null) {
            return 0;
        }

        String namespace = plugin.getName().toLowerCase() + ":";
        List<String> toRemove = recipes.keySet().stream()
                .filter(id -> id.startsWith(namespace))
                .toList();

        toRemove.forEach(recipes::remove);

        if (!toRemove.isEmpty()) {
            logger.info("Unregistered " + toRemove.size() + " recipes from plugin " + plugin.getName());
        }

        return toRemove.size();
    }

    /**
     * Gets a recipe by its namespaced ID.
     *
     * @param namespacedId The namespaced ID of the recipe
     * @return The recipe, or null if not found
     */
    public static PedestalRecipe getRecipe(String namespacedId) {
        return recipes.get(namespacedId);
    }

    /**
     * Gets all registered recipe IDs.
     *
     * @return A set of all namespaced recipe IDs
     */
    public static List<String> getAllRecipeIds() {
        return recipes.keySet().stream().toList();
    }

    /**
     * Gets all recipes from a specific plugin.
     *
     * @param plugin The plugin to get recipes from
     * @return A map of recipe IDs (without namespace) to recipes
     */
    public static Map<String, PedestalRecipe> getRecipesFromPlugin(Plugin plugin) {
        if (plugin == null) {
            return Map.of();
        }

        String namespace = plugin.getName().toLowerCase() + ":";
        Map<String, PedestalRecipe> pluginRecipes = new HashMap<>();

        recipes.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(namespace))
                .forEach(entry -> {
                    String idWithoutNamespace = entry.getKey().substring(namespace.length());
                    pluginRecipes.put(idWithoutNamespace, entry.getValue());
                });

        return pluginRecipes;
    }

    /**
     * Checks if a recipe with the given ID exists.
     *
     * @param namespacedId The namespaced ID to check
     * @return true if the recipe exists, false otherwise
     */
    public static boolean hasRecipe(String namespacedId) {
        return recipes.containsKey(namespacedId);
    }

    /**
     * Gets the total number of registered recipes.
     *
     * @return The number of registered recipes
     */
    public static int getRecipeCount() {
        return recipes.size();
    }

    /**
     * Initializes the default recipes for this plugin.
     * This method should be called during plugin startup.
     *
     * @param plugin The plugin instance
     */
    public static void initDefaultRecipes(Plugin plugin) {
        registerRecipe(plugin, "test", new PedestalRecipe(
                new ItemStack(Material.DIAMOND_SWORD),
                List.of(
                        new ItemStack(Material.DIAMOND, 2),
                        new ItemStack(Material.STICK, 1)
                )
        ));    }

    /**
     * Clears all registered recipes.
     * Use with caution - this will remove all recipes from all plugins.
     */
    public static void clearAllRecipes() {
        int count = recipes.size();
        recipes.clear();
        logger.info("Cleared all " + count + " registered recipes");
    }

    public record PedestalRecipe(ItemStack result, List<ItemStack> ingredients) {

        public ItemStack result() {
            return result.clone();
        }

        public Component getRecipeText() {
            TextComponent.Builder text = Component.text();
            text.append(result.displayName().color(NamedTextColor.GOLD));
            for (ItemStack ingredient : ingredients) {
                text.append(Component.text("\n"));
                text.append(Component.text(ingredient.getAmount() + "x " + ingredient.getType().name().replace("_", " "), NamedTextColor.GREEN));
            }
            return text.build();
        }
    }
}