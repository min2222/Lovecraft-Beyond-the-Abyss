package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.BiocrafterBlock;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BiocrafterBlockEntity extends BlockEntity
{
	public final SmoothAnimationState processingAnimationState = new SmoothAnimationState();
	public int tickCount;
	
	public BiocrafterBlockEntity(BlockPos p_155229_, BlockState p_155230_)
	{
		super(BTABlocks.BIOCRAFTER_BLOCK_ENTITY.get(), p_155229_, p_155230_);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, BiocrafterBlockEntity crafter)
	{
		++crafter.tickCount;
		crafter.processingAnimationState.updateWhen(state.getValue(BiocrafterBlock.PROCESSING), crafter.tickCount);
	}
	
	@Override
	protected void saveAdditional(CompoundTag p_187471_)
	{
		super.saveAdditional(p_187471_);
	}
	
	@Override
	public void load(CompoundTag p_155245_)
	{
		super.load(p_155245_);
	}
}
