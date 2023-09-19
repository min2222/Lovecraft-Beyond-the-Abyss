package com.min01.beyondtheabyss.entity.goals;

import com.min01.beyondtheabyss.entity.AbstractAbyssEntity;

public abstract class BasicAbyssSkillGoal<T extends AbstractAbyssEntity> extends AbstractAbyssSkillGoal
{
	public T mob;
	
	public BasicAbyssSkillGoal(T mob) 
	{
		this.mob = mob;
	}

	@Override
	public AbstractAbyssEntity getMob() 
	{
		return this.mob;
	}
}
