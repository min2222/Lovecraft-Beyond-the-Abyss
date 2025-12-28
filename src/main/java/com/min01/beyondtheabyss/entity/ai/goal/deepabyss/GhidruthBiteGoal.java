package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.sound.BTASounds;

public class GhidruthBiteGoal extends AbstractGhidruthSkillGoal
{
	public GhidruthBiteGoal(EntityGhidruth mob) 
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		if(this.mob.getRandom().nextBoolean())
		{
			this.mob.setAnimationState(1);
		}
		else
		{
			this.mob.setAnimationState(2);
		}
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) <= 8.0F;
	}

	@Override
	public void performSkill() 
	{
		this.mob.playSound(BTASounds.GHIDRUTH_BITE.get(), 1.5F, 1.0F);
		if(this.mob.getTarget() != null)
		{
			if(this.mob.distanceTo(this.mob.getTarget()) <= 8.0F)
			{
				this.mob.doHurtTarget(this.mob.getTarget());
			}
		}
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	public int getSkillUsingTime()
	{
		return 25;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 13;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 60;
	}
}
