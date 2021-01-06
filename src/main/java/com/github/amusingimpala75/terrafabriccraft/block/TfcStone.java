package com.github.amusingimpala75.terrafabriccraft.block;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TfcStone extends Block {

    public TfcStone(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.getBlockState(pos.north()).getBlock() instanceof AirBlock && world.getBlockState(pos.east()).getBlock() instanceof AirBlock &&
                world.getBlockState(pos.south()).getBlock() instanceof AirBlock && world.getBlockState(pos.west()).getBlock() instanceof AirBlock &&
                world.getBlockState(pos.up()).getBlock() instanceof AirBlock && world.getBlockState(pos.down()).getBlock() instanceof AirBlock) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this)));
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }
}
