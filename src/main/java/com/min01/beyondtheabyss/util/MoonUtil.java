package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.world.entity.Entity;

public class MoonUtil
{
	public static void updateMoonGravity(Entity entity)
	{
		if(entity.level.dimension() == BTAWorlds.MOON)
		{
			entity.resetFallDistance();
			if(entity.getDeltaMovement().y <= 0.0D)
			{
				entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y * 0.7, entity.getDeltaMovement().z);
			}
		}
	}
}