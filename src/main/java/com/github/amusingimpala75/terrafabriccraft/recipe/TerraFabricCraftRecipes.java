package com.github.amusingimpala75.terrafabriccraft.recipe;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

public class TerraFabricCraftRecipes {

    public static final RecipeSerializer<KnappingRecipe> KNAPPING_RECIPE = Registry.register(Registry.RECIPE_SERIALIZER, TerraFabricCraft.getId("knapping"), new KnappingRecipe.Serializer());
    public static final RecipeType<KnappingRecipe> KNAPPING_RECIPE_TYPE = Registry.register(Registry.RECIPE_TYPE, TerraFabricCraft.getId("knapping"), new RecipeType<KnappingRecipe>() {
        public String toString() {
            return "knapping";
        }
    });

    public static void registerRecipes() {

    }
}
