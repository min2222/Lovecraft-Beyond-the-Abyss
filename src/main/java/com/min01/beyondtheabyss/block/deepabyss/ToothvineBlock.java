package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.misc.BTADamageSource;
import com.min01.beyondtheabyss.misc.BTATags;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToothvineBlock extends KelpBlock
{
	public static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
	public ToothvineBlock()
	{
		super(BlockBehaviour.Properties.of().noCollission().sound(SoundType.CROP));
	}
	
	@Override
	protected Block getBodyBlock() 
	{
		return BTABlocks.TOOTHVINE_PLANT.get();
	}
	
	@Override
	protected boolean canAttachTo(BlockState pState)
	{
		return pState.is(BTABlocks.ROT_SOIL.get()) || pState.is(this) || pState.is(BTABlocks.TOOTHVINE_PLANT.get());
	}
	
	@Override
	public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity)
	{
		if(pEntity instanceof LivingEntity && !pEntity.getType().is(BTATags.DEATH_VALLEY_CREATURES)) 
		{
			pEntity.makeStuckInBlock(pState, new Vec3((double)0.8F, 0.75D, (double)0.8F));
			if(!pLevel.isClientSide && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) 
			{
				double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
				double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
				if(d0 >= (double)0.003F || d1 >= (double)0.003F)
				{
					pEntity.hurt(BTADamageSource.causeToothVineDamage(pLevel.registryAccess()), 1.5F);
				}
			}
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		return AABB;
	}
}
