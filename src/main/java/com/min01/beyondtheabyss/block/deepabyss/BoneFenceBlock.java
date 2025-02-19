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
	
	public BoneFenceBlock(Properties p_53302_) 
	{
		super(p_53302_);
		this.registerDefaultState(this.stateDefinition.any().setValue(CONNECTED, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> p_152043_) 
	{
		super.createBlockStateDefinition(p_152043_);
		p_152043_.add(CONNECTED);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_53304_) 
	{
    	LevelAccessor level = p_53304_.getLevel();
    	BlockPos blockPos = p_53304_.getClickedPos();
    	BlockState above = level.getBlockState(blockPos.relative(Direction.UP, 1));
		return super.getStateForPlacement(p_53304_).setValue(CONNECTED, above.is(BTABlocks.BONE_FENCE.get()));
	}
	
    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_)
    {
    	BlockState above = p_60544_.getBlockState(p_60545_.relative(Direction.UP, 1));
    	return super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_).setValue(CONNECTED, above.is(BTABlocks.BONE_FENCE.get()));
    }
}
