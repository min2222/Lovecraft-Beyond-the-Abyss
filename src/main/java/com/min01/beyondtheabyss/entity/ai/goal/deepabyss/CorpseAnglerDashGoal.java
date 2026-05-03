package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.CorpseAnglerEntity;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec3;

public class CorpseAnglerDashGoal extends AbstractAnimationGoal<CorpseAnglerEntity>
{
	public boolean canContinueToUse = true;
	
	public CorpseAnglerDashGoal(CorpseAnglerEntity mob) 
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
	public boolean canUse()
	{
		return super.canUse() && !this.mob.isBurrow() && this.mob.distanceTo(this.mob.getTarget()) <= 12.0F;
	}
	
	@Override
	public boolean canContinueToUse()
	{
		return this.canContinueToUse;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.mob.getTarget() != null)
		{
			if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - this.getSkillWarmupTime())
			{
				Vec3 lookPos = BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, 20);
				this.mob.setStopLookTick(Integer.MAX_VALUE);
				this.mob.setLastLookPos(lookPos);
				this.mob.setDeltaMovement(BTAUtil.getVelocityTowards(this.mob.position(), lookPos, 1.5F));
				if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 2.5F))
				{
					this.canContinueToUse = false;
					this.mob.doHurtTarget(this.mob.getTarget());
				}
				else if(this.mob.distanceTo(this.mob.getTarget()) >= 20.0F)
				{
					this.canContinueToUse = false;
				}
			}
		}
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setStopLookTick(0);
		this.mob.setAnimationState(0);
		this.mob.setAnimationTick(0);
		this.mob.getNavigation().stop();
		this.mob.setDeltaMovement(Vec3.ZERO);
		this.mob.setLastLookPos(Vec3.ZERO);
		this.canContinueToUse = true;
	}

	@Override
	public int getSkillUsingTime() 
	{
		return 1000;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 60;
	}

	@Override
	public int getSkillUsingInterval()
	{
		return 110;
	}
}
