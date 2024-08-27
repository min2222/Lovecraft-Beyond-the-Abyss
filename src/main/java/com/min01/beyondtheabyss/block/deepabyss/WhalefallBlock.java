package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WhalefallBlock extends FallingBlock
{
	public WhalefallBlock(Properties p_53205_) 
	{
		super(p_53205_);
	}

	@Override
	public int getDustColor(BlockState p_53238_, BlockGetter p_53239_, BlockPos p_53240_)
	{
		return 12039318;
	}
}
