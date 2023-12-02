package com.min01.beyondtheabyss.entity.goals.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAEntity.AbyssSkills;
import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicBTASkillGoal;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class GhidruthBiteGoal extends BasicBTASkillGoal<EntityGhidruth>
{
	public GhidruthBiteGoal(EntityGhidruth mob) 
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
	public boolean additionalStartCondition()
	{
		return BTAUtil.isWithinMeleeAttackRangeOfPart(this.mob.head, this.mob.getTarget(), 5F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 5F;
	}

	@Override
	protected void performSkill() 
	{
		if(BTAUtil.isWithinMeleeAttackRangeOfPart(this.mob.head, this.mob.getTarget(), 5F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 5F)
		{
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.head.getBoundingBox().inflate(2.5F));
			for(int i = 0; i < list.size(); i++)
			{
				LivingEntity living = list.get(i);
				if(living != this.mob)
				{
					living.hurt(DamageSource.mobAttack(this.mob), 10);
				}
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
		return AbyssSkills.GHIDRUTH_BITE;
	}
}
