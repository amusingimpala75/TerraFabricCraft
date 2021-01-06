package com.github.amusingimpala75.terrafabriccraft.block;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.ducks.BlockDuck;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TerraFabricCraftBlocks {

    public static final EnumProperty<SupportStatesEnum> SUPPORT_STATES = EnumProperty.of("support_state", SupportStatesEnum.class);

    public static final Block ANDESITE_COBBLE = ((BlockDuck)(((BlockDuck)(new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).doesInstantlyCollapse();
    public static final Block DIORITE_COBBLE = ((BlockDuck)(((BlockDuck)(new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).doesInstantlyCollapse();
    public static final Block GRANITE_COBBLE = ((BlockDuck)(((BlockDuck)(new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIRT).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).doesInstantlyCollapse();

    public static final Block LOG_PILE = new LogPile(FabricBlockSettings.of(Material.WOOD).strength(2.0F).sounds(BlockSoundGroup.WOOD));

    public static final Tag<Block> SUPPORT_UNPLACEABLE = TagRegistry.block(TerraFabricCraft.getId("support-unplaceable"));

    public static Map<String, List<String>> STONE_ORE_MAP = new HashMap<>();

    public static final BlockEntityType<LogPileEntity> LOG_PILE_ENTITY = BlockEntity

    public static final String[] WOOD_TYPES = {"acacia", "ash", "aspen", "birch", "chestnut", "douglas_fir", "hickory", "kapok", "maple", "oak", "pine", "sequoia", "spruce", "sycamore", "white_cedar", "white_elm", "willow"};

    public static void registerBlocks(TerraFabricCraftConfig config) {
        //Rock Types
        String[] generalIgExOres = {"bismuthinite", "hematite", "native_copper", "native_gold", "cinnabar", "sulfur", "galena"};
        registerStoneCobblestoneAndOres("andesite", generalIgExOres);
        registerStoneCobblestoneAndOres("basalt", generalIgExOres);
        registerStoneCobblestoneAndOres("dacite", generalIgExOres);
        registerStoneCobblestoneAndOres("rhyolite", generalIgExOres);
        String[] generalIgInOres = {"cassiterite", "native_gold", "sulfur"};
        registerStoneCobblestoneAndOres("diorite", generalIgInOres);
        registerStoneCobblestoneAndOres("gabbro", generalIgInOres, "garnierite", "kimberlite");
        registerStoneCobblestoneAndOres("granite", generalIgInOres, "native_silver", "cryolite", "galena", "pitchblende");
        String[] generalMetaOres = {"sphalerite", "tetrahedrite", "sulfur", "galena"};
        registerStoneCobblestoneAndOres("gneiss", generalMetaOres, "native_silver", "graphite");
        registerStoneCobblestoneAndOres("marble", generalMetaOres, "malachite", "graphite", "lapis_lazuli");
        registerStoneCobblestoneAndOres("phyllite", generalMetaOres);
        registerStoneCobblestoneAndOres("quartzite", generalMetaOres, "cinnabar", "graphite");
        registerStoneCobblestoneAndOres("schist", generalMetaOres, "graphite");
        registerStoneCobblestoneAndOres("slate", generalMetaOres);
        String[] generalSedOres = {"bismuthinite", "limonite", "magnetite", "bituminous_coal", "kaolinite", "lignite", "saltpeter", "sulfur", "native_platinum", "gypsum", "jet"};
        registerStoneCobblestoneAndOres("chalk", generalSedOres);
        registerStoneCobblestoneAndOres("chert", generalSedOres);
        registerStoneCobblestoneAndOres("claystone", generalSedOres);
        registerStoneCobblestoneAndOres("conglomerate", generalSedOres);
        registerStoneCobblestoneAndOres("dolomite", generalSedOres);
        registerStoneCobblestoneAndOres("limestone", generalSedOres, "malachite", "galena");
        registerStoneCobblestoneAndOres("rock_salt", generalSedOres, "borax", "sylvite");
        registerStoneCobblestoneAndOres("shale", generalSedOres, "cinnabar");

        Registry.register(Registry.BLOCK, TerraFabricCraft.getId("log_pile"), LOG_PILE);

        for (String wood : WOOD_TYPES) {
            registerBlockAndBlockItem(new WoodenSupportBlock(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood);
            registerLog(wood, MaterialColor.WOOD, MaterialColor.WOOD, Optional.empty());
            registerBlockAndBlockItem(new Block(AbstractBlock.Settings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), wood+"_planks");
        }

    }

    public static void registerBlockAndBlockItem(Block block, String name, Optional<ItemGroup> group) {
        Registry.register(Registry.BLOCK, TerraFabricCraft.getId(name), block);
        Registry.register(Registry.ITEM, TerraFabricCraft.getId(name), new BlockItem(block, !group.isPresent() ? new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS) : new FabricItemSettings().group(group.get())));
    }
    public static void registerBlockAndBlockItem(Block block, String name) {
        registerBlockAndBlockItem(block, name, Optional.empty());
    }

    public static void registerStoneCobblestoneAndOres(String name, String... acceptableOres) {
        Block cobblestone = ((BlockDuck)(((BlockDuck) new Block(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F))).canCollapse())).doesInstantlyCollapse();
        Block stone = ((BlockDuck)(((BlockDuck)(new TfcStone(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)))).canCollapse())).collapsesToOtherBlock(cobblestone);
        Item pebble = new Item(new FabricItemSettings().group(ItemGroup.MATERIALS));
        registerBlockAndBlockItem(cobblestone, name+"_cobblestone");
        registerBlockAndBlockItem(stone, name);
        Registry.register(Registry.ITEM, TerraFabricCraft.getId(name+"_pebble"), pebble);
        for (String ore : acceptableOres) {
            registerBlockAndBlockItem(new TfcOreBlock(FabricBlockSettings.copyOf(stone)), ore + "_" + name, Optional.of(ItemGroup.BUILDING_BLOCKS));
        }
        STONE_ORE_MAP.put(name, Arrays.asList(acceptableOres));
    }

    public static void registerStoneCobblestoneAndOres(String name, String[] groupOres, String... otherOres) {
        String[] total = new String[groupOres.length+ otherOres.length];
        for (int i = 0; i < total.length; i++) {
            if (i < groupOres.length) {
                total[i] = groupOres[i];
            } else {
                total[i] = otherOres[i- groupOres.length];
            }
        }
        registerStoneCobblestoneAndOres(name, total);
    }

    public static PillarBlock getLog(MaterialColor top, MaterialColor side) {
        Method createLog;
        try {
            createLog = Blocks.class.getDeclaredMethod("createLogBlock", MaterialColor.class, MaterialColor.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new NullPointerException("Method createLog(MaterialColor, MaterialColor) not found in Blocks.class!");
        }
        createLog.setAccessible(true);
        Object log;
        try {
            log = createLog.invoke(Blocks.class.newInstance(), top, side);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
        return (PillarBlock) log;
    }

    public static void registerLog(String name, MaterialColor top, MaterialColor side, Optional<ItemGroup> group) {
        registerBlockAndBlockItem(getLog(top, side), name, group);
    }
}
