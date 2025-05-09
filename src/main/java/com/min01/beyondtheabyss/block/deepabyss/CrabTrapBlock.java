package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.CrabTrapBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class CrabTrapBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OPENED = BlockStateProperties.OPEN;
	
	public CrabTrapBlock() 
	{
		super(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(OPENED, false));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_)
	{
		return new CrabTrapBlockEntity(p_153215_, p_153216_);
	}
	
	@Override
	public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) 
	{
		if(p_60504_.getBlockEntity(p_60505_) instanceof CrabTrapBlockEntity trap)
		{
			if(trap.animationTick <= 0)
			{
				p_60504_.setBlockAndUpdate(p_60505_, p_60503_.setValue(OPENED, !p_60503_.getValue(OPENED)));
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_)
    {
        return createTicker(p_153273_, p_153275_, BTABlocks.CRAB_TRAP_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<CrabTrapBlockEntity> p_151990_)
    {
        return createTickerHelper(p_151989_, p_151990_, CrabTrapBlockEntity::update);
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
    	return this.defaultBlockState().setValue(FACING, p_152019_.getHorizontalDirection()).setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
    }
    
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(WATERLOGGED, FACING, OPENED);
    }
}
