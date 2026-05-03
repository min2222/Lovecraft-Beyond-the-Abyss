package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

public class GnasherBiteGoal extends AbstractAnimationGoal<GnasherEntity>
{
	public GnasherBiteGoal(GnasherEntity mob) 
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
		return super.canUse() && BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 2.5F);
	}

	@Override
	public void performSkill()
	{
		if(this.mob.getTarget() != null)
		{
			if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 2.5F))
			{
				this.mob.playSound(BTASounds.GNASHER_BITE.get(), 4.0F, 1.0F);
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
		return 16;
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
