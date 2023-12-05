package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityAbyssalAltar extends BlockEntity
{
	private ItemStack item;
	
	public BlockEntityAbyssalAltar(BlockPos p_155229_, BlockState p_155230_) 
	{
		super(BTABlocks.ABYSSAL_ALTAR_BLOCK_ENTITY.get(), p_155229_, p_155230_);
		this.item = ItemStack.EMPTY;
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, BlockEntityAbyssalAltar altar)
	{
		
	}
	
	public void setItem(ItemStack stack)
	{
		this.item = stack;
	}
	
	public ItemStack getItem()
	{
		return this.item;
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		CompoundTag itemTag = new CompoundTag();
		this.item.save(itemTag);
		nbt.put("item", itemTag);
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		CompoundTag itemTag = nbt.getCompound("item");
		this.item = ItemStack.of(itemTag);
	}
}
