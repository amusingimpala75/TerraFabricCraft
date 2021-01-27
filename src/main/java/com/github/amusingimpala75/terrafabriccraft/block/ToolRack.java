package com.github.amusingimpala75.terrafabriccraft.block;

import com.github.amusingimpala75.terrafabriccraft.block.entity.ToolRackBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ToolRack extends Block implements BlockEntityProvider {
    public ToolRack(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(DIRECTION, Direction.NORTH));
    }

    private static final EnumProperty<Direction> DIRECTION = EnumProperty.of("direction", Direction.class, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ((ToolRackBlockEntity)world.getBlockEntity(pos)).onUse(player, world, hand);
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ToolRackBlockEntity();
    }

}
