package com.github.amusingimpala75.terrafabriccraft.block.vanillawrappers;

import com.github.amusingimpala75.terrafabriccraft.block.entity.TFCChestEntity;
import com.github.amusingimpala75.terrafabriccraft.block.entity.TerraFabricCraftBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class ChestBlock extends net.minecraft.block.ChestBlock {

    public ChestBlock(Settings settings) {
        super(settings, () -> TerraFabricCraftBlockEntities.CHEST_BLOCK_ENTITY);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TFCChestEntity(getTextureId());
    }

    public String getTextureId() {
        String id = Registry.BLOCK.getId(this).getPath();
        String woodType = id.substring(0, id.indexOf("_chest"));
        String path = "models/chest/normal_"+woodType;
        return path;
    }
}
