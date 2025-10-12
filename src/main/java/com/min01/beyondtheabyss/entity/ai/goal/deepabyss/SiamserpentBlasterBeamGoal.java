package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.sound.BTASounds;

public class SiamserpentBlasterBeamGoal extends AbstractSiamserpentSkillGoal
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
	public boolean canUse() 
	{
		return super.canUse() && this.mob.getHeadType() == HeadType.BLASTER && !this.isOtherHeadDormant();
	}

	@Override
	protected void performSkill() 
	{
		this.mob.setAnimationState(1);
		this.mob.playSound(BTASounds.SIAMSERPENT_BEAM_CHARGE.get());
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(3);
		this.mob.setAnimationTick(40);
		this.mob.setCanLook(false);
		this.mob.setCanMove(false);
		if(this.mob.getTarget() != null)
		{
			this.mob.setLastLookPos(this.mob.getTarget().getEyePosition());
		}
		//this.mob.playSound(BTASounds.SIAMSERPENT_BEAM_SHOOT.get());
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
		return 250;
	}
}
