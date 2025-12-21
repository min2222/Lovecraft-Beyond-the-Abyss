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

public class SpineBoneMiddleBlock extends Block implements SimpleWaterloggedBlock
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public static final VoxelShape X_AABB = Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
	public static final VoxelShape Y_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
	public static final VoxelShape Z_AABB = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
	
	public SpineBoneMiddleBlock() 
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.5F).dynamicShape().noOcclusion().requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		switch(pState.getValue(FACING))
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
	public BlockState getStateForPlacement(BlockPlaceContext pContext) 
	{
    	LevelAccessor level = pContext.getLevel();
    	BlockPos blockPos = pContext.getClickedPos();
		return this.defaultBlockState().setValue(FACING, pContext.getClickedFace().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockPos).getType() == Fluids.WATER));
	}
	
	@Override
	public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) 
	{
		return false;
	}
	
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
    	pBuilder.add(WATERLOGGED, FACING);
    }
}
