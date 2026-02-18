package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

public class GhidruthChargePrepareGoal extends AbstractGhidruthSkillGoal
{
	public GhidruthChargePrepareGoal(EntityGhidruth mob) 
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		this.mob.setAnimationState(5);
		this.mob.playSound(BTASounds.GHIDRUTH_EYE_FLASH.get(), 2.0F, 1.0F);
		this.mob.lookAtTarget();
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) >= 15.0F;
	}

	@Override
	public void performSkill() 
	{
		this.mob.setLastLookPos(BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, 100));
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
		this.mob.setCharge(true);
		this.mob.setStopMoveTick(Integer.MAX_VALUE);
		this.mob.setStopLookTick(Integer.MAX_VALUE);
		this.mob.playSound(BTASounds.GHIDRUTH_CHARGE_START.get(), 2.0F, 1.0F);
	}

	@Override
	public int getSkillUsingTime()
	{
		return 20;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 10;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 600;
	}
}
