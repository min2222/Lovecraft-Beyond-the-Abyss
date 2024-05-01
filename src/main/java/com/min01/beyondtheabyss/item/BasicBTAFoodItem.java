package com.min01.beyondtheabyss.item;

import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BasicBTAFoodItem extends Item
{
	public BasicBTAFoodItem(FoodProperties properties) 
	{
		super(new Item.Properties().tab(DeepAbyssTabs.ABYSS_FOODS).food(properties));
	}
}
