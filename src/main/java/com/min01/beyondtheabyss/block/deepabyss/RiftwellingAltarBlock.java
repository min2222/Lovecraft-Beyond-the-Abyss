package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateAltarItemPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
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

public class RiftwellingAltarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	public RiftwellingAltarBlock() 
	{
		super(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).lightLevel(value -> 15).noLootTable().isValidSpawn((pState, pLevel, pPos, pValue) -> false).noOcclusion());
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
	{
		return new RiftwellingAltarBlockEntity(pPos, pState);
	}
	
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
		BlockEntity blockEntity = pLevel.getBlockEntity(pPos);

		if(!(blockEntity instanceof RiftwellingAltarBlockEntity altar) || pPlayer.isShiftKeyDown())
		{
			return InteractionResult.FAIL;
		}

		ItemStack current = altar.getItem();
		ItemStack stack = pPlayer.getItemInHand(pHand);

		if(current.isEmpty())
		{
			altar.setItem(stack.copyWithCount(1));
			if(!pLevel.isClientSide)
			{
				BTANetwork.sendToAll(new UpdateAltarItemPacket(stack.copyWithCount(1), pPos));
			}
			if(!pPlayer.getAbilities().instabuild)
			{
				stack.shrink(1);
			}
		}
		else
		{
			if(!pPlayer.getAbilities().instabuild)
			{
				if(stack.isEmpty())
				{
					pPlayer.setItemInHand(pHand, current);
				}
				else if(!pPlayer.addItem(current))
				{
					pPlayer.drop(current, false);
				}
			}
			altar.setItem(ItemStack.EMPTY);
			if(!pLevel.isClientSide)
			{
				BTANetwork.sendToAll(new UpdateAltarItemPacket(ItemStack.EMPTY, pPos));
			}
		}
		return InteractionResult.PASS;
    }
	
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return createTickerHelper(pBlockEntityType, BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), RiftwellingAltarBlockEntity::tick);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
    	LevelAccessor level = pContext.getLevel();
    	BlockPos pos = pContext.getClickedPos();
    	return this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }
    
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
    	pBuilder.add(WATERLOGGED);
    }
}
