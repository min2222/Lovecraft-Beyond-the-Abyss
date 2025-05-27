package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;

public class SiamserpentBlasterBeamGoal extends BasicBTASkillGoal<EntitySiamserpentHead>
{
	public SiamserpentBlasterBeamGoal(EntitySiamserpentHead mob) 
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
	public boolean additionalStartCondition() 
	{
		return this.mob.getHeadType() == HeadType.BLASTER && !this.mob.isDormant();
	}

	@Override
	protected void performSkill() 
	{
		this.mob.setAnimationState(1);
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(3);
		this.mob.setAnimationTick(80);
		this.mob.setCanLook(false);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 45;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 5;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 150;
	}
}
