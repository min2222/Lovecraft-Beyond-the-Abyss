package com.min01.beyondtheabyss.blockentity;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class AnimatableBlockEntity extends BlockEntity
{
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public int tickCount;
	
	public AnimatableBlockEntity(BlockPos pPos, BlockState pBlockState) 
	{
		super(BTABlocks.ANIMATABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, AnimatableBlockEntity block)
	{
		++block.tickCount;
		block.idleAnimationState.updateWhen(state.getValue(BlockStateProperties.WATERLOGGED), block.tickCount);
	}
}
