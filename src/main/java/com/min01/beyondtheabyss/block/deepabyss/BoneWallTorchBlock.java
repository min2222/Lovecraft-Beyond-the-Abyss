package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BoneWallTorchBlock extends WallTorchBlock implements EntityBlock
{
	public BoneWallTorchBlock(Properties pProperties) 
	{
		super(pProperties, ParticleTypes.FLAME);
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
