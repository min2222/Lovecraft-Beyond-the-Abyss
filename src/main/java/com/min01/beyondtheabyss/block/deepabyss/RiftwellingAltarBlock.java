package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;

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
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) 
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);

		if(!(blockEntity instanceof RiftwellingAltarBlockEntity altar) || player.isShiftKeyDown())
		{
			return InteractionResult.FAIL;
		}

		ItemStack currentStack = altar.getItem();
		ItemStack toInsert = player.getItemInHand(hand);

		if(currentStack.isEmpty())
		{
			ItemStack stack = toInsert.copy();
			stack.setCount(1);
			
			altar.setItem(stack);
			
			if(!player.getAbilities().instabuild)
			{
				toInsert.shrink(1);
			}
		}
		else
		{
			if(!player.getAbilities().instabuild)
			{
				if(toInsert.isEmpty())
				{
					player.setItemInHand(hand, currentStack);
				}
				else if(!player.addItem(currentStack))
				{
					player.drop(currentStack, false);
				}
			}

			altar.setItem(ItemStack.EMPTY);
		}
		return InteractionResult.SUCCESS;
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
        return createTicker(pLevel, pBlockEntityType, BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<RiftwellingAltarBlockEntity> pClientType)
    {
        return createTickerHelper(pServerType, pClientType, RiftwellingAltarBlockEntity::update);
    }
    
    @Override
    @Nullable
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
