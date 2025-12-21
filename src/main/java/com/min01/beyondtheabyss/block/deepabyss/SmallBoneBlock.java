package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallBoneBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final VoxelShape X_AABB = Block.box(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
	public static final VoxelShape Y_AABB = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	public static final VoxelShape Z_AABB = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
	
	public SmallBoneBlock() 
	{
		super(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).noOcclusion());
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		switch(pState.getValue(AXIS))
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
	
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
    	LevelAccessor level = pContext.getLevel();
    	BlockPos pos = pContext.getClickedPos();
    	return super.getStateForPlacement(pContext).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }
    
	@Override
	public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) 
	{
		return false;
	}
    
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
    	super.createBlockStateDefinition(pBuilder);
    	pBuilder.add(WATERLOGGED);
    }
}
