package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GuidingClamItem extends Item
{
	public GuidingClamItem()
	{
		super(new Item.Properties().stacksTo(1).rarity(BTAItems.RARITY_DEEP_ABYSS));
	}
	
	@Override
	public boolean isFoil(ItemStack p_41453_)
	{
		return true;
	}
	
	@Override
	public void inventoryTick(ItemStack p_41404_, Level level, Entity entity, int p_41407_, boolean p_41408_) 
	{
        boolean isDeepOcean = entity.level.getBiome(entity.blockPosition()).is(BiomeTags.IS_DEEP_OCEAN);
    	setOpen(p_41404_, isDeepOcean);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext p_41427_) 
	{
		Level level = p_41427_.getLevel();
		BlockPos pos = p_41427_.getClickedPos();
		BlockState state = level.getBlockState(pos);
		Player player = p_41427_.getPlayer();
		ItemStack stack = p_41427_.getItemInHand();
		if(state.is(BTABlocks.ORIVINE.get()))
		{
			BTASavedData data = BTASavedData.get(level);
			if(data != null)
			{
				data.setAbyssPortalPos(pos.above());
			}
			if(!player.getAbilities().instabuild)
			{
				stack.shrink(1);
			}
			return InteractionResult.SUCCESS;
		}
		return super.useOn(p_41427_);
	}
	
	public static boolean isOpen(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean("Open");
	}
	
	public static void setOpen(ItemStack stack, boolean open) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("Open", open);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
}
