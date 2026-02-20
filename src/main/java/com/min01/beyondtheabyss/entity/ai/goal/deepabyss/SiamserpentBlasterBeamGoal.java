package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

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
	public void performSkill() 
	{
		this.mob.setAnimationState(1);
		this.mob.playSound(BTASounds.SIAMSERPENT_BEAM_CHARGE.get(), 10.0F, 1.0F);
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setStopMoveTick(Integer.MAX_VALUE);
		this.mob.setStopLookTick(Integer.MAX_VALUE);
		this.mob.setLastLookPos(BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, 100));
		this.mob.setAnimationState(3);
		this.mob.setAnimationTick(40);
		//this.mob.playSound(BTASounds.SIAMSERPENT_BEAM_SHOOT.get());
	}

	@Override
	public int getSkillUsingTime() 
	{
		return 45;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 5;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 250;
	}
}
