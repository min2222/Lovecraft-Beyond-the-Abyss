package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BTAFoods 
{
	public static final FoodProperties RAW_GHIDRUTH_FLESH = new FoodProperties.Builder().nutrition(5).saturationMod(0.5F).build();
	public static final FoodProperties COOKED_GHIDRUTH_FLESH = new FoodProperties.Builder().nutrition(9).saturationMod(1.0F).effect(() -> new MobEffectInstance(BTAEffects.STONE_SKIN.get(), 1200, 0), 1.0F).build();
	public static final FoodProperties RAW_GNASHER = new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).build();
	public static final FoodProperties COOKED_GNASHER = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
	public static final FoodProperties GHOUL_BLOOM = new FoodProperties.Builder().fast().effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0), 1.0F).build();
	public static final FoodProperties COOKED_GHOUL_BLOOM = new FoodProperties.Builder().fast().nutrition(3).saturationMod(0.2F).build();
}
