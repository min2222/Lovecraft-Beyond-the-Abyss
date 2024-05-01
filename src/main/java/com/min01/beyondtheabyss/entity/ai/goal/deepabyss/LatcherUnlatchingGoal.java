package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;

public class LatcherUnlatchingGoal extends BasicBTASkillGoal<EntityLatcher>
{
	public LatcherUnlatchingGoal(EntityLatcher mob) 
	{
		super(mob);
	}
	
	@Override
	public boolean additionalStartCondition() 
	{
		return this.mob.getAnimationState() == 3;
	}
	
	@Override
	protected void performSkill() 
	{
		this.mob.setAnimationState(0);
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 10;
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 10;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 5;
	}
}
