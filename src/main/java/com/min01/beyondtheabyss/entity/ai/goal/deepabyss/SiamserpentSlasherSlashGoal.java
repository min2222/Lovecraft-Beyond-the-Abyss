package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity.HeadType;

public class SiamserpentSlasherSlashGoal extends AbstractSiamserpentSkillGoal
{
	public SiamserpentSlasherSlashGoal(SiamserpentHeadEntity mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		if(this.mob.getRandom().nextBoolean())
		{
			this.mob.setAnimationState(5);
		}
		else
		{
			this.mob.setAnimationState(9);
		}
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.isWithinMeleeAttackRange(this.mob.getTarget()) && this.mob.getHeadType() == HeadType.SLASHER && this.isOtherHeadDormant();
	}

	@Override
	public void run() 
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
	}

	@Override
	public int getDuration() 
	{
		return 20;
	}
	
	@Override
	public int getDelay() 
	{
		return 8;
	}

	@Override
	public int getInterval() 
	{
		return 10;
	}
}
