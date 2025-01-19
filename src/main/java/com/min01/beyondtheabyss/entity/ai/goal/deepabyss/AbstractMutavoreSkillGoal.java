package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;

public abstract class AbstractMutavoreSkillGoal extends BasicBTASkillGoal<EntityMutavore>
{
	public AbstractMutavoreSkillGoal(EntityMutavore mob) 
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		this.mob.setAnimationState(1);
	}

	@Override
	protected void performSkill()
	{
		this.mob.setAnimationState(2);
	}

	@Override
	protected int getSkillUsingTime()
	{
		return 5;
	}
	
	@Override
	protected int getSkillWarmupTime()
	{
		return 5;
	}
	
	public boolean isMouthOpened()
	{
		return this.mob.getAnimationState() == 2 && this.mob.getTarget() != null;
	}
}
