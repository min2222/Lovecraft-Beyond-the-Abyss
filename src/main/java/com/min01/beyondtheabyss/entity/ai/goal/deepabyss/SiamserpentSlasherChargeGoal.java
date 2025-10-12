package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec3;

public class SiamserpentSlasherChargeGoal extends AbstractSiamserpentSkillGoal
{
	public SiamserpentSlasherChargeGoal(EntitySiamserpentHead mob) 
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
	protected void performSkill() 
	{
		this.mob.setAnimationState(8);
		this.mob.setCanLook(false);
		if(this.mob.getTarget() != null)
		{
			this.mob.setLastLookPos(this.mob.getTarget().getEyePosition());
		}
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(!this.mob.getLastLookPos().equals(Vec3.ZERO))
		{
			this.mob.setDeltaMovement(BTAUtil.fromToVector(this.mob.position(), this.mob.getLastLookPos()));
			if(this.mob.getTarget() != null)
			{
				if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 4))
				{
					this.mob.doHurtTarget(this.mob.getTarget());
				}
			}
			if(this.mob.getLastLookPos().subtract(this.mob.position()).length() <= 1.5F)
			{
				EntitySiamserpentHead head = (EntitySiamserpentHead) this.mob.getHead();
				head.setCanLook(true);
				head.setCanMove(true);
				head.setAnimationState(4);
				head.setAnimationTick(5);
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
		this.mob.setCanLook(true);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 100;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 45;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 150;
	}
}
