package com.min01.beyondtheabyss.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IServerUpdate
{
	public void onServerUpdate(LivingEntity living, ItemStack stack);
}
