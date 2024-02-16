package com.min01.beyondtheabyss.entity.goal;

import com.min01.beyondtheabyss.entity.AbstractBTAEntity;

public abstract class BasicBTASkillGoal<T extends AbstractBTAEntity> extends AbstractBTASkillGoal
{
	public T mob;
	
	public BasicBTASkillGoal(T mob) 
	{
		this.mob = mob;
	}

	@Override
	public AbstractBTAEntity getMob() 
	{
		return this.mob;
	}
}
