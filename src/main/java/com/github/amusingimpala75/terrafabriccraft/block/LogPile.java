package com.github.amusingimpala75.terrafabriccraft.block;

import com.github.amusingimpala75.terrafabriccraft.block.entity.LogPileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
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

/* TODO: Implement charcoal making */
public class LogPile extends Block implements BlockEntityProvider {

    public LogPile(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(DIRECTION, Direction.Axis.Z));
    }

    public static final EnumProperty<Direction.Axis> DIRECTION = EnumProperty.of("direction", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LogPileEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof LogPileEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)be);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(DIRECTION, ctx.getPlayerFacing().getAxis());
    }
}
