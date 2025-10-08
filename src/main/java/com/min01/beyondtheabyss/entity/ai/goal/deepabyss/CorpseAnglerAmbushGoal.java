package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.util.BTAUtil;

public class CorpseAnglerAmbushGoal extends BasicBTASkillGoal<EntityCorpseAngler>
{
	public CorpseAnglerAmbushGoal(EntityCorpseAngler mob)
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		this.mob.setAnimationState(5);
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.getAnimationState() == 3 && BTAUtil.distanceToXZ(this.mob.getTarget(), this.mob) <= 2.5F;
	}

	@Override
	protected void performSkill()
	{
		if(this.mob.getTarget() != null)
		{
			if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 3.5F))
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
	protected int getSkillUsingTime()
	{
		return 30;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 15;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 100;
	}
}
