package com.min01.beyondtheabyss.entity.goal;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;

public abstract class BasicBTASkillGoal<T extends AbstractBTAMob> extends AbstractBTASkillGoal
{
	public T mob;
	
	public BasicBTASkillGoal(T mob) 
	{
		this.mob = mob;
	}

	@Override
	public AbstractBTAMob getMob() 
	{
		return this.mob;
	}
}
