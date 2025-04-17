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
	protected static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
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
	protected boolean canAttachTo(BlockState p_153455_)
	{
		return p_153455_.is(BTABlocks.ROT_SOIL.get()) || p_153455_.is(this) || p_153455_.is(BTABlocks.TOOTHVINE_PLANT.get());
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		return AABB;
	}
}
