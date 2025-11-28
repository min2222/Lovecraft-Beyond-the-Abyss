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
	
	protected static final VoxelShape FLOOR_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	protected static final VoxelShape CEILING_AABB = Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
	protected static final VoxelShape WEST_AABB = Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	
	public ChainTrapBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACE, AttachFace.FLOOR).setValue(OPENED, false));
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
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public void onPlace(BlockState p_57466_, Level p_57467_, BlockPos p_57468_, BlockState p_57469_, boolean p_57470_) 
	{
		if(!p_57469_.is(p_57466_.getBlock()))
		{
			p_57467_.setBlockAndUpdate(p_57468_, p_57466_.setValue(OPENED, p_57467_.hasNeighborSignal(p_57468_)));
		}
	}

	@Override
	public void neighborChanged(BlockState p_57457_, Level p_57458_, BlockPos p_57459_, Block p_57460_, BlockPos p_57461_, boolean p_57462_)
	{
		p_57458_.setBlockAndUpdate(p_57459_, p_57457_.setValue(OPENED, p_57458_.hasNeighborSignal(p_57461_)));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) 
	{
		return new ChainTrapBlockEntity(p_153215_, p_153216_);
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_)
    {
        return createTicker(p_153273_, p_153275_, BTABlocks.CHAIN_TRAP_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<ChainTrapBlockEntity> p_151990_)
    {
        return createTickerHelper(p_151989_, p_151990_, ChainTrapBlockEntity::update);
    }
    
    @SuppressWarnings("unchecked")
	@Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) 
    {
    	return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_)
    {
    	Level level = p_152019_.getLevel();
    	BlockPos pos = p_152019_.getClickedPos();
    	BlockState state = super.getStateForPlacement(p_152019_);
    	if(state != null)
    	{
        	return state.setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
    	}
    	return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
    }
    
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(WATERLOGGED, FACING, FACE, OPENED);
    }
}
