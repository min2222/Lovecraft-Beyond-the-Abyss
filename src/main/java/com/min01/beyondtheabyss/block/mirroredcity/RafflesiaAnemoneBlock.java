package com.min01.beyondtheabyss.block.mirroredcity;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RafflesiaAnemoneBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public static final VoxelShape FLOOR_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
	public static final VoxelShape CEILING_AABB = Block.box(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	public static final VoxelShape EAST_AABB = Block.box(0.0D, 2.0D, 2.0D, 4.0D, 14.0D, 14.0D);
	public static final VoxelShape NORTH_AABB = Block.box(2.0D, 2.0D, 12.0D, 14.0D, 14.0D, 16.0D);
	public static final VoxelShape SOUTH_AABB = Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 4.0D);
	public static final VoxelShape WEST_AABB = Block.box(12.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);
	
	public RafflesiaAnemoneBlock(Properties pProperties)
	{
		super(pProperties);
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		switch(pState.getValue(FACE))
		{
		case CEILING:
			return CEILING_AABB;
		case FLOOR:
			return FLOOR_AABB;
		case WALL:
			switch(pState.getValue(FACING))
			{
			case EAST:
				return EAST_AABB;
			case NORTH:
				return NORTH_AABB;
			case SOUTH:
				return SOUTH_AABB;
			case WEST:
				return WEST_AABB;
			default:
				return FLOOR_AABB;
			}
		default:
			return FLOOR_AABB;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext)
	{
		BlockState state = super.getStateForPlacement(pContext);
		BlockPos pos = pContext.getClickedPos();
		Level level = pContext.getLevel();
		FluidState fluidState = level.getFluidState(pos);
		return state != null ? state.setValue(WATERLOGGED, fluidState.is(FluidTags.WATER)) : state;
	}
	
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) 
	{
		pBuilder.add(FACE, FACING, WATERLOGGED);
	}
}
