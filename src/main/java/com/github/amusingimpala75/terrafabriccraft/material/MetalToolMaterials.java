package com.github.amusingimpala75.terrafabriccraft.material;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public enum MetalToolMaterials implements ToolMaterial {
    COPPER(2, 600, 8.0F, 65, 8, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("copper_ingot")))),
    BRONZE(2, 1300, 11.0F, 100, 13, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("bronze_ingot")))),
    BISMUTH_BRONZE(2, 1200, 10.0F, 90, 10, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("bismuth_bronze_ingot")))),
    BLACK_BRONZE(2, 1460, 9.0F, 95, 10, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("black_bronze_ingot")))),
    WROUGHT_IRON(2, 2200, 12.0F, 135, 10, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("wrought_iron_ingot")))),
    STEEL(2, 3300, 14.0F, 170, 10, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("steel_ingot")))),
    BLACK_STEEL(2, 4200, 16.0F, 205, 12, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("black_steel_ingot")))),
    BLUE_STEEL(3, 6500, 18.0F, 240, 22, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("blue_steel_ingot")))),
    RED_STEEL(3, 6500, 18.0F, 240, 22, () -> Ingredient.ofItems(Registry.ITEM.get(TerraFabricCraft.getId("red_steel_ingot"))));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    MetalToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy<>(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
