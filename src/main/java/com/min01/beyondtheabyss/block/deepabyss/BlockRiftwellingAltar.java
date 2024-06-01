package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityRiftwellingAltar;
import com.min01.beyondtheabyss.network.AltarItemSyncPacket;
import com.min01.beyondtheabyss.network.BTANetwork;

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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

public class BlockRiftwellingAltar extends BaseEntityBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public BlockRiftwellingAltar() 
	{
		super(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((p_61031_, p_61032_, p_61033_, p_61034_) -> false).noOcclusion());
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_)
	{
		return new BlockEntityRiftwellingAltar(p_153215_, p_153216_);
	}
	
	@Override
	public InteractionResult use(BlockState p_60503_, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) 
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);

		if(!(blockEntity instanceof BlockEntityRiftwellingAltar) || player.isShiftKeyDown())
		{
			return InteractionResult.FAIL;
		}

		ItemStack currentStack = ((BlockEntityRiftwellingAltar) blockEntity).getItem();
		ItemStack toInsert = player.getItemInHand(hand);

		if(currentStack.isEmpty())
		{
			ItemStack stack = toInsert.copy();
			stack.setCount(1);
			
			((BlockEntityRiftwellingAltar) blockEntity).setItem(stack);
			
			if(!world.isClientSide)
			{
				BTANetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new AltarItemSyncPacket(player, stack, pos));
			}
			
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

			((BlockEntityRiftwellingAltar)blockEntity).setItem(ItemStack.EMPTY);
		}
		return InteractionResult.SUCCESS;
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_)
    {
        return createTicker(p_153273_, p_153275_, BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<BlockEntityRiftwellingAltar> p_151990_)
    {
        return createTickerHelper(p_151989_, p_151990_, BlockEntityRiftwellingAltar::update);
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_)
    {
    	LevelAccessor levelaccessor = p_152019_.getLevel();
    	BlockPos blockpos = p_152019_.getClickedPos();
    	return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
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
}
