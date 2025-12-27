package com.min01.beyondtheabyss.entity;

import net.minecraft.world.phys.Vec3;

public interface IBTAMob
{
	default int maxTurnX()
	{
		return 85;
	}
	
	default int maxTurnY()
	{
		return 10;
	}
	
	default float moveSpeed()
	{
		return 0.05F;
	}
	
	default Vec3 getMoveRadius()
	{
		return new Vec3(35, 10, 35);
	}
	
	default int targetSettingInterval()
	{
		return 40;
	}
	
	public boolean canMoveAround();
}
