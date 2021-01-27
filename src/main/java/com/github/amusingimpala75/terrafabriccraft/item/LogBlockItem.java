package com.github.amusingimpala75.terrafabriccraft.item;

import com.github.amusingimpala75.terrafabriccraft.block.LogPile;
import com.github.amusingimpala75.terrafabriccraft.block.TerraFabricCraftBlocks;
import com.github.amusingimpala75.terrafabriccraft.block.entity.LogPileEntity;
import com.github.amusingimpala75.terrafabriccraft.mixin.InvokerBlockItemPlace;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LogBlockItem extends BlockItem {
    public LogBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (context.getPlayer().isSneaking()) {
            if (context.canPlace()) {
                ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
                if (itemPlacementContext != null) {
                    BlockState blockState = TerraFabricCraftBlocks.LOG_PILE.getPlacementState(context);
                    if (blockState != null && this.place(itemPlacementContext, blockState)) {
                        BlockPos blockPos = itemPlacementContext.getBlockPos();
                        World world = itemPlacementContext.getWorld();
                        PlayerEntity playerEntity = itemPlacementContext.getPlayer();
                        ItemStack itemStack = itemPlacementContext.getStack();
                        Block block = blockState.getBlock();
                        blockState = ((InvokerBlockItemPlace) this).invokePlaceFromTag(blockPos, world, itemStack, blockState);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState);
                        block.onPlaced(world, blockPos, blockState, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);
                        }

                        BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
                        world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                        if (playerEntity == null || !playerEntity.abilities.creativeMode) {
                            itemStack.decrement(1);
                        }

                        return ActionResult.success(world.isClient);
                    }
                }
            }
        }
        return super.place(context);
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        if (context.getPlayer().isSneaking()) {
            return context.getWorld().setBlockState(context.getBlockPos(), TerraFabricCraftBlocks.LOG_PILE.getDefaultState());
        }
        return super.place(context, state);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if (state.getBlock() instanceof LogPile) {
            if (world.getBlockEntity(pos) instanceof LogPileEntity) {
                ((LogPileEntity) (world.getBlockEntity(pos))).setStack(0, new ItemStack(this));
                if (player.isCreative()) {
                    stack.decrement(1);
                }
            }
        }
        return super.postPlacement(pos, world, player, stack, state);
    }
}
