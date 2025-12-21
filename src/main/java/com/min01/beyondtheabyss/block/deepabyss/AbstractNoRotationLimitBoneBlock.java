package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractNoRotationLimitBoneBlock extends AbstractRotatedBoneBlock implements EntityBlock
{
	public AbstractNoRotationLimitBoneBlock(Properties pProperties) 
	{
		super(pProperties);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) 
	{
		return new NoRotationLimitBlockEntity(pPos, pState);
	}
}
