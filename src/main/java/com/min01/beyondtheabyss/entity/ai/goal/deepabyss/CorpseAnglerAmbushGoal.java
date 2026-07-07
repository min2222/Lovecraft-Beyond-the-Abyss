package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.CorpseAnglerEntity;

public class CorpseAnglerAmbushGoal extends AbstractAnimationGoal<CorpseAnglerEntity>
{
	public CorpseAnglerAmbushGoal(CorpseAnglerEntity mob)
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		this.mob.setAnimationState(5);
		this.mob.setBurrow(false);
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.isBurrow() && this.mob.getTarget().position().distanceTo(this.mob.modelPositions.getModelPos("Gnasher")) <= 2.5F;
	}

	@Override
	public void performSkill()
	{
		if(this.mob.getTarget() != null)
		{
			if(this.mob.isWithinMeleeAttackRange(this.mob.getTarget()))
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
		this.mob.setBurrowCooldown(200);
		this.mob.setStopMoveTick(0);
		this.mob.setStopLookTick(0);
	}

	@Override
	public int getSkillUsingTime()
	{
		return 30;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 15;
	}

	@Override
	public int getSkillUsingInterval()
	{
		return 100;
	}
}
