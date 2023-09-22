package com.min01.beyondtheabyss.entity.goals.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractAbyssEntity.AbyssSkills;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicAbyssSkillGoal;

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
	protected void performSkill()
	{
		List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.tail.getBoundingBox().inflate(1.5));
		for(int i = 0; i < list.size(); i++)
		{
			LivingEntity living = list.get(i);
			if(living != this.mob)
			{
				living.hurt(DamageSource.mobAttack(this.mob), 7);
			}
		}
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 25;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 15;
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
