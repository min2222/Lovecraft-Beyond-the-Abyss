package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

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
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) <= 12.0F;
	}

	@Override
	protected void performSkill() 
	{
		if(this.mob.posArray[0] != null)
		{
			float size = 3.5F; 
			AABB aabb = new AABB(-size, -size, -size, size, size, size);
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, aabb.move(this.mob.posArray[0]), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(t -> t != this.mob && !t.isAlliedTo(this.mob)));
			list.forEach(t -> 
			{
				if(this.mob.doHurtTarget(t))
				{
					t.addDeltaMovement(BTAUtil.fromToVector(this.mob.posArray[0], t.position(), 2.5F));
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
