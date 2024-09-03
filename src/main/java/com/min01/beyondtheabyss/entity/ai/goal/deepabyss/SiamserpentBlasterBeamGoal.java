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
		this.mob.setAnimationState(1);
	}
	
	@Override
	public boolean stopMovingWhenStart() 
	{
		return false;
	}
	
	@Override
	public boolean additionalStartCondition() 
	{
		return this.mob.getHeadType() == HeadType.BLASTER;
	}

	@Override
	protected void performSkill() 
	{
		
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
		return 140;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 0;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
}
