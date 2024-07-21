package com.min01.beyondtheabyss.blockentity;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NoRotationLimitBlockEntity extends BlockEntity
{
	public NoRotationLimitBlockEntity(BlockPos p_155229_, BlockState p_155230_) 
	{
		super(BTABlocks.NO_ROTATION_LIMIT_BLOCK_ENTITY.get(), p_155229_, p_155230_);
	}
}
