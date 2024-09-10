package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.util.BTAUtil;

public class DeepVampireBiteGoal extends BasicBTASkillGoal<EntityDeepVampire>
{
	public DeepVampireBiteGoal(EntityDeepVampire mob) 
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
	public boolean additionalStartCondition() 
	{
		return BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 3);
	}

	@Override
	protected void performSkill()
	{
		if(this.mob.getTarget() != null)
		{
			if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 3))
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
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
}
