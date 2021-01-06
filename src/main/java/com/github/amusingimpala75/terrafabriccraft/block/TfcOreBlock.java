package com.github.amusingimpala75.terrafabriccraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class TfcOreBlock extends OreBlock {

    public TfcOreBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(QUALITY, OreQualityEnum.UNITS_25));
    }

    public static final EnumProperty<OreQualityEnum> QUALITY = EnumProperty.of("quality", OreQualityEnum.class);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(QUALITY);
        super.appendProperties(builder);
    }
}
