package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.ChainTrapBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChainTrapBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OPENED = BlockStateProperties.OPEN;
	
	public static final VoxelShape FLOOR_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final VoxelShape CEILING_AABB = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	public static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
	public static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
	public static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
	public static final VoxelShape WEST_AABB = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	
	public ChainTrapBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACE, AttachFace.FLOOR).setValue(OPENED, false));
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
	public RenderShape getRenderShape(BlockState pState)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) 
	{
		if(!pOldState.is(pState.getBlock()))
		{
			pLevel.setBlockAndUpdate(pPos, pState.setValue(OPENED, pLevel.hasNeighborSignal(pPos)));
		}
	}

	@Override
	public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston)
	{
		pLevel.setBlockAndUpdate(pPos, pState.setValue(OPENED, pLevel.hasNeighborSignal(pPos)));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) 
	{
		return new ChainTrapBlockEntity(pPos, pState);
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return createTicker(pLevel, pBlockEntityType, BTABlocks.CHAIN_TRAP_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<ChainTrapBlockEntity> pClientType)
    {
        return createTickerHelper(pServerType, pClientType, ChainTrapBlockEntity::update);
    }
    
    @SuppressWarnings("unchecked")
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker)
    {
    	return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
    	Level level = pContext.getLevel();
    	BlockPos pos = pContext.getClickedPos();
    	BlockState state = super.getStateForPlacement(pContext);
    	if(state != null)
    	{
        	return state.setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
    	}
    	return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
    }
    
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
    	pBuilder.add(WATERLOGGED, FACING, FACE, OPENED);
    }
}
