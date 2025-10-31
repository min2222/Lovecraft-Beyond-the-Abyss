package com.min01.beyondtheabyss.item.animation;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public interface IAnimatableItem 
{
	default boolean isFirstPersonAnim(ItemStack stack, Entity entity)
	{
		return false;
	}
	
	default Vec3 getOffset()
	{
		return Vec3.ZERO;
	}
}
