package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.CrabTrapBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrabTrapBlockEntity extends BlockEntity
{
	public final AnimationState openAnimationState = new AnimationState();
	public final AnimationState closeAnimationState = new AnimationState();
	public int animationTick;
	public int tickCount;
	
	public CrabTrapBlockEntity(BlockPos p_155229_, BlockState p_155230_)
	{
		super(BTABlocks.CRAB_TRAP_BLOCK_ENTITY.get(), p_155229_, p_155230_);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, CrabTrapBlockEntity trap)
	{
		++trap.tickCount;
		boolean opened = state.getValue(CrabTrapBlock.OPENED);
		if(!trap.openAnimationState.isStarted() && opened)
		{
			trap.closeAnimationState.stop();
			trap.openAnimationState.startIfStopped(trap.tickCount);
			trap.animationTick = 10;
		}
		if(trap.openAnimationState.isStarted() && !opened)
		{
			trap.openAnimationState.stop();
			trap.closeAnimationState.startIfStopped(trap.tickCount);
			trap.animationTick = 10;
		}
		if(trap.animationTick > 0)
		{
			trap.animationTick--;
		}
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
