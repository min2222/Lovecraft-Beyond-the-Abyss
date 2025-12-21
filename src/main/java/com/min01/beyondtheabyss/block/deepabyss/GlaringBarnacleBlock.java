package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.AnimatableBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlaringBarnacleBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public static final VoxelShape FLOOR_AABB = Block.box(4.5D, 0.0D, 4.5D, 11.5D, 6.0D, 11.5D);
	public static final VoxelShape CEILING_AABB = Block.box(4.5D, 10.0D, 4.5D, 11.5D, 16.0D, 11.5D);
	public static final VoxelShape EAST_AABB = Block.box(0.0D, 4.5D, 4.5D, 6.0D, 11.5D, 11.5D);
	public static final VoxelShape NORTH_AABB = Block.box(4.5D, 4.5D, 10.0D, 11.5D, 11.5D, 16.0D);
	public static final VoxelShape SOUTH_AABB = Block.box(4.5D, 4.5D, 0.0D, 11.5D, 11.5D, 6.0D);
	public static final VoxelShape WEST_AABB = Block.box(10.0D, 4.5D, 4.5D, 16.0D, 11.5D, 11.5D);
	
	public GlaringBarnacleBlock(Properties pProperties)
	{
		super(pProperties);
	}

	@Override
	public RenderShape getRenderShape(BlockState pState)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) 
	{
		return new AnimatableBlockEntity(pPos, pState);
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		switch(pState.getValue(FACE))
		{
		case CEILING:
			return CEILING_AABB;
		case FLOOR:
			return FLOOR_AABB;
		case WALL:
			switch(pState.getValue(FACING))
			{
			case EAST:
				return EAST_AABB;
			case NORTH:
				return NORTH_AABB;
			case SOUTH:
				return SOUTH_AABB;
			case WEST:
				return WEST_AABB;
			default:
				return FLOOR_AABB;
			}
		default:
			return FLOOR_AABB;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext)
	{
		BlockState state = super.getStateForPlacement(pContext);
		BlockPos pos = pContext.getClickedPos();
		Level level = pContext.getLevel();
		FluidState fluidState = level.getFluidState(pos);
		return state != null ? state.setValue(WATERLOGGED, fluidState.is(FluidTags.WATER)) : state;
	}
	
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) 
	{
		pBuilder.add(FACE, FACING, WATERLOGGED);
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return createTicker(pLevel, pBlockEntityType, BTABlocks.ANIMATABLE_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<AnimatableBlockEntity> pClientType)
    {
        return createTickerHelper(pServerType, pClientType, AnimatableBlockEntity::update);
    }
    
    @SuppressWarnings("unchecked")
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker)
    {
    	return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }
}
