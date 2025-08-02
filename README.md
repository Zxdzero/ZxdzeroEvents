## Features (as of 1.2):
 - [Pedestals](#Pedestals)
 - [Items command](#/items)


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
