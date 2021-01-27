package com.github.amusingimpala75.terrafabriccraft.block;

import net.minecraft.block.Block;

import java.util.function.Supplier;

public class TFCConvertableBlock extends Block {

    public Supplier<Block> grassVariant;

    public TFCConvertableBlock(Settings settings) {
        super(settings);
    }
}
