package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

//FIXME waterlogging
public class SmallBoneBlock extends RotatedPillarBlock
{
	protected static final VoxelShape X_AABB = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
	protected static final VoxelShape Y_AABB = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	protected static final VoxelShape Z_AABB = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
	
	public SmallBoneBlock() 
	{
		super(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).noOcclusion());
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		switch(p_60555_.getValue(AXIS))
		{
		case X:
			return X_AABB;
		case Y:
			return Y_AABB;
		case Z:
			return Z_AABB;
		default :
			return Y_AABB;
		}
	}
}
