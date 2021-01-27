package com.github.amusingimpala75.terrafabriccraft.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpreadableBlock.class)
public interface SpreadableInvokers {
    @Invoker
    static boolean invokeCanSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        throw new AssertionError();
    }

    @Invoker
    static boolean invokeCanSpread(BlockState state, WorldView worldView, BlockPos pos) {
        throw new AssertionError();
    }

}
