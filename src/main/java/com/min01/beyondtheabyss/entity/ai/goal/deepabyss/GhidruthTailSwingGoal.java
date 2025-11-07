package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;

public class GhidruthTailSwingGoal extends AbstractGhidruthSkillGoal
{
	public GhidruthTailSwingGoal(EntityGhidruth mob) 
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		if(this.mob.getRandom().nextBoolean())
		{
			this.mob.setAnimationState(3);
		}
		else
		{
			this.mob.setAnimationState(4);
		}
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) <= 8.0F;
	}

	@Override
	protected void performSkill() 
	{
		if(this.mob.posArray[0] != null)
		{
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(8.0F), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(t -> t != this.mob && !t.isAlliedTo(this.mob)));
			list.forEach(t -> 
			{
				if(this.mob.doHurtTarget(t))
				{
					t.addDeltaMovement(BTAUtil.fromToVector(this.mob.position(), t.position(), 4.5F));
				}
			});
		}
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
		return 30;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 18;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 150;
	}
}
