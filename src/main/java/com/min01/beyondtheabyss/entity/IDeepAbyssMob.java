package com.min01.beyondtheabyss.entity;

public interface IDeepAbyssMob
{
	default int maxTurnX()
	{
		return 85;
	}
	
	default int maxTurnY()
	{
		return 10;
	}
	
	default float insideWaterSpeed()
	{
		return 0.05F;
	}
	
	default int getSwimRadius()
	{
		return 12;
	}
	
	boolean canSwim();
}
