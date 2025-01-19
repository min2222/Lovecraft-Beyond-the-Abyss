package com.min01.beyondtheabyss.entity;

public interface IAnimatable
{
	boolean isUsingSkill();
	
	void setUsingSkill(boolean value);
	
	void setAnimationTick(int value);
	
	int getAnimationTick();
	
	int getMoveStopDelay();
	
	int getPrevAnimationTick();
	
	boolean canMove();
	
	void setCanMove(boolean value);
}
