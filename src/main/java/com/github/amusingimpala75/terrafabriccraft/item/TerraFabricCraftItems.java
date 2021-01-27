package com.github.amusingimpala75.terrafabriccraft.item;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraftConstants;
import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraftItemGroups;
import com.github.amusingimpala75.terrafabriccraft.item.vanillawrapper.AxeItem;
import com.github.amusingimpala75.terrafabriccraft.item.vanillawrapper.HoeItem;
import com.github.amusingimpala75.terrafabriccraft.item.vanillawrapper.PickaxeItem;
import com.github.amusingimpala75.terrafabriccraft.material.MetalToolMaterials;
import com.github.amusingimpala75.terrafabriccraft.material.StoneToolMaterials;
import com.github.amusingimpala75.terrafabriccraft.block.propertyenums.OreQualityEnum;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.material.TerraFabricCraftArmourMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

//TODO Implement size and weight requirements
public class TerraFabricCraftItems {

    public static final Tag<Item> SEDIMENTARY_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("sedimentary"));
    public static final Tag<Item> METAMORPHIC_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("metamorphic"));
    public static final Tag<Item> IGNEOUS_IN_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("igneous_intrusive"));
    public static final Tag<Item> IGNEOUS_EXT_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("igneous_extrusive"));

    public static void registerItems(TerraFabricCraftConfig config) {
        registerItem("straw", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
        for (String ingot : TerraFabricCraftConstants.INGOTS) {
            registerItem(ingot+"_ingot", new Item(new FabricItemSettings().maxCount(32).group(ItemGroup.MATERIALS)));
            if (!ingot.equals("unknown")) {
                registerItem(ingot + "_double_ingot", new Item(new FabricItemSettings().maxCount(8).group(ItemGroup.MATERIALS)));
                registerItem(ingot + "_sheet", new Item(new FabricItemSettings().maxCount(16).group(ItemGroup.MATERIALS)));
                registerItem(ingot + "_double_sheet", new Item(new FabricItemSettings().maxCount(8).group(ItemGroup.MATERIALS)));
            }
        }

        //TODO: Fix attack damages and speed, implement special tools
        for (StoneToolMaterials material : StoneToolMaterials.values()) {
            String name = material.name().toLowerCase(Locale.ROOT);
            for (Pair<String, Boolean> toolTypePair : TerraFabricCraftConstants.TOOL_TYPES) {
                String name2 = toolTypePair.getLeft();
                if (toolTypePair.getRight()) {
                    registerItem(name + "_"+name2+"_head", new ToolHeadItem(material, new FabricItemSettings().maxCount(4).group(TerraFabricCraftItemGroups.TOOL_COMPONENTS)));
                }
            }
            registerItem(name+"_axe", new AxeItem(material, 60, -3.2F, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_hammer", new HammerItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_hoe", new HoeItem(material, 60, -2.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_javelin", new JavelinItem(material, new FabricItemSettings().group(ItemGroup.COMBAT)));
            registerItem(name+"_knife", new KnifeItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_shovel", new ShovelItem(material, 60, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
        }
        for (MetalToolMaterials material : MetalToolMaterials.values()) {
            String name = material.name().toLowerCase(Locale.ROOT);
            for (Pair<String, Boolean> toolTypePair : TerraFabricCraftConstants.TOOL_TYPES) {
                String name2 = toolTypePair.getLeft();
                registerItem(name + "_"+name2+"_head", new ToolHeadItem(material, new FabricItemSettings().maxCount(4).group(TerraFabricCraftItemGroups.TOOL_COMPONENTS)));
            }
            registerItem(name+"_axe", new AxeItem(material, 0.0F, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_hammer", new HammerItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_hoe", new HoeItem(material, 0, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_javelin", new JavelinItem(material, new FabricItemSettings().group(ItemGroup.COMBAT)));
            registerItem(name+"_knife", new KnifeItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_shovel", new ShovelItem(material, 0.0F, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_propick", new ProPickItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_chisel", new ChiselItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_pick", new PickaxeItem(material, 0, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_saw", new SawItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_scythe", new ScytheItem(material, new FabricItemSettings().group(ItemGroup.TOOLS)));
            registerItem(name+"_mace", new MaceItem(material, new FabricItemSettings().group(ItemGroup.COMBAT)));
            registerItem(name+"_sword", new SwordItem(material, 0, 0.0F, new FabricItemSettings().group(ItemGroup.COMBAT)));

            registerItem(name+"_helmet", new ModdedArmorItem(TerraFabricCraftArmourMaterials.fromString(name), EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT), TerraFabricCraft.getTextureId("models/armor/"+name.replace("_", ""))));
            registerItem(name+"_chestplate", new ModdedArmorItem(TerraFabricCraftArmourMaterials.fromString(name), EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT), TerraFabricCraft.getTextureId("models/armor/"+name.replace("_", ""))));
            registerItem(name+"_leggings", new ModdedArmorItem(TerraFabricCraftArmourMaterials.fromString(name), EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT), TerraFabricCraft.getTextureId("models/armor/"+name.replace("_", ""))));
            registerItem(name+"_boots", new ModdedArmorItem(TerraFabricCraftArmourMaterials.fromString(name), EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT), TerraFabricCraft.getTextureId("models/armor/"+name.replace("_", ""))));

        }


        for (Pair<String, Boolean> toolTypePair : TerraFabricCraftConstants.TOOL_TYPES) {
            String name = toolTypePair.getLeft();
            registerItem(name+"_mold", new MoldItem(new FabricItemSettings().maxCount(32).group(ItemGroup.TOOLS)));
        }
        for (String wood : TerraFabricCraftConstants.WOOD_TYPES) {
            registerItem(wood+"_lumber", new Item(new FabricItemSettings().maxCount(32).group(ItemGroup.MATERIALS)));
            //ArmourStand
        }
        for (Pair<String, Boolean> ore2 : TerraFabricCraftConstants.ORES) {
            String ore = ore2.getLeft();
            if (ore2.getRight()) {
                registerItem(ore+"_small", new OreItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MATERIALS), OreQualityEnum.UNITS_10));
            }
            registerItem(ore+"_poor", new OreItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MATERIALS), OreQualityEnum.UNITS_15));
            registerItem(ore+"_average", new OreItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MATERIALS), OreQualityEnum.UNITS_25));
            registerItem(ore+"_rich", new OreItem(new FabricItemSettings().maxCount(16).group(ItemGroup.MATERIALS), OreQualityEnum.UNITS_35));
        }
        for (String gem : TerraFabricCraftConstants.GEMS) {
            registerItem(gem+"_chipped", new Item(new FabricItemSettings().maxCount(64).group(ItemGroup.MATERIALS)));
            registerItem(gem+"_flawed", new Item(new FabricItemSettings().maxCount(64).group(ItemGroup.MATERIALS)));
            registerItem(gem+"_normal", new Item(new FabricItemSettings().maxCount(64).group(ItemGroup.MATERIALS)));
            registerItem(gem+"_flawless", new Item(new FabricItemSettings().maxCount(64).group(ItemGroup.MATERIALS)));
            registerItem(gem+"_exquisite", new Item(new FabricItemSettings().maxCount(64).group(ItemGroup.MATERIALS)));
        }
    }

    public static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, TerraFabricCraft.getId(name), item);
    }
}
