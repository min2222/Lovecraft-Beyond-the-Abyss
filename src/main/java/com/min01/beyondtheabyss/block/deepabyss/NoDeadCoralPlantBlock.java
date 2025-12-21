package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CoralPlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NoDeadCoralPlantBlock extends CoralPlantBlock
{
	public NoDeadCoralPlantBlock(Properties pProperties) 
	{
		super(null, pProperties);
	}
	
	@Override
	public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) 
	{
		
	}
}
