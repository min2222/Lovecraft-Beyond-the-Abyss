package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		return AABB;
	}
}
