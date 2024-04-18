package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMob.BTASkills;
import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class GhidruthTailSwingGoal extends BasicBTASkillGoal<EntityGhidruth>
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
		this.mob.setAttackCount(this.mob.getAttackCount() + 1);
	}
	
	@Override
	public boolean additionalStartCondition()
	{
		return BTAUtil.isWithinMeleeAttackRange(this.mob.head, this.mob.getTarget(), 4F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 4F && !this.mob.isDash() && !this.mob.isStun();
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.mob.skillUsingTickCount <= 25 && this.mob.skillUsingTickCount >= 5)
		{
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.tail.getBoundingBox().inflate(3.5F));
			list.removeIf((living) -> living == this.mob);
			list.forEach((living) -> living.hurt(DamageSource.mobAttack(this.mob), 20));
			
			List<LivingEntity> list1 = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.body.getBoundingBox().inflate(3.5F));
			list1.removeIf((living) -> living == this.mob);
			list1.forEach((living) -> living.hurt(DamageSource.mobAttack(this.mob), 20));
		}
	}

	@Override
	protected void performSkill()
	{

	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 30;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 20;
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	protected BTASkills getSkills() 
	{
		return BTASkills.GHIDRUTH_TAIL_SWING;
	}
}
