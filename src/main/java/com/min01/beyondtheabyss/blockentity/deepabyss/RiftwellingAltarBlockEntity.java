package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateAltarItemPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RiftwellingAltarBlockEntity extends BlockEntity
{
	private ItemStack item;
	
	public RiftwellingAltarBlockEntity(BlockPos pPos, BlockState pBlockState)
	{
		super(BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), pPos, pBlockState);
		this.item = ItemStack.EMPTY;
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, RiftwellingAltarBlockEntity altar)
	{
		
	}
	
	public void setItem(ItemStack stack)
	{
		this.item = stack;
		if(!this.level.isClientSide)
		{
			BTANetwork.sendToAll(new UpdateAltarItemPacket(stack, this.worldPosition));
		}
	}
	
	public ItemStack getItem()
	{
		return this.item;
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.put("Item", this.item.save(new CompoundTag()));
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.setItem(ItemStack.of(nbt.getCompound("Item")));
	}
}
