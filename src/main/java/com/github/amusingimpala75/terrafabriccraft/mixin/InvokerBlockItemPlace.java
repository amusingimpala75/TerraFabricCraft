package com.github.amusingimpala75.terrafabriccraft.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockItem.class)
public interface InvokerBlockItemPlace {

    @Invoker
    BlockState invokePlaceFromTag(BlockPos pos, World world, ItemStack stack, BlockState state);
}
