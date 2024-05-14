package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec3;

public class LatcherPropelGoal extends BasicBTASkillGoal<EntityLatcher>
{
	public LatcherPropelGoal(EntityLatcher mob)
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(-1);
	}
	
	@Override
	public boolean canUse() 
	{
		if (this.mob.isUsingSkill())
		{
			return false;
		} 
		else 
		{
			return this.mob.tickCount >= this.nextSkillTickCount && this.additionalStartCondition();
		}
	}
	
	@Override
	public boolean canContinueToUse() 
	{
		return this.mob.skillUsingTickCount > 0;
	}
	
	@Override
	public boolean stopMovingWhenStart()
	{
		return false;
	}
	
	@Override
	public boolean additionalStartCondition() 
	{
		return this.mob.getTarget() == null && this.mob.isInWater() && this.mob.getRandom().nextInt(40) == 0;
	}

	@Override
	protected void performSkill() 
	{
		Vec3 vec3 = BTAUtil.getLookPos(this.mob.getXRot(), this.mob.getYHeadRot(), 0, 0.25);
		this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3.x, 0.1F, vec3.y));
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
		return 32;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 16;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 10;
	}
}
