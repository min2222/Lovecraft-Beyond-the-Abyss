package com.min01.beyondtheabyss.entity.goals.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAEntity.BTASkills;
import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicBTASkillGoal;
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
		return BTAUtil.isWithinMeleeAttackRangeOfPart(this.mob.head, this.mob.getTarget(), 4F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 4F && !this.mob.isDash();
	}

	@Override
	protected void performSkill()
	{
		List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.body.getBoundingBox().inflate(6));
		list.removeIf((living) -> living == this.mob);
		list.forEach((living) -> living.hurt(DamageSource.mobAttack(this.mob), 14));
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
