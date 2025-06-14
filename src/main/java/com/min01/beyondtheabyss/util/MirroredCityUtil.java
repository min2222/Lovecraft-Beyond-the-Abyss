package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class MirroredCityUtil 
{
	public static boolean isBlockUpsideDown(BlockPos pos, Level level)
	{
		return level.dimension() == BTAWorlds.MIRRORED_CITY && pos.getY() >= 200;
	}
	
	public static boolean isUpsideDown(Entity entity)
	{
		return entity.level.dimension() == BTAWorlds.MIRRORED_CITY && entity.getY() >= 200;
	}
}
