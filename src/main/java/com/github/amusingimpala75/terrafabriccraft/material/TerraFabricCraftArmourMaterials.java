package com.github.amusingimpala75.terrafabriccraft.material;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.item.DamageTypeEnum;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum TerraFabricCraftArmourMaterials implements ArmorMaterial {

    COPPER("copper", TerraFabricCraft.getId("copper_ingot"), new int[]{300, 250, 200}),
    BISMUTH_BRONZE("bismuth_bronze", TerraFabricCraft.getId("bismuth_bronze_ingot"), new int[]{330, 400, 600}),
    BLACK_BRONZE("black_bronze", TerraFabricCraft.getId("black_bronze_ingot"), new int[]{330, 600, 400}),
    BRONZE("bronze", TerraFabricCraft.getId("bronze_ingot"), new int[]{330, 500, 500}),
    WROUGHT_IRON("wrought_iron", TerraFabricCraft.getId("wrought_iron_ingot"), new int[]{528, 800, 800}),
    STEEL("steel", TerraFabricCraft.getId("steel_ingot"), new int[]{660, 1200, 1000}),
    BLACK_STEEL("black_steel", TerraFabricCraft.getId("black_steel_ingot"), new int[]{1320, 1800, 2000}),
    BLUE_STEEL("blue_steel", TerraFabricCraft.getId("blue_steel_ingot"), new int[]{2000, 2000, 2500}),
    RED_STEEL("red_steel", TerraFabricCraft.getId("red_steel_ingot"), new int[]{2000, 2500, 2000});


    private static final int[] baseDurability = new int[] {2500, 3750, 3000, 2500,0};
    private final String name;
    private final Identifier repairIng;

    //Follows ordinal of DamageTypeEnum
    private final int[] damageTypes;

    TerraFabricCraftArmourMaterials(String name, Identifier repairIng, int[] damageTypeVals) {
        this.name = name;
        this.repairIng = repairIng;
        this.damageTypes = damageTypeVals;
    }


    @Override
    public int getDurability(EquipmentSlot slot) {
        return baseDurability[slot.getArmorStandSlotId()-1];
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return getProtectionAmount(slot, DamageTypeEnum.SLASHING);
    }

    public int getProtectionAmount(EquipmentSlot slot, DamageTypeEnum type) {
        return damageTypes[type.ordinal()];
    }

    //I don't know
    @Override
    public int getEnchantability() {
        return 20;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Registry.ITEM.get(repairIng));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

    public static TerraFabricCraftArmourMaterials fromString(String name) {
        for (TerraFabricCraftArmourMaterials val : TerraFabricCraftArmourMaterials.values()) {
            if (val.name.equals(name)) {
                return val;
            }
        }
        throw new IllegalStateException("Could not find armor material "+name+" in TerraFabricCraftArmourMaterials!");
    }
}
