package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;

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
		this.mob.playSound(BTASounds.GHIDRUTH_EYE_FLASH.get());
		this.mob.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) >= 15.0F;
	}

	@Override
	protected void performSkill() 
	{
		if(this.mob.getTarget() != null)
		{
			this.mob.setLastLookPos(BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, 100));
		}
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
		this.mob.setCharge(true);
		this.mob.setCanLook(false);
		this.mob.setCanMove(false);
		this.mob.playSound(BTASounds.GHIDRUTH_CHARGE_START.get());
	}

	@Override
	protected int getSkillUsingTime()
	{
		return 20;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 10;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 600;
	}
}
