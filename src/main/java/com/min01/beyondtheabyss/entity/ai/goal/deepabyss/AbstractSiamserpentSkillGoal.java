package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;

public abstract class AbstractSiamserpentSkillGoal extends BasicBTASkillGoal<EntitySiamserpentHead>
{
	public AbstractSiamserpentSkillGoal(EntitySiamserpentHead mob)
	{
		super(mob);
	}

	@Override
	public boolean canUse() 
	{
		if(!this.mob.isHead())
		{
			return false;
		}
		if(this.mob.isDormant())
		{
			return false;
		}
		return super.canUse() || this.getClass() == this.mob.goal;
	}
	
	public boolean isOtherHeadDormant()
	{
		return this.mob.getHead() instanceof EntitySiamserpentHead head && head.isDormant();
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
