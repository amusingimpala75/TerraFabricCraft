package com.github.amusingimpala75.terrafabriccraft.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.CompoundTag;

public class TFCChestEntity extends ChestBlockEntity {
    private String texPath;

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        texPath = tag.getString("textureForRenderer");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putString("textureForRenderer", texPath);
        return tag;
    }

    public TFCChestEntity() {
        this("");
    }

    public TFCChestEntity(String texturePath) {
        super(TerraFabricCraftBlockEntities.CHEST_BLOCK_ENTITY);
        this.texPath = texturePath;
    }

    public String getTexturePath() {
        return this.texPath;
    }
}
