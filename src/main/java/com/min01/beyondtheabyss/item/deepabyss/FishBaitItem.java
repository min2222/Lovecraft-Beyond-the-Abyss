package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFishBait;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FishBaitItem extends Item
{
	public FishBaitItem() 
	{
		super(new Item.Properties());
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) 
	{
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		if(!p_41432_.isClientSide)
		{
			EntityFishBait bait = new EntityFishBait(BTAEntities.FISH_BAIT.get(), p_41432_);
			bait.setPos(p_41433_.position());
			p_41432_.addFreshEntity(bait);
		}
		p_41433_.awardStat(Stats.ITEM_USED.get(this));
		if(!p_41433_.getAbilities().instabuild) 
		{
			stack.shrink(1);
		}
		return InteractionResultHolder.sidedSuccess(stack, p_41432_.isClientSide);
	}
}
