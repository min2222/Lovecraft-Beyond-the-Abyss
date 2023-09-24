package com.min01.beyondtheabyss.entity.goals.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractAbyssEntity.AbyssSkills;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicAbyssSkillGoal;
import com.min01.beyondtheabyss.util.AbyssUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class GhidruthTailSwingGoal extends BasicAbyssSkillGoal<EntityGhidruth>
{
	public GhidruthTailSwingGoal(EntityGhidruth mob) 
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
	public boolean additionalStartCondition()
	{
		return AbyssUtil.isWithinMeleeAttackRangeOfPart(this.mob.head, this.mob.getTarget(), 3.5F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 3.5F;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.skillWarmupDelay <= 10)
		{
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.tail.getBoundingBox().inflate(4));
			for(int i = 0; i < list.size(); i++)
			{
				LivingEntity living = list.get(i);
				if(living != this.mob)
				{
					living.hurt(DamageSource.mobAttack(this.mob), 7);
				}
			}
		}
	}

	@Override
	protected void performSkill()
	{

	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 35;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 25;
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	protected AbyssSkills getSkills() 
	{
		return AbyssSkills.GHIDRUTH_TAIL_SWING;
	}
}
