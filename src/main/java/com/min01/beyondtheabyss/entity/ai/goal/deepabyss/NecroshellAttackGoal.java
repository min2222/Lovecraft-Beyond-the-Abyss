package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.NecroshellEntity;

public class NecroshellAttackGoal extends AbstractAnimationGoal<NecroshellEntity>
{
	public boolean isSecond;
	
	public NecroshellAttackGoal(NecroshellEntity mob)
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
		return super.canUse() && this.mob.isWithinMeleeAttackRange(this.mob.getTarget()) && !this.mob.isHiding();
	}

	@Override
	public void performSkill()
	{
		if(this.mob.getTarget() != null)
		{
			if(this.mob.isWithinMeleeAttackRange(this.mob.getTarget()))
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
