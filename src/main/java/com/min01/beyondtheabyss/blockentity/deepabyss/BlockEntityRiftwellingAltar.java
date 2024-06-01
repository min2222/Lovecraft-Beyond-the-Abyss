package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityRiftwellingAltar extends BlockEntity
{
	private ItemStack item;
	
	public BlockEntityRiftwellingAltar(BlockPos p_155229_, BlockState p_155230_) 
	{
		super(BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), p_155229_, p_155230_);
		this.item = ItemStack.EMPTY;
	}
	
	public static void update(Level level, BlockPos pos, BlockState state, BlockEntityRiftwellingAltar altar)
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
		nbt.put("item", this.item.save(itemTag));
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		CompoundTag itemTag = nbt.getCompound("item");
		this.item = ItemStack.of(itemTag);
	}
}
