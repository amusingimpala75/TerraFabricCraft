package com.github.amusingimpala75.terrafabriccraft.block;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraftConstants;
import com.github.amusingimpala75.terrafabriccraft.block.propertyenums.SupportStatesEnum;
import com.github.amusingimpala75.terrafabriccraft.block.vanillawrappers.ChestBlock;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.ducks.BlockDuck;
import com.github.amusingimpala75.terrafabriccraft.item.LogBlockItem;
import com.github.amusingimpala75.terrafabriccraft.item.PebbleItem;
import com.github.amusingimpala75.terrafabriccraft.material.StoneToolMaterials;
import com.github.amusingimpala75.terrafabriccraft.mixin.BlocksLogInvoker;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class TerraFabricCraftBlocks {

    public static final EnumProperty<SupportStatesEnum> SUPPORT_STATES = EnumProperty.of("support_state", SupportStatesEnum.class);

    //public static final Block ANDESITE_COBBLE = ((BlockDuck)(((BlockDuck)(new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).doesInstantlyCollapse();
    //public static final Block DIORITE_COBBLE = ((BlockDuck)(((BlockDuck)(new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).doesInstantlyCollapse();
    //public static final Block GRANITE_COBBLE = ((BlockDuck)(((BlockDuck)(new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIRT).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).doesInstantlyCollapse();


    public static final Block LOG_PILE = new LogPile(FabricBlockSettings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD));

    public static Map<String, List<String>> STONE_ORE_MAP = new HashMap<>();
    public static List<Block> RENDER_MAP_CUTOUT = new ArrayList<>();
    public static List<ChestBlock> CHESTS = new ArrayList<>();
    public static List<ToolRack> RACKS = new ArrayList<>();

    public static final Tag<Block> SUPPORT_UNPLACEABLE = TagRegistry.block(TerraFabricCraft.getId("support-unplaceable"));

    public static void registerBlocks(TerraFabricCraftConfig config) {
        //Rock Types
        String[] generalIgExOres = {"bismuthinite", "hematite", "native_copper", "native_gold", "cinnabar", "sulfur", "galena"};
        registerStoneCobblestoneAndOres("andesite", StoneToolMaterials.IGNEOUS_EXTRUSIVE, generalIgExOres);
        registerStoneCobblestoneAndOres("basalt", StoneToolMaterials.IGNEOUS_EXTRUSIVE, generalIgExOres);
        registerStoneCobblestoneAndOres("dacite", StoneToolMaterials.IGNEOUS_EXTRUSIVE, generalIgExOres);
        registerStoneCobblestoneAndOres("rhyolite", StoneToolMaterials.IGNEOUS_EXTRUSIVE, generalIgExOres);
        String[] generalIgInOres = {"cassiterite", "native_gold", "sulfur"};
        registerStoneCobblestoneAndOres("diorite", StoneToolMaterials.IGNEOUS_INTRUSIVE, generalIgInOres);
        registerStoneCobblestoneAndOres("gabbro", StoneToolMaterials.IGNEOUS_INTRUSIVE, generalIgInOres, "garnierite", "kimberlite");
        registerStoneCobblestoneAndOres("granite", StoneToolMaterials.IGNEOUS_INTRUSIVE, generalIgInOres, "native_silver", "cryolite", "galena", "pitchblende");
        String[] generalMetaOres = {"sphalerite", "tetrahedrite", "sulfur", "galena"};
        registerStoneCobblestoneAndOres("gneiss", StoneToolMaterials.METAMORPHIC, generalMetaOres, "native_silver", "graphite");
        registerStoneCobblestoneAndOres("marble", StoneToolMaterials.METAMORPHIC, generalMetaOres, "malachite", "graphite", "lapis_lazuli");
        registerStoneCobblestoneAndOres("phyllite", StoneToolMaterials.METAMORPHIC, generalMetaOres);
        registerStoneCobblestoneAndOres("quartzite", StoneToolMaterials.METAMORPHIC, generalMetaOres, "cinnabar", "graphite");
        registerStoneCobblestoneAndOres("schist", StoneToolMaterials.METAMORPHIC, generalMetaOres, "graphite");
        registerStoneCobblestoneAndOres("slate", StoneToolMaterials.METAMORPHIC, generalMetaOres);
        String[] generalSedOres = {"bismuthinite", "limonite", "magnetite", "bituminous_coal", "kaolinite", "lignite", "saltpeter", "sulfur", "native_platinum", "gypsum", "jet"};
        registerStoneCobblestoneAndOres("chalk", StoneToolMaterials.SEDIMENTARY, generalSedOres);
        registerStoneCobblestoneAndOres("chert", StoneToolMaterials.SEDIMENTARY, generalSedOres);
        registerStoneCobblestoneAndOres("claystone", StoneToolMaterials.SEDIMENTARY, generalSedOres);
        registerStoneCobblestoneAndOres("conglomerate", StoneToolMaterials.SEDIMENTARY, generalSedOres);
        registerStoneCobblestoneAndOres("dolomite", StoneToolMaterials.SEDIMENTARY, generalSedOres);
        registerStoneCobblestoneAndOres("limestone", StoneToolMaterials.SEDIMENTARY, generalSedOres, "malachite", "galena");
        registerStoneCobblestoneAndOres("rock_salt", StoneToolMaterials.SEDIMENTARY, generalSedOres, "borax", "sylvite");
        registerStoneCobblestoneAndOres("shale", StoneToolMaterials.SEDIMENTARY, generalSedOres, "cinnabar");

        Registry.register(Registry.BLOCK, TerraFabricCraft.getId("log_pile"), LOG_PILE);

        for (String wood : TerraFabricCraftConstants.WOOD_TYPES) {
            registerBlockAndBlockItem(new WoodenSupportBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_support", 32);
            registerLog(wood+"_log", MaterialColor.WOOD, MaterialColor.WOOD);
            registerBlockAndBlockItem(new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_planks", 32);
            //Block door = registerBlockAndBlockItem(new DoorBlock(FabricBlockSettings.of(Material.WOOD).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()), wood+"_door", 1);
            //RENDER_MAP_CUTOUT.add(door);
            Block fence = registerBlockAndBlockItem(new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_fence", 32);
            RENDER_MAP_CUTOUT.add(fence);
            //TODO: Implement models and uncomment
            //Block fenceGate = registerBlockAndBlockItem(new FenceGateBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_fence_gate", 32);
            //RENDER_MAP_CUTOUT.add(fenceGate);
            //registerBlockAndBlockItem(new BarrelBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_barrel", 4);
            ChestBlock chest = (ChestBlock) registerBlockAndBlockItem(new ChestBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_chest", 32);
            CHESTS.add(chest);
            //Block loom = registerBlockAndBlockItem(new LoomBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD)), wood+"_loom", 4);
            ToolRack tool_rack = (ToolRack) registerBlockAndBlockItem(new ToolRack(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD)), wood+"_tool_rack", 4);
            RACKS.add(tool_rack);
        }

        for (Map.Entry<String, List<String>> stone : STONE_ORE_MAP.entrySet()) {
            registerBlockAndBlockItem(new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F)), stone.getKey()+"_bricks", 32);

        }

    }

    public static Block registerBlockAndBlockItem(Block block, String name, Optional<ItemGroup> group, int maxCount) {
        Registry.register(Registry.BLOCK, TerraFabricCraft.getId(name), block);
        Registry.register(Registry.ITEM, TerraFabricCraft.getId(name), new BlockItem(block, !group.isPresent() ? new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS).maxCount(maxCount) : new FabricItemSettings().group(group.get()).maxCount(maxCount)));
        return block;
    }

    public static Block registerBlockAndBlockItem(Block block, String name, int maxCount) {
        return registerBlockAndBlockItem(block, name, Optional.empty(), maxCount);
    }

    public static void registerStoneCobblestoneAndOres(String name, StoneToolMaterials material, String... acceptableOres) {
        Block cobblestone = ((BlockDuck)(((BlockDuck) new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F))).canCollapse())).doesInstantlyCollapse();
        Block stone = ((BlockDuck)(((BlockDuck)(new TfcStone(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).collapsesToOtherBlock(cobblestone);
        Item pebble = new PebbleItem(new FabricItemSettings().group(ItemGroup.MATERIALS), material, "textures/items/rocks/flatrocks/"+name+".png");
        registerBlockAndBlockItem(cobblestone, name+"_cobblestone", 32);
        registerBlockAndBlockItem(stone, name, 32);
        Registry.register(Registry.ITEM, TerraFabricCraft.getId(name+"_pebble"), pebble);
        for (String ore : acceptableOres) {
            registerBlockAndBlockItem(new TfcOreBlock(FabricBlockSettings.copyOf(stone)), ore + "_" + name, Optional.of(ItemGroup.BUILDING_BLOCKS), 16);
        }
        STONE_ORE_MAP.put(name, Arrays.asList(acceptableOres));
        registerGrassable(name, false);
        registerGrassable(name, true);
        registerBlockAndBlockItem(((BlockDuck)(((BlockDuck) new Block(FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.STONE).strength(0.6F).sounds(BlockSoundGroup.GRAVEL))).canCollapse())).doesInstantlyCollapse(), name+"_gravel", 32);
        registerBlockAndBlockItem(((BlockDuck)(((BlockDuck) new Block(FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.SAND).strength(0.5F).sounds(BlockSoundGroup.SAND))).canCollapse())).doesInstantlyCollapse(), name+"_sand", 32);
        //TODO: Farmland
    }

    public static void registerStoneCobblestoneAndOres(String name, StoneToolMaterials material, String[] groupOres, String... otherOres) {
        String[] total = new String[groupOres.length+ otherOres.length];
        for (int i = 0; i < total.length; i++) {
            if (i < groupOres.length) {
                total[i] = groupOres[i];
            } else {
                total[i] = otherOres[i- groupOres.length];
            }
        }
        registerStoneCobblestoneAndOres(name, material, total);
    }

    public static PillarBlock getLog(MaterialColor top, MaterialColor side) {
        return BlocksLogInvoker.invokeCreateLogBlock(top, side);
    }

    public static void registerLog(String name, MaterialColor top, MaterialColor side) {
        Block log = Registry.register(Registry.BLOCK, TerraFabricCraft.getId(name), getLog(top, side));
        Registry.register(Registry.ITEM, TerraFabricCraft.getId(name), new LogBlockItem(log, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS).maxCount(16)));
    }

    public static void registerGrassable(String name, boolean isClay) {
        TFCGrassBlock grass = new TFCGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS));
        TFCConvertableBlock nonGrass = new TFCConvertableBlock(FabricBlockSettings.of(Material.SOIL, MaterialColor.DIRT).strength(0.5F).sounds(BlockSoundGroup.GRAVEL));
        grass.convertBlock = () -> nonGrass;
        nonGrass.grassVariant = () -> grass;
        registerBlockAndBlockItem(grass, name+(isClay ? "_clay" : "")+"_grass", 32);
        registerBlockAndBlockItem(nonGrass, name+(isClay ? "_clay" : "_dirt"), 32);
        RENDER_MAP_CUTOUT.add(grass);
    }
}
