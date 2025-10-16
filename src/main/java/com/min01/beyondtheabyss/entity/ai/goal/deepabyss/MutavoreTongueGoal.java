package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MutavoreTongueGoal extends BasicBTASkillGoal<EntityMutavore>
{
	private boolean canContinueToUse = true;
	
	public MutavoreTongueGoal(EntityMutavore mob)
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(3);
	}
	
	@Override
	public boolean stopMovingWhenStart()
	{
		return false;
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) <= 5.0F;
	}
	
	@Override
	public boolean canContinueToUse() 
	{
		return super.canContinueToUse() && this.canContinueToUse;
	}
	
	@Override
	public void tick() 
	{
		if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - 10 && this.mob.getTarget() != null) 
		{
			if(this.mob.getAnimationState() == 3)
			{
				this.mob.setAnimationState(4);
			}
			Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.mob.getXRot(), this.mob.getYHeadRot()), this.mob.position(), 0.0F, 0.5F, 4.0F);
			boolean flag = this.mob.getTarget().position().subtract(lookPos).length() <= 2.0F;
			this.canContinueToUse = flag && this.mob.getTarget().isAlive();
			if(this.mob.tickCount % 5 == 1 && flag)
			{
				this.mob.doHurtTarget(this.mob.getTarget());
			}
		}
	}

	@Override
	protected void performSkill()
	{
		this.mob.setAnimationState(5);
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.canContinueToUse = true;
		this.mob.setAnimationState(0);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 80;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 70;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 40;
	}
}
