package com.github.amusingimpala75.terrafabriccraft.block.entity;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.block.TerraFabricCraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class TerraFabricCraftBlockEntities {
    private static final Block[] CHESTS = TerraFabricCraftBlocks.CHESTS.toArray(new Block[0]);
    private static final Block[] RACKS = TerraFabricCraftBlocks.RACKS.toArray(new Block[0]);

    public static final BlockEntityType<LogPileEntity> LOG_PILE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraFabricCraft.getId("log_pile"), BlockEntityType.Builder.create(LogPileEntity::new, TerraFabricCraftBlocks.LOG_PILE).build(null));
    public static final BlockEntityType<TFCChestEntity> CHEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraFabricCraft.getId("mod_chest"), BlockEntityType.Builder.create(TFCChestEntity::new, CHESTS).build(null));
    public static final BlockEntityType<ToolRackBlockEntity> TOOL_RACK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, TerraFabricCraft.getId("tool_rack"), BlockEntityType.Builder.create(ToolRackBlockEntity::new, RACKS).build(null));

    public static void registerBlockEntities() {

    }
}
