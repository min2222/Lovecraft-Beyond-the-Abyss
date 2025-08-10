package com.min01.beyondtheabyss.blockentity;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class AnimatableBlockEntity extends BlockEntity
{
	public final AnimationState idleAnimationState = new AnimationState();
	public int tickCount;
	
	public AnimatableBlockEntity(BlockPos p_155229_, BlockState p_155230_) 
	{
		super(BTABlocks.ANIMATABLE_BLOCK_ENTITY.get(), p_155229_, p_155230_);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, AnimatableBlockEntity block)
	{
		++block.tickCount;
		block.idleAnimationState.animateWhen(state.getValue(BlockStateProperties.WATERLOGGED), block.tickCount);
	}
}
