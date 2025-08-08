package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.util.BTAUtil;

public class SiamserpentSlasherSlashGoal extends AbstractSiamserpentSkillGoal
{
	public SiamserpentSlasherSlashGoal(EntitySiamserpentHead mob) 
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
	public boolean additionalStartCondition() 
	{
		return BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 4) && this.mob.getHeadType() == HeadType.SLASHER && this.isOtherHeadDisabled();
	}

	@Override
	protected void performSkill() 
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
	protected int getSkillUsingTime() 
	{
		return 20;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 8;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 30;
	}
}
