package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpineBoneMiddleBlock extends Block
{
	protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
	
	public SpineBoneMiddleBlock() 
	{
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(1.5F).dynamicShape().noOcclusion().requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		return AABB;
	}
}
