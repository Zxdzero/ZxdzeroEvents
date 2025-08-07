## Features (as of 1.4):
 - [Pedestals](#pedestals)
 - [Items command](#items)
 - [Item Action Registry](#item-action-registry)
 - [Cooldown Registry](#cooldown-registry)


### Pedestals
Can be created and manipulated with the /pedestal command  
Plugins can register items/recipes for pedestals using RecipeManager.registerRecipe (see example)
```java
RecipeManager.registerRecipe(plugin, "test", new PedestalRecipe(  
        new ItemStack(Material.DIAMOND_SWORD),  
        List.of(  
                new ItemStack(Material.DIAMOND, 2),  
                new ItemStack(Material.STICK, 1)  
        )  
));
```


### /items
Used to obtain custom items added by plugins  
Will open a menu where you can select which plugin you want to get the items of  
Handling of menus beyond that is up to the individual plugin  
See example of [ItemMenuManager](https://github.com/PuzzleDude98/WitherGames/blob/main/src/main/java/dev/withergames/items/ItemsMenuManager.java) on withergames  
Example for registering item menu:
```java
ItemMenuRegistry.registerItemMenu(
                plugin,
                "withergames_items",
                new ItemStack(Material.NETHER_STAR),
                Component.text("Withergames Items", NamedTextColor.GOLD),
                this::createWitherGamesItemsMenu // method that returns Inventory
        );
```

### Item Action Registry
A convenient way to quickly add rightclick behavior to an item, without making a whole listener  
ItemActionRegistry accepts either a CustomModelDataComponent or an ItemStack, along with a BiConsumer of the player and itemstack object. See example for how to register behavior:  
```java
// This item just produces the cosmetic effects of the blaze amulet
ItemActionRegistry.register(myItemBuilder(), (player, item) -> {  

    player.getWorld().spawnParticle(  
            Particle.FLAME,  
            player.getLocation().add(0, 1, 0),
            100
            0.5, 1, 0.5, 
            0.05 
    );  
    player.getWorld().spawnParticle(  
            Particle.LARGE_SMOKE,  
            player.getLocation().add(0, 1, 0),  
            40,  
            0.3, 0.8, 0.3,  
            0.01  
    );  
    player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);  
    
});
```

### Cooldown Registry
The core plugin supports easy addition of item cooldowns using a namespaced key and a material type. The key is used as the actual identifier of the cooldown, and a provided material will show the visual cooldown to the player.  
Registering a cooldown should be done during the plugins onEnable function, and looks like this:  
```java
public static NamespacedKey exampleCooldown;
@Override  
public void onEnable() {  
    plugin = this;
    exampleCooldown = new NamespacedKey(plugin, "example_cooldown");
    CooldownRegistry.registerCooldown(exampleCooldown, Material.NAUTILUS_SHELL); 
    }  
}
```
Getting and setting the value of the cooldown can be done simply with the `getCooldown()` and `setCooldown()` methods. The cooldown will automatically tick down to 0, then stop.
