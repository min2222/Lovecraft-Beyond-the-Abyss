package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.world.entity.Entity;

public class MirroredCityUtil 
{
	public static boolean isUpsideDown(Entity entity)
	{
		return entity.level.dimension() == BTAWorlds.MIRRORED_CITY && entity.getY() >= 200;
	}
}
