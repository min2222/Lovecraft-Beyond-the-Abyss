package com.min01.beyondtheabyss.item.animation;

import net.minecraft.world.phys.Vec3;

public interface IAnimatableItem 
{
	default boolean isFirstPersonAnim()
	{
		return false;
	}
	
	default Vec3 getOffset()
	{
		return Vec3.ZERO;
	}
}
