package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity.HeadType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec3;

public class SiamserpentSlasherChargeGoal extends AbstractSiamserpentSkillGoal
{
	public SiamserpentSlasherChargeGoal(SiamserpentHeadEntity mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(7);
		if(this.mob.getHead() != null)
		{
			this.mob.getHead().goal = SiamserpentBlasterBeamGoal.class;
		}
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.getHeadType() == HeadType.SLASHER && !this.isOtherHeadDormant();
	}

	@Override
	public void run() 
	{
		this.mob.setStopLookTick(Integer.MAX_VALUE);
		this.mob.setAnimationState(8);
		this.mob.setLastLookPos(BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, 100));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(!this.mob.getLastLookPos().equals(Vec3.ZERO))
		{
			this.mob.setDeltaMovement(BTAUtil.getVelocityTowards(this.mob.position(), this.mob.getLastLookPos(), 1.5F));
			if(this.mob.getTarget() != null)
			{
				if(this.mob.isWithinMeleeAttackRange(this.mob.getTarget()))
				{
					this.mob.doHurtTarget(this.mob.getTarget());
				}
			}
			if(this.mob.getLastLookPos().subtract(this.mob.position()).length() <= 1.5F)
			{
				this.mob.setAnimationTick(0);
			}
		}
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
		this.mob.setLastLookPos(Vec3.ZERO);
	}

	@Override
	public int getDuration() 
	{
		return 100;
	}
	
	@Override
	public int getDelay() 
	{
		return 45;
	}

	@Override
	public int getInterval() 
	{
		return 50;
	}
}
