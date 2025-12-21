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

public class FangSkullBlock extends AbstractNoRotationLimitBoneBlock
{
	public static final VoxelShape X_AXIS_AABB = Block.box(2.0D, 0.0D, 4.0D, 15.0D, 9.0D, 12.0D);
	public static final VoxelShape Z_AXIS_AABB = Block.box(4.0D, 0.0D, 2.0D, 12.0D, 9.0D, 15.0D);
	
	public FangSkullBlock()
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.8F));
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		Direction direction = pState.getValue(FACING);
		if(direction == Direction.EAST || direction == Direction.WEST)
		{
			return X_AXIS_AABB;
		}
		return Z_AXIS_AABB;
	}
}
