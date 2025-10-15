package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityNecroshell;

public class NecroshellHidingGoal extends BasicBTASkillGoal<EntityNecroshell>
{
	private int noTargetTick;
	
	public NecroshellHidingGoal(EntityNecroshell mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setHiding(true);
		this.mob.setCanLook(false);
	}
	
	@Override
	public boolean canUse()
	{
		if(this.mob.getTarget() == null && this.mob.getLastHurtByMob() == null)
		{
			this.noTargetTick++;
		}
		return !this.mob.isUsingSkill() && this.noTargetTick >= 100;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.mob.getTarget() != null || this.mob.getTarget().distanceTo(this.mob) <= 4.5F || this.mob.getLastHurtByMob() != null)
		{
			this.noTargetTick = 0;
		}
	}
	
	@Override
	public boolean canContinueToUse() 
	{
		return this.noTargetTick >= 100;
	}

	@Override
	protected void performSkill() 
	{
		
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setHiding(false);
		this.mob.setCanLook(true);
		this.mob.setAnimationState(2);
		this.mob.setUsingSkill(true);
		this.mob.setAnimationTick(15);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 15;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 100;
	}
}
