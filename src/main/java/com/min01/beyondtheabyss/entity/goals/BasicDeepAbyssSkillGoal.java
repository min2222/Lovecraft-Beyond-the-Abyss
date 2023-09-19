package com.min01.beyondtheabyss.entity.goals;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractDeepAbyssEntity;

public abstract class BasicDeepAbyssSkillGoal<T extends AbstractDeepAbyssEntity> extends AbstractAbyssSkillGoal
{
	public T mob;
	
	public BasicDeepAbyssSkillGoal(T mob) 
	{
		this.mob = mob;
	}

	@Override
	public AbstractDeepAbyssEntity getMob() 
	{
		return this.mob;
	}
}
