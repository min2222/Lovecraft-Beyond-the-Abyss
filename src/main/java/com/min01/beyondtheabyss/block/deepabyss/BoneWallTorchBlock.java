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
	public BoneWallTorchBlock(Properties p_57491_) 
	{
		super(p_57491_, ParticleTypes.FLAME);
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
