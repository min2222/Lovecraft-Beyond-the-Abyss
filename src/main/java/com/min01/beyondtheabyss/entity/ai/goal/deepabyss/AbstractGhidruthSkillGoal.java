package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.GhidruthEntity;

public abstract class AbstractGhidruthSkillGoal extends AbstractAnimationGoal<GhidruthEntity>
{
	public AbstractGhidruthSkillGoal(GhidruthEntity mob) 
	{
		super(mob);
	}

	@Override
	public boolean canUse() 
	{
		return super.canUse() && !this.mob.isStun() && !this.mob.isCharge();
	}
}
