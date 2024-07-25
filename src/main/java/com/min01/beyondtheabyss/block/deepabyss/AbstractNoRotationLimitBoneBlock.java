package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractNoRotationLimitBoneBlock extends AbstractRotatedBoneBlock implements EntityBlock
{
	public AbstractNoRotationLimitBoneBlock(Properties p_49795_) 
	{
		super(p_49795_);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) 
	{
		return new NoRotationLimitBlockEntity(p_153215_, p_153216_);
	}
}
