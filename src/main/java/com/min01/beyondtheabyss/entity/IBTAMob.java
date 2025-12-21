package com.min01.beyondtheabyss.entity;

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
}
