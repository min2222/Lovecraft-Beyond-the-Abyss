package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BonePilesBlock extends AbstractNoRotationLimitBoneBlock
{
	public static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);
	
	public BonePilesBlock()
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(2.0F));
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		return AABB;
	}
}
