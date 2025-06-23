package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpineBoneTipBlock extends Block implements SimpleWaterloggedBlock
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	protected static final VoxelShape X_AABB = Block.box(0.0D, 7.0D, 7.0D, 16.0D, 9.0D, 9.0D);
	protected static final VoxelShape Y_AABB = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
	protected static final VoxelShape Z_AABB = Block.box(7.0D, 7.0D, 0.0D, 9.0D, 9.0D, 16.0D);
	
	public SpineBoneTipBlock() 
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).dynamicShape().noOcclusion().requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		switch(p_60555_.getValue(FACING))
		{
		case DOWN:
			return Y_AABB;
		case UP:
			return Y_AABB;
		case EAST:
			return X_AABB;
		case WEST:
			return X_AABB;
		case NORTH:
			return Z_AABB;
		case SOUTH:
			return Z_AABB;
		default:
			return Y_AABB;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) 
	{
    	LevelAccessor level = p_49820_.getLevel();
    	BlockPos blockPos = p_49820_.getClickedPos();
		return this.defaultBlockState().setValue(FACING, p_49820_.getClickedFace().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockPos).getType() == Fluids.WATER));
	}
	
	@Override
	public boolean isPathfindable(BlockState p_154341_, BlockGetter p_154342_, BlockPos p_154343_, PathComputationType p_154344_) 
	{
		return false;
	}
	
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(WATERLOGGED, FACING);
    }
}
