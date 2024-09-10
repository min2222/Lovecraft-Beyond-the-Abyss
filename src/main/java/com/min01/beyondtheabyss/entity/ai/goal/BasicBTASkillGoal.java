package com.min01.beyondtheabyss.entity.ai.goal;

import com.min01.beyondtheabyss.entity.IAnimatable;

import net.minecraft.world.entity.Mob;

public abstract class BasicBTASkillGoal<T extends Mob & IAnimatable> extends AbstractBTASkillGoal<T>
{
	public T mob;
	
	public BasicBTASkillGoal(T mob) 
	{
		this.mob = mob;
	}

	@Override
	public T getMob() 
	{
		return this.mob;
	}
}
