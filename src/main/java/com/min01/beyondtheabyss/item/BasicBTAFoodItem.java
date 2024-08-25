package com.min01.beyondtheabyss.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BasicBTAFoodItem extends Item
{
	public BasicBTAFoodItem(FoodProperties properties) 
	{
		super(new Item.Properties().food(properties));
	}
}
