package com.github.amusingimpala75.terrafabriccraft.block;

import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.item.TerraFabricCraftItems;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.Tag;

public enum StoneToolMaterials implements ToolMaterial {

    SEDIMENTARY(TerraFabricCraftItems.SEDIMENTARY_PEBBLES, 50, 6.0F),
    METAMORPHIC(TerraFabricCraftItems.METAMORPHIC_PEBBLES, 55, 6.5F),
    IGNEOUS_INTRUSIVE(TerraFabricCraftItems.IGNEOUS_IN_PEBBLES, 60, 7.0F),
    IGNEOUS_EXTRUSIVE(TerraFabricCraftItems.IGNEOUS_EXT_PEBBLES, 70, 7.0F);

    public final Tag<Item> INGREDIENT_TAG;
    public final int DURABILITY;
    public final float SPEED;

    StoneToolMaterials(Tag<Item> ing_tag, int durab, float speed) {
        this.INGREDIENT_TAG = ing_tag;
        this.DURABILITY = durab;
        this.SPEED = speed;
    }

    @Override
    public int getDurability() {
        return this.DURABILITY;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.SPEED;
    }

    @Override
    public float getAttackDamage() {
        TerraFabricCraftConfig config = AutoConfig.getConfigHolder(TerraFabricCraftConfig.class).getConfig();
        if (FabricLoader.getInstance().isModLoaded("terrafabriccraft")) {
            config.TFCMode = true;
        }
        return config.TFCMode ? 40.0F : 1.0F;
    }

    @Override
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.fromTag(this.INGREDIENT_TAG);
    }
}
