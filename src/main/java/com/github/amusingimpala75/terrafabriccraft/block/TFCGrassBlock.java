package com.github.amusingimpala75.terrafabriccraft.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import com.github.amusingimpala75.terrafabriccraft.mixin.SpreadableInvokers;

import java.util.Random;
import java.util.function.Supplier;

public class TFCGrassBlock extends SpreadableBlock {

    public Supplier<Block> convertBlock;

    public TFCGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //States for dirt, clay, grass-dirt, grass-clay
        if (!SpreadableInvokers.invokeCanSurvive(state, world, pos)) {
            world.setBlockState(pos, convertBlock.get().getDefaultState());
        } else {
            if (world.getLightLevel(pos.up()) >= 9) {
                BlockState blockState = this.getDefaultState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (world.getBlockState(blockPos).getBlock() instanceof TFCConvertableBlock && SpreadableInvokers.invokeCanSpread(blockState, world, blockPos)) {
                        blockState = ((TFCConvertableBlock)world.getBlockState(blockPos).getBlock()).grassVariant.get().getDefaultState();
                        world.setBlockState(blockPos, blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                    }
                }
            }
        }
    }
}
