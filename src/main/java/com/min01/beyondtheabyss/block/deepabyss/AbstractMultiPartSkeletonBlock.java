package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;

public abstract class AbstractMultiPartSkeletonBlock extends AbstractNoRotationLimitBoneBlock
{
	public static final EnumProperty<SkeletonPart> SKELETON_PART = EnumProperty.create("skeleton_part", SkeletonPart.class);
	
	public AbstractMultiPartSkeletonBlock(Properties pProperties)
	{
		super(pProperties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SKELETON_PART, SkeletonPart.LOWER));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) 
	{
		super.createBlockStateDefinition(pBuilder);
		pBuilder.add(SKELETON_PART);
	}
	
	@Override
	public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) 
	{
		if(!pLevel.isClientSide)
		{
			SkeletonPart skeletonPart = pState.getValue(SKELETON_PART);
			BlockPos blockPos = pPos.relative(this.getNeighbourDirection(skeletonPart, pState.getValue(FACING)));
			BlockState blockState = pLevel.getBlockState(blockPos);
			if(blockState.is(this))
			{
				pLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
				pLevel.levelEvent(pPlayer, 2001, blockPos, Block.getId(blockState));
			}
		}

		super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
	}
	
	public Direction getNeighbourDirection(SkeletonPart part, Direction direction)
	{
		return part == SkeletonPart.LOWER ? direction.getOpposite() : direction;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) 
	{
		if(!pLevel.isClientSide)
		{
			Direction direction = this.getPartDirection(pState);
			BlockPos blockPos = pPos.relative(direction);
			boolean flag = pLevel.isEmptyBlock(blockPos) || pLevel.getBlockState(blockPos).liquid();
			if(flag)
			{
				pLevel.setBlock(blockPos, pState.setValue(SKELETON_PART, SkeletonPart.UPPER).setValue(WATERLOGGED, pLevel.getFluidState(blockPos).getType() == Fluids.WATER), 3);
				pLevel.blockUpdated(pPos, Blocks.AIR);
				pState.updateNeighbourShapes(pLevel, pPos, 3);
			}
		}
	}
	
	public Direction getPartDirection(BlockState state)
	{
		return state.getValue(FACING).getOpposite();
	}
	
	public static enum SkeletonPart implements StringRepresentable
	{
		UPPER("upper"),
		LOWER("lower");

		private final String name;
		   
		private SkeletonPart(String name)
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
