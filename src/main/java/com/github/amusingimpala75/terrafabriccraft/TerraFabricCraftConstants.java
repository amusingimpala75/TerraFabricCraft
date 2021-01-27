package com.github.amusingimpala75.terrafabriccraft;

import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TerraFabricCraftConstants {
    public static final String[] WOOD_TYPES = {"acacia", "ash", "aspen", "birch", "chestnut", "douglas_fir", "hickory", "kapok", "maple", "oak", "pine", "sequoia", "spruce", "sycamore", "white_cedar", "white_elm", "willow"};
    public static final boolean datagen = true;
    public static final List<Pair<String, Boolean>> ORES = new ArrayList<>();
    public static final List<String> GEMS = new ArrayList<>();
    public static final List<Pair<String, Boolean>> TOOL_TYPES = new ArrayList<>();
    public static final List<String> INGOTS = new ArrayList<>();

    static {
        ORES.add(new Pair<>("bismuthinite", true));
        ORES.add(new Pair<>("cassiterite", true));
        ORES.add(new Pair<>("garnierite", true));
        ORES.add(new Pair<>("hematite", true));
        ORES.add(new Pair<>("limonite", true));
        ORES.add(new Pair<>("magnetite", true));
        ORES.add(new Pair<>("malachite", true));
        ORES.add(new Pair<>("native_copper", true));
        ORES.add(new Pair<>("native_gold", true));
        ORES.add(new Pair<>("native_silver", true));
        ORES.add(new Pair<>("sphalerite", true));
        ORES.add(new Pair<>("tetrahedrite", true));
        ORES.add(new Pair<>("galena", true));
        ORES.add(new Pair<>("native_platinum", true));
        ORES.add(new Pair<>("serpentine", false));
        ORES.add(new Pair<>("sylvite", false));
        ORES.add(new Pair<>("sulfur", false));

        GEMS.add("agate");
        GEMS.add("amethyst");
        GEMS.add("beryl");
        GEMS.add("diamond");
        GEMS.add("emerald");
        GEMS.add("garnet");
        GEMS.add("jade");
        GEMS.add("jasper");
        GEMS.add("opal");
        GEMS.add("ruby");
        GEMS.add("sapphire");
        GEMS.add("topaz");
        GEMS.add("tourmaline");

        TOOL_TYPES.add(new Pair<>("axe", true));
        TOOL_TYPES.add(new Pair<>("hammer", true));
        TOOL_TYPES.add(new Pair<>("hoe", true));
        TOOL_TYPES.add(new Pair<>("javelin", true));
        TOOL_TYPES.add(new Pair<>("knife", true));
        TOOL_TYPES.add(new Pair<>("shovel", true));
        TOOL_TYPES.add(new Pair<>("propick", false));
        TOOL_TYPES.add(new Pair<>("chisel", false));
        TOOL_TYPES.add(new Pair<>("pick", false));
        TOOL_TYPES.add(new Pair<>("saw", false));
        TOOL_TYPES.add(new Pair<>("scythe", false));
        TOOL_TYPES.add(new Pair<>("mace", false));
        TOOL_TYPES.add(new Pair<>("sword", false));

        INGOTS.add("bismuth");
        INGOTS.add("tin");
        INGOTS.add("zinc");
        INGOTS.add("copper");
        INGOTS.add("bronze");
        INGOTS.add("bismuth_bronze");
        INGOTS.add("black_bronze");
        INGOTS.add("brass");
        INGOTS.add("lead");
        INGOTS.add("gold");
        INGOTS.add("rose_gold");
        INGOTS.add("silver");
        INGOTS.add("sterling_silver");
        INGOTS.add("platinum");
        INGOTS.add("wrought_iron");
        INGOTS.add("nickel");
        INGOTS.add("pig_iron");
        INGOTS.add("steel");
        INGOTS.add("black_steel");
        INGOTS.add("blue_steel");
        INGOTS.add("red_steel");
        INGOTS.add("unknown");

    }
}
