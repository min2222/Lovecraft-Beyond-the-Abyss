package com.min01.beyondtheabyss.entity.goals.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractAbyssEntity.AbyssSkills;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicAbyssSkillGoal;

public class GhidruthTailSlapGoal extends BasicAbyssSkillGoal<EntityGhidruth>
{
	public GhidruthTailSlapGoal(EntityGhidruth mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(2);
	}

	@Override
	protected void performSkill()
	{
		
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 25;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 15;
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	protected AbyssSkills getSkills() 
	{
		return AbyssSkills.GHIDRUTH_TAIL_SLAP;
	}
}
