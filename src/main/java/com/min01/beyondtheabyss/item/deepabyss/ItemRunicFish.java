package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRunicFish extends Item
{
	public ItemRunicFish(FoodProperties properties) 
	{
		super(new Item.Properties().tab(DeepAbyssTabs.ABYSS_FOODS).food(properties));
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) 
	{
		if(this.isEdible() && p_41411_ instanceof Player player)
		{
			player.giveExperiencePoints(player.getRandom().nextInt(1, 3));
			player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
		}
		return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
	}
}
