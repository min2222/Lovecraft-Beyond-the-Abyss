package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead;

public abstract class AbstractTwinserpentSkillGoal extends BasicBTASkillGoal<EntityTwinserpentHead>
{
	public AbstractTwinserpentSkillGoal(EntityTwinserpentHead mob)
	{
		super(mob);
	}

	@Override
	public boolean canUse() 
	{
		return ((super.canUse() && this.mob.isHead()) || this.getClass() == this.mob.goal) && !this.mob.isDisabled();
	}
	
	public boolean isOtherHeadDisabled()
	{
		return this.mob.getHead() != null && ((EntityTwinserpentHead) this.mob.getHead()).isDisabled();
	}
	
	@Override
	public void stop()
	{
		super.stop();
		if(this.getClass() == this.mob.goal)
		{
			this.mob.goal = null;
		}
	}
}
