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

public abstract class AbstractMultiPartSkeletonBlock extends AbstractNoRotationLimitBoneBlock
{
	public static final EnumProperty<SkeletonPart> SKELETON_PART = EnumProperty.create("skeleton_part", SkeletonPart.class);
	
	public AbstractMultiPartSkeletonBlock(Properties p_49795_)
	{
		super(p_49795_);
		this.registerDefaultState(this.stateDefinition.any().setValue(SKELETON_PART, SkeletonPart.LOWER));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> p_152043_) 
	{
		super.createBlockStateDefinition(p_152043_);
		p_152043_.add(SKELETON_PART);
	}
	
	@Override
	public void playerWillDestroy(Level p_49505_, BlockPos p_49506_, BlockState p_49507_, Player p_49508_) 
	{
		if(!p_49505_.isClientSide)
		{
			SkeletonPart skeletonPart = p_49507_.getValue(SKELETON_PART);
			BlockPos blockpos = p_49506_.relative(this.getNeighbourDirection(skeletonPart, p_49507_.getValue(FACING)));
			BlockState blockstate = p_49505_.getBlockState(blockpos);
			if(blockstate.is(this))
			{
				p_49505_.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
				p_49505_.levelEvent(p_49508_, 2001, blockpos, Block.getId(blockstate));
			}
		}

		super.playerWillDestroy(p_49505_, p_49506_, p_49507_, p_49508_);
	}
	
	public Direction getNeighbourDirection(SkeletonPart p_49534_, Direction p_49535_)
	{
		return p_49534_ == SkeletonPart.LOWER ? p_49535_.getOpposite() : p_49535_;
	}
	
	@Override
	public void setPlacedBy(Level p_49499_, BlockPos p_49500_, BlockState p_49501_, @Nullable LivingEntity p_49502_, ItemStack p_49503_) 
	{
		super.setPlacedBy(p_49499_, p_49500_, p_49501_, p_49502_, p_49503_);
		if(!p_49499_.isClientSide)
		{
			Direction direction = this.getPartDirection(p_49501_);
			BlockPos blockpos = p_49500_.relative(direction);
			p_49499_.setBlock(blockpos, p_49501_.setValue(SKELETON_PART, SkeletonPart.UPPER), 3);
			p_49499_.blockUpdated(p_49500_, Blocks.AIR);
			p_49501_.updateNeighbourShapes(p_49499_, p_49500_, 3);
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
		   
		private SkeletonPart(String p_61339_)
		{
			this.name = p_61339_;
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
