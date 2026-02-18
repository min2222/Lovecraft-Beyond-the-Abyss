package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityNecroshell;
import com.min01.beyondtheabyss.util.BTAUtil;

public class NecroshellAttackGoal extends AbstractAnimationGoal<EntityNecroshell>
{
	public boolean isSecond;
	
	public NecroshellAttackGoal(EntityNecroshell mob)
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
	public boolean canUse() 
	{
		return super.canUse() && BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 2.5F) && !this.mob.isHiding();
	}

	@Override
	public void performSkill()
	{
		if(this.mob.getTarget() != null)
		{
			if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 2.5F))
			{
				this.mob.doHurtTarget(this.mob.getTarget());
				if(!this.isSecond)
				{
					this.skillWarmupDelay = this.adjustedTickDelay(10);
					this.mob.setAnimationTick(this.getSkillUsingTime() - this.getSkillWarmupTime());
					this.isSecond = true;
				}
			}
		}
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
		this.isSecond = false;
	}

	@Override
	public int getSkillUsingTime()
	{
		return 30;
	}
	
	@Override
	public int getSkillWarmupTime()
	{
		return 16;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 20;
	}
}
