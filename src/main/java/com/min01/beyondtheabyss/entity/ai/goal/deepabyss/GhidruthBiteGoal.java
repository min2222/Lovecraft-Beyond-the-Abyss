package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMob.BTASkills;
import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

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
		this.mob.setAttackCount(this.mob.getAttackCount() + 1);
	}
	
	@Override
	public boolean additionalStartCondition()
	{
		return BTAUtil.isWithinMeleeAttackRange(this.mob.head, this.mob.getTarget(), 4F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 4F && !this.mob.isDash() && !this.mob.isStun();
	}

	@Override
	protected void performSkill() 
	{
		this.mob.playSound(BTASounds.GHIDRUTH_BITE.get());
		if(BTAUtil.isWithinMeleeAttackRange(this.mob.head, this.mob.getTarget(), 4F) && this.mob.head.distanceTo(this.mob.getTarget()) <= 4F)
		{
			Vec3 vec3 = BTAUtil.getLookPos(this.mob.getXRot(), this.mob.getYRot(), 0, 0.5F);
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.head.getBoundingBox().move(vec3).inflate(2F));
			list.removeIf((living) -> living == this.mob);
			list.forEach((living) -> 
			{
				if(living.hurt(DamageSource.mobAttack(this.mob), 17))
				{
                    double d0 = living.getX() - this.mob.head.getX();
                    double d1 = living.getZ() - this.mob.head.getZ();
                    double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
                    float f = 1.5F;
                    living.push(d0 / d2 * f, 0.15F, d1 / d2 * f);
				}
			});
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
	protected BTASkills getSkills() 
	{
		return BTASkills.GHIDRUTH_BITE;
	}
}
