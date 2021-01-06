package com.github.amusingimpala75.terrafabriccraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

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
}
