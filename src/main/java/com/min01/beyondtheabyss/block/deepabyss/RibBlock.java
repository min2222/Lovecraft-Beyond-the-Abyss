package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RibBlock extends AbstractRotatedBoneBlock
{
	protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 13.0D, 15.0D);
	protected static final VoxelShape Z_AXIS_AABB = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 13.0D, 16.0D);
	
	public RibBlock()
	{
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(1.2F));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		Direction direction = p_60555_.getValue(FACING);
		if(direction == Direction.EAST || direction == Direction.WEST)
		{
			return X_AXIS_AABB;
		}
		return Z_AXIS_AABB;
	}
}
