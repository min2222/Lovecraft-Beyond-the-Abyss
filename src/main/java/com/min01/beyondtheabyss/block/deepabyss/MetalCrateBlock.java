package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.blockentity.deepabyss.MetalCrateBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class MetalCrateBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public MetalCrateBlock() 
	{
		super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
	}

	@Override
	public InteractionResult use(BlockState p_49069_, Level p_49070_, BlockPos p_49071_, Player p_49072_, InteractionHand p_49073_, BlockHitResult p_49074_)
	{
		if(p_49070_.isClientSide) 
		{
			return InteractionResult.SUCCESS;
		}
		else
		{
			BlockEntity blockentity = p_49070_.getBlockEntity(p_49071_);
			if(blockentity instanceof MetalCrateBlockEntity)
			{
				p_49072_.openMenu((MetalCrateBlockEntity) blockentity);
			}
			return InteractionResult.CONSUME;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState p_49076_, Level p_49077_, BlockPos p_49078_, BlockState p_49079_, boolean p_49080_) 
	{
		if(!p_49076_.is(p_49079_.getBlock()))
		{
			BlockEntity blockentity = p_49077_.getBlockEntity(p_49078_);
			if(blockentity instanceof Container)
			{
				Containers.dropContents(p_49077_, p_49078_, (Container) blockentity);
				p_49077_.updateNeighbourForOutputSignal(p_49078_, this);
			}
			super.onRemove(p_49076_, p_49077_, p_49078_, p_49079_, p_49080_);
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) 
	{
    	LevelAccessor level = p_49820_.getLevel();
    	BlockPos blockPos = p_49820_.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockPos).getType() == Fluids.WATER));
	}
	
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(WATERLOGGED);
    }

	@Override
	public void tick(BlockState p_220758_, ServerLevel p_220759_, BlockPos p_220760_, RandomSource p_220761_) 
	{
		BlockEntity blockentity = p_220759_.getBlockEntity(p_220760_);
		if(blockentity instanceof MetalCrateBlockEntity) 
		{
			((MetalCrateBlockEntity) blockentity).recheckOpen();
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_)
	{
		return new MetalCrateBlockEntity(p_153215_, p_153216_);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49090_) 
	{
		return RenderShape.MODEL;
	}

	@Override
	public void setPlacedBy(Level p_49052_, BlockPos p_49053_, BlockState p_49054_, @Nullable LivingEntity p_49055_, ItemStack p_49056_) 
	{
		if(p_49056_.hasCustomHoverName()) 
		{
			BlockEntity blockentity = p_49052_.getBlockEntity(p_49053_);
			if(blockentity instanceof MetalCrateBlockEntity) 
			{
				((MetalCrateBlockEntity) blockentity).setCustomName(p_49056_.getHoverName());
			}
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState p_49058_) 
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState p_49065_, Level p_49066_, BlockPos p_49067_) 
	{
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(p_49066_.getBlockEntity(p_49067_));
	}
}
