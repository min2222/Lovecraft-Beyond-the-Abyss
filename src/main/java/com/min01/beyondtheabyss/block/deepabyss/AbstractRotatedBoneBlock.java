package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;

public abstract class AbstractRotatedBoneBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public AbstractRotatedBoneBlock(Properties pProperties) 
	{
		super(pProperties.dynamicShape().noOcclusion().requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) 
	{
    	Direction direction = pContext.getHorizontalDirection().getOpposite();
    	LevelAccessor level = pContext.getLevel();
    	BlockPos blockPos = pContext.getClickedPos();
		return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER);
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
