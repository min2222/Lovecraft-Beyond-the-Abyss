package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity.HeadType;
import com.min01.beyondtheabyss.util.BTAUtil;

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
		return super.canUse() && BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 4) && this.mob.getHeadType() == HeadType.SLASHER && this.isOtherHeadDormant();
	}

	@Override
	public void performSkill() 
	{
		if(this.mob.getTarget() != null)
		{
			if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 4))
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
		return 20;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 8;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 10;
	}
}
