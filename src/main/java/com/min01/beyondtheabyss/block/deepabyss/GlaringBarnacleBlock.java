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
	
	protected static final VoxelShape FLOOR_AABB = Block.box(4.5D, 0.0D, 4.5D, 11.5D, 6.0D, 11.5D);
	protected static final VoxelShape CEILING_AABB = Block.box(4.5D, 10.0D, 4.5D, 11.5D, 16.0D, 11.5D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 4.5D, 4.5D, 6.0D, 11.5D, 11.5D);
	protected static final VoxelShape NORTH_AABB = Block.box(4.5D, 4.5D, 10.0D, 11.5D, 11.5D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(4.5D, 4.5D, 0.0D, 11.5D, 11.5D, 6.0D);
	protected static final VoxelShape WEST_AABB = Block.box(10.0D, 4.5D, 4.5D, 16.0D, 11.5D, 11.5D);
	
	public GlaringBarnacleBlock(Properties p_154496_)
	{
		super(p_154496_);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) 
	{
		return new AnimatableBlockEntity(p_153215_, p_153216_);
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
	{
		switch(p_60555_.getValue(FACE))
		{
		case CEILING:
			return CEILING_AABB;
		case FLOOR:
			return FLOOR_AABB;
		case WALL:
			switch(p_60555_.getValue(FACING))
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
	public BlockState getStateForPlacement(BlockPlaceContext p_54302_)
	{
		BlockState state = super.getStateForPlacement(p_54302_);
		BlockPos pos = p_54302_.getClickedPos();
		Level level = p_54302_.getLevel();
		FluidState fluidState = level.getFluidState(pos);
		return state != null ? state.setValue(WATERLOGGED, fluidState.is(FluidTags.WATER)) : state;
	}
	
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) 
	{
		p_54663_.add(FACE, FACING, WATERLOGGED);
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_)
    {
        return createTicker(p_153273_, p_153275_, BTABlocks.ANIMATABLE_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<AnimatableBlockEntity> p_151990_)
    {
        return createTickerHelper(p_151989_, p_151990_, AnimatableBlockEntity::update);
    }
    
    @SuppressWarnings("unchecked")
	@Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_)
    {
    	return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }
}
