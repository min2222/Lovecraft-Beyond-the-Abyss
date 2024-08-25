package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SittingSkeletonBlock extends AbstractMultiPartSkeletonBlock
{
	protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 15.0D);
	protected static final VoxelShape Z_AXIS_AABB = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D);
	
	protected static final VoxelShape X_AXIS_UPPER_AABB = Block.box(2.0D, 0.0D, 4.0D, 13.0D, 7.0D, 12.0D);
	protected static final VoxelShape Z_AXIS_UPPER_AABB = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 7.0D, 13.0D);
	
	public SittingSkeletonBlock()
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(2.0F));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		Direction direction = p_60555_.getValue(FACING);
		SkeletonPart part = p_60555_.getValue(SKELETON_PART);
		if(part == SkeletonPart.UPPER)
		{
			if(direction == Direction.EAST || direction == Direction.WEST)
			{
				return X_AXIS_UPPER_AABB;
			}
			return Z_AXIS_UPPER_AABB;
		}
		else
		{
			if(direction == Direction.EAST || direction == Direction.WEST)
			{
				return X_AXIS_AABB;
			}
			return Z_AXIS_AABB;
		}
	}
	
	@Override
	public Direction getNeighbourDirection(SkeletonPart p_49534_, Direction p_49535_) 
	{
		return p_49534_ == SkeletonPart.LOWER ? Direction.UP : Direction.DOWN;
	}
	
	@Override
	public Direction getPartDirection(BlockState state) 
	{
		return Direction.UP;
	}
}
