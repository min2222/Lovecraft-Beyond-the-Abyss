package com.min01.beyondtheabyss.blockentity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RiftwellingAltarBlockEntity extends BlockEntity
{
	private ItemStack stack = ItemStack.EMPTY;
	
	public RiftwellingAltarBlockEntity(BlockPos pPos, BlockState pBlockState)
	{
		super(BTABlocks.RIFTWELLING_ALTAR_BLOCK_ENTITY.get(), pPos, pBlockState);
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, RiftwellingAltarBlockEntity altar)
	{
		
	}
	
	public void setItem(ItemStack stack)
	{
		this.stack = stack;
	}
	
	public ItemStack getItem()
	{
		return this.stack;
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.put("ItemStack", this.stack.save(new CompoundTag()));
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.stack = ItemStack.of(nbt.getCompound("ItemStack"));
	}
}
