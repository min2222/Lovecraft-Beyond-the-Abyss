package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;

public class MutavoreTongueBiteGoal extends AbstractMutavoreSkillGoal
{
	public MutavoreTongueBiteGoal(EntityMutavore mob) 
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		this.mob.setUsingTongue(true);
		this.mob.setCanMove(false);
	}
	
	@Override
	public boolean additionalStartCondition()
	{
		return !this.mob.isUsingTongue() && this.mob.posArray[11] != null && this.mob.posArray[11].distanceTo(this.mob.getTarget().position()) <= 5.0F;
	}
	
	@Override
	public boolean stopMovingWhenStart()
	{
		return false;
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setUsingSkill(true);
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 100;
	}
}
