package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class JawBoneBlock extends AbstractRotatedBoneBlock
{
	public static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final VoxelShape Y_AABB = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	
	public static final BooleanProperty UP = BooleanProperty.create("up");
	
	public JawBoneBlock()
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.5F));
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		if(pState.getValue(UP))
		{
			return Y_AABB;
		}
		return AABB;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) 
	{
		return super.getStateForPlacement(pContext).setValue(UP, pContext.getClickedFace().getOpposite().equals(Direction.UP));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder)
	{
		super.createBlockStateDefinition(pBuilder);
		pBuilder.add(UP);
	}
}
