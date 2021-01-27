package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraftcore.sounds.TerraFabricCraftSounds;
import com.github.amusingimpala75.terrafabriccraftcore.util.BlockCollapsingUtil;
import com.github.amusingimpala75.terrafabriccraft.block.propertyenums.SupportStatesEnum;
import com.github.amusingimpala75.terrafabriccraft.block.WoodenSupportBlock;
import com.github.amusingimpala75.terrafabriccraft.ducks.BlockDuck;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Block.class)
@SuppressWarnings("deprecation")
//TODO: Fix supporting to allow for if on other block then is supported
public abstract class MixinBlock extends AbstractBlock implements BlockDuck {

    @Unique
    private boolean collapses = false;

    @Unique
    private boolean instaCollapses = false;

    @Unique
    private Block convertBlock = null;

    public MixinBlock(Settings settings) {
        super(settings);
    }

    public Block canCollapse() {
        this.collapses = true;
        return (Block) (Object) this;
    }

    public Block doesInstantlyCollapse() {
        this.instaCollapses = true;
        return (Block) (Object) this;
    }

    public Block collapsesToOtherBlock(Block block) {
        this.convertBlock = block;
        return (Block) (Object) this;
    }

    public boolean collapses() {
        return this.collapses;
    }

    public boolean instaCollapses() {
        return instaCollapses;
    }

    public Block getCollapseBlock() {
        return this.convertBlock != null ? this.convertBlock : ((Block) (Object) this).equals(Blocks.STONE) ? Blocks.COBBLESTONE : (Block) (Object) this;
    }

    public boolean isSoil() {
        return ((Block) (Object) this).equals(Blocks.DIRT) || ((Block) (Object) this).equals(Blocks.GRASS_BLOCK);
    }

    @Override
    public boolean tryToCollapse(World world, BlockPos pos, float i) {
        return tryToFall(world, pos);
    }

    @Override
    public boolean doesCollapse() {
        return collapses() && !this.instaCollapses();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.instaCollapses && !isSupportedWithAirCheck(world, pos)) {
            fall(world, pos);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (this.instaCollapses) {
            world.getBlockTickScheduler().schedule(pos, (Block) (Object) this, 2);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        world.getBlockTickScheduler().schedule(pos, (Block) (Object) this, 2);
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public boolean isSupportedWithoutAirCheck(World world, BlockPos pos) {
        if (this.collapses) {
            for (int x = -4; x <= 4; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -4; z <= 4; z++) {
                        BlockState state = world.getBlockState(pos.up(y).north(z).east(x));
                        if (state.getBlock() instanceof WoodenSupportBlock) {
                            SupportStatesEnum supportState = state.get(WoodenSupportBlock.STATES);
                            if (supportState.equals(SupportStatesEnum.NORTH_SOUTH) || supportState.equals(SupportStatesEnum.EAST_WEST)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Inject(at=@At("TAIL"), method = "onBreak")
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo info) {
        new BlockCollapsingUtil().harvestBlock(world, player, pos.getX(), pos.getY(), pos.getZ(), 0);
    }

    private void fall(World world, BlockPos pos) {
        if (!world.isClient()) {
            world.setBlockState(pos, ((BlockDuck) (world.getBlockState(pos).getBlock())).getCollapseBlock().getDefaultState());
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
            world.spawnEntity(fallingBlockEntity);
            System.out.println("Falling!");
        } else {
            if (this.isSoil()) {
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        pos, // The position of where the sound will come from
                        TerraFabricCraftSounds.DIRT_SLIDE, // The sound that will play
                        SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                        1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );
            } else {
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        pos, // The position of where the sound will come from
                        TerraFabricCraftSounds.ROCK_SLIDE_LONG, // The sound that will play
                        SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                        1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );
            }
        }
    }

    private boolean tryToFall(World world, BlockPos pos) {
        if (!isSupportedWithoutAirCheck(world, pos)) {
            fall(world, pos);
            return true;
        }
        return false;
    }

    private boolean isSupportedWithAirCheck(World world, BlockPos pos) {
        if (!FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
            return true;
        }
        return this.isSupportedWithoutAirCheck(world, pos);
    }
}
