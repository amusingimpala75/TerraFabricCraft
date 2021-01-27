package com.github.amusingimpala75.terrafabriccraft.block;

import com.github.amusingimpala75.terrafabriccraft.block.propertyenums.SupportStatesEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
//TODO: Improve placement && states
public class WoodenSupportBlock extends Block {

    public static final EnumProperty<SupportStatesEnum> STATES = TerraFabricCraftBlocks.SUPPORT_STATES;
    public static final VoxelShape VERTICAL_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(VERTICAL_SHAPE, Block.createCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 16.0D));
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(VERTICAL_SHAPE, Block.createCuboidShape(4.0D, 8.0D, 4.0D, 16.0D, 16.0D, 12.0D));
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(VERTICAL_SHAPE, Block.createCuboidShape(4.0D, 8.0D, 0.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(VERTICAL_SHAPE, Block.createCuboidShape(0.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape NORTH_SOUTH_SHAPE = Block.createCuboidShape(4.0D, 8.0D, 0.0D, 12.0D, 16.0D, 16.0D);
    public static final VoxelShape EAST_WEST_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 4.0D, 16.0D, 16.0D, 12.0D);

    public WoodenSupportBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(STATES, SupportStatesEnum.VERTICAL));
    }

    @Override
    public StateManager<Block, BlockState> getStateManager() {
        return super.getStateManager();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STATES);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SupportStatesEnum supportState = state.get(STATES);
        switch (supportState) {
            case SOUTH:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case NORTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case NORTH_SOUTH:
                return NORTH_SOUTH_SHAPE;
            case EAST_WEST:
                return EAST_WEST_SHAPE;
            default:
                return VERTICAL_SHAPE;
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        if (blockBelow.isIn(TerraFabricCraftBlocks.SUPPORT_UNPLACEABLE) && !(world.getBlockState(pos.north()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.east()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.south()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.west()).getBlock() instanceof WoodenSupportBlock)) {
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        if ((world.getBlockState(pos.north()).getBlock() instanceof WoodenSupportBlock || world.getBlockState(pos.south()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock)) {
            return this.getDefaultState().with(STATES, SupportStatesEnum.NORTH_SOUTH);
        } else if ((world.getBlockState(pos.east()).getBlock() instanceof WoodenSupportBlock || world.getBlockState(pos.west()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock)) {
            return this.getDefaultState().with(STATES, SupportStatesEnum.EAST_WEST);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.north()).getBlock() instanceof WoodenSupportBlock) {
            return this.getDefaultState().with(STATES, SupportStatesEnum.NORTH);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.east()).getBlock() instanceof WoodenSupportBlock) {
            return this.getDefaultState().with(STATES, SupportStatesEnum.EAST);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.south()).getBlock() instanceof WoodenSupportBlock) {
            return this.getDefaultState().with(STATES, SupportStatesEnum.SOUTH);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.west()).getBlock() instanceof WoodenSupportBlock) {
            return this.getDefaultState().with(STATES, SupportStatesEnum.WEST);
        } else {
            return this.getDefaultState().with(STATES, SupportStatesEnum.VERTICAL);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        BlockState setState;
        if ((world.getBlockState(pos.north()).getBlock() instanceof WoodenSupportBlock || world.getBlockState(pos.south()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock)) {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.NORTH_SOUTH);
        } else if ((world.getBlockState(pos.east()).getBlock() instanceof WoodenSupportBlock || world.getBlockState(pos.west()).getBlock() instanceof WoodenSupportBlock) && !(world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock)) {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.EAST_WEST);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.north()).getBlock() instanceof WoodenSupportBlock) {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.NORTH);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.east()).getBlock() instanceof WoodenSupportBlock) {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.EAST);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.south()).getBlock() instanceof WoodenSupportBlock) {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.SOUTH);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.west()).getBlock() instanceof WoodenSupportBlock) {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.WEST);
        } else {
            setState = this.getDefaultState().with(STATES, SupportStatesEnum.VERTICAL);
        }
        if (!world.getBlockState(pos).equals(setState)) {
            world.setBlockState(pos, setState);
        }
    }

    private static boolean isSupportLegValid(BlockPos pos, World world) {
        if (world.getBlockState(pos).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.down()).getBlock() instanceof WoodenSupportBlock && world.getBlockState(pos.down(2)).getBlock() instanceof WoodenSupportBlock) {
            return true;
        }
        return false;
    }
}
