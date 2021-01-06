package com.github.amusingimpala75.terrafabriccraft.item;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class TerraFabricCraftItems {

    public static final Tag<Item> SEDIMENTARY_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("sedimentary"));
    public static final Tag<Item> METAMORPHIC_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("metamorphic"));
    public static final Tag<Item> IGNEOUS_IN_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("igneous_intrusive"));
    public static final Tag<Item> IGNEOUS_EXT_PEBBLES = TagRegistry.item(TerraFabricCraft.getId("igneous_extrusive"));

    public static void registerItems(TerraFabricCraftConfig config) {

    }
}
