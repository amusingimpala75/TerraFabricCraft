package com.github.amusingimpala75.terrafabriccraft;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class TerraFabricCraftItemGroups {

    public static final ItemGroup TOOL_COMPONENTS = FabricItemGroupBuilder.create(
            TerraFabricCraft.getId("tool_components"))
            .icon(() -> new ItemStack(Registry.ITEM.get(TerraFabricCraft.getId("red_steel_pick_head")))).build();

    public static void registerItemGroups() {

    }
}
