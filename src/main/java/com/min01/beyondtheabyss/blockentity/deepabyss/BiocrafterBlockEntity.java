package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BiocrafterBlockEntity extends BlockEntity
{
	public int animationTick;
	public int tickCount;
	
	public BiocrafterBlockEntity(BlockPos p_155229_, BlockState p_155230_)
	{
		super(BTABlocks.BIOCRAFTER_BLOCK_ENTITY.get(), p_155229_, p_155230_);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, BiocrafterBlockEntity crafter)
	{
		++crafter.tickCount;
	}
	
	@Override
	protected void saveAdditional(CompoundTag p_187471_)
	{
		super.saveAdditional(p_187471_);
		p_187471_.putInt("AnimationTick", this.animationTick);
	}
	
	@Override
	public void load(CompoundTag p_155245_)
	{
		super.load(p_155245_);
		if(p_155245_.contains("AnimationTick"))
		{
			this.animationTick = p_155245_.getInt("AnimationTick");
		}
	}
}
