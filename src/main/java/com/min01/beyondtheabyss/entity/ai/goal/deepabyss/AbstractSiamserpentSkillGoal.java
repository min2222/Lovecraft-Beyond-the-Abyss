package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;

import net.minecraft.world.entity.LivingEntity;

public abstract class AbstractSiamserpentSkillGoal extends AbstractAnimationGoal<SiamserpentHeadEntity>
{
	public AbstractSiamserpentSkillGoal(SiamserpentHeadEntity mob)
	{
		super(mob);
	}

	@Override
	public boolean canUse() 
	{
    	LivingEntity target = this.mob.getTarget();
    	if(target == null || !target.isAlive()) 
    	{
    		return false;
    	}
		if(!this.mob.isHead() && this.mob.goal == null)
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
		return this.mob.getHead() instanceof SiamserpentHeadEntity head && head.isDormant();
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
