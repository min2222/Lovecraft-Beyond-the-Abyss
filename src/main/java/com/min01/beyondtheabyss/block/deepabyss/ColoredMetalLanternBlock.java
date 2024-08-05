package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ColoredMetalLanternBlock extends Block
{
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	   
	public ColoredMetalLanternBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).lightLevel(value -> value.getValue(LIT) ? 15 : 0));
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) 
	{
    	Level level = p_49820_.getLevel();
    	BlockPos blockPos = p_49820_.getClickedPos();
		return this.defaultBlockState().setValue(LIT, level.hasNeighborSignal(blockPos));
	}
	
	@Override
	public void neighborChanged(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_)
	{
		if(!p_55667_.isClientSide)
		{
			boolean flag = p_55666_.getValue(LIT);
			if(p_55667_.hasNeighborSignal(p_55668_))
			{
				p_55667_.setBlock(p_55668_, p_55666_.setValue(LIT, !flag), 2);
			}
		}
	}
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(LIT);
    }
}
