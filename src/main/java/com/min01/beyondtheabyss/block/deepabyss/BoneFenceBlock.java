package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BoneFenceBlock extends FenceBlock
{
	public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
	
	public BoneFenceBlock(Properties pProperties) 
	{
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(CONNECTED, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) 
	{
		super.createBlockStateDefinition(pBuilder);
		pBuilder.add(CONNECTED);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) 
	{
    	LevelAccessor level = pContext.getLevel();
    	BlockPos blockPos = pContext.getClickedPos();
    	BlockState above = level.getBlockState(blockPos.relative(Direction.UP, 1));
		return super.getStateForPlacement(pContext).setValue(CONNECTED, above.is(BTABlocks.BONE_FENCE.get()));
	}
	
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos)
    {
    	BlockState above = pLevel.getBlockState(pCurrentPos.relative(Direction.UP, 1));
    	return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos).setValue(CONNECTED, above.is(BTABlocks.BONE_FENCE.get()));
    }
}
