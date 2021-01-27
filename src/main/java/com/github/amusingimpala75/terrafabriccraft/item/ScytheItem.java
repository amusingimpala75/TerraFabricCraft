package com.github.amusingimpala75.terrafabriccraft.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScytheItem extends ToolItem {

    public ScytheItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            BlockPos.Mutable posMut = pos.mutableCopy();
            if (state.getBlock() instanceof CropBlock || state.getBlock() instanceof LeavesBlock) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            posMut.set(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (world.getBlockState(posMut).getBlock() instanceof CropBlock || state.getBlock() instanceof LeavesBlock) {
                                world.breakBlock(posMut, true);
                                stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
