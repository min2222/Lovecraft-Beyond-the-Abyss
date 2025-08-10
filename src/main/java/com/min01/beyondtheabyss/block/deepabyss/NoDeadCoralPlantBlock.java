package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CoralPlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NoDeadCoralPlantBlock extends CoralPlantBlock
{
	public NoDeadCoralPlantBlock(Properties p_52176_) 
	{
		super(null, p_52176_);
	}
	
	@Override
	public void tick(BlockState p_221030_, ServerLevel p_221031_, BlockPos p_221032_, RandomSource p_221033_) 
	{
		
	}
}
