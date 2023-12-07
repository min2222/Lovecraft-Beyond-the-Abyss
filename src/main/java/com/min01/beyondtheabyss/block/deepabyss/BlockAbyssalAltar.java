package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityAbyssalAltar;
import com.min01.beyondtheabyss.network.AltarItemSyncPacket;
import com.min01.beyondtheabyss.network.BTANetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PacketDistributor;

public class BlockAbyssalAltar extends BaseEntityBlock
{
	public BlockAbyssalAltar() 
	{
		super(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn(BTABlocks::never).noOcclusion());
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_)
	{
		return new BlockEntityAbyssalAltar(p_153215_, p_153216_);
	}
	
	@Override
	public InteractionResult use(BlockState p_60503_, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) 
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);

		if(!(blockEntity instanceof BlockEntityAbyssalAltar) || player.isShiftKeyDown())
		{
			return InteractionResult.FAIL;
		}

		ItemStack currentStack = ((BlockEntityAbyssalAltar) blockEntity).getItem();
		ItemStack toInsert = player.getItemInHand(hand);

		if(currentStack.isEmpty())
		{
			ItemStack stack = toInsert.copy();
			stack.setCount(1);
			
			((BlockEntityAbyssalAltar) blockEntity).setItem(stack);
			
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

			((BlockEntityAbyssalAltar)blockEntity).setItem(ItemStack.EMPTY);
		}
		return InteractionResult.SUCCESS;
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_)
    {
        return createTicker(p_153273_, p_153275_, BTABlocks.ABYSSAL_ALTAR_BLOCK_ENTITY.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<BlockEntityAbyssalAltar> p_151990_)
    {
        return createTickerHelper(p_151989_, p_151990_, BlockEntityAbyssalAltar::update);
    }
}
