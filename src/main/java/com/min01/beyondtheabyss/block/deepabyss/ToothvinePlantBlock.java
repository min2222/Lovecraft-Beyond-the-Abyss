package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.misc.BTADamageSource;
import com.min01.beyondtheabyss.misc.BTATags;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToothvinePlantBlock extends KelpPlantBlock
{
	public static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	public static final EnumProperty<VineState> VINE_STATE = EnumProperty.create("state", VineState.class);
	
	public ToothvinePlantBlock()
	{
		super(BlockBehaviour.Properties.of().noCollission().sound(SoundType.CROP));
	}
	
	@Override
	protected GrowingPlantHeadBlock getHeadBlock() 
	{
		return (GrowingPlantHeadBlock) BTABlocks.TOOTHVINE.get();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext)
	{
		return super.getStateForPlacement(pContext).setValue(VINE_STATE, this.getVineState(pContext.getClickedPos(), pContext.getLevel()));
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
	
	public VineState getVineState(BlockPos pos, Level level) 
	{
		if(level.getBlockState(pos.relative(this.growthDirection)).is(BTABlocks.TOOTHVINE.get()) && !level.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(BTABlocks.TOOTHVINE.get()))
		{
			return VineState.BASE;
		}
		else if(level.getBlockState(pos.relative(this.growthDirection)).is(BTABlocks.TOOTHVINE.get()) && level.getBlockState(pos.relative(this.growthDirection.getOpposite())).is(BTABlocks.TOOTHVINE.get()))
		{
			if(level.random.nextBoolean())
			{
				return VineState.VARIANT_1;
			}
			else
			{
				return VineState.VARIANT_2;
			}
		}
		return VineState.BASE;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) 
	{
		pBuilder.add(VINE_STATE);
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		return AABB;
	}
	
	public static enum VineState implements StringRepresentable
	{
		BASE("base"),
		VARIANT_1("variant_1"),
		VARIANT_2("variant_2");

		private final String name;
		
		private VineState(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return this.name;
		}

		@Override
		public String getSerializedName()
		{
			return this.name;
		}
	}
}
