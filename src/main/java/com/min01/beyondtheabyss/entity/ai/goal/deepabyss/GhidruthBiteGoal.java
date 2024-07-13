package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
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
		Vec3 headPos = this.mob.posArray[0];
		return BTAUtil.isWithinMeleeAttackRange(headPos, 2.9375F, this.mob.getTarget(), 4.0F) && headPos.distanceTo(this.mob.getTarget().position()) <= 4.0F && !this.mob.isDash() && !this.mob.isStun();
	}

	@Override
	protected void performSkill() 
	{
		Vec3 headPos = this.mob.posArray[0];
		this.mob.playSound(BTASounds.GHIDRUTH_BITE.get());
		if(BTAUtil.isWithinMeleeAttackRange(headPos, 2.9375F, this.mob.getTarget(), 4.0F) && headPos.distanceTo(this.mob.getTarget().position()) <= 4.0F)
		{
			AABB headAABB = new AABB(new Vec3(1.46875F, 0.0F, 1.96875F).reverse(), new Vec3(1.46875F, 3.1875F, 1.96875F));
			Vec3 vec3 = BTAUtil.getLookPos(this.mob.getXRot(), this.mob.getYRot(), 0, 0.5F);
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, headAABB.move(vec3).inflate(2.0F));
			list.removeIf((living) -> living == this.mob);
			list.forEach((living) -> 
			{
				if(living.hurt(DamageSource.mobAttack(this.mob), 17))
				{
                    double d0 = living.getX() - headPos.x;
                    double d1 = living.getZ() - headPos.z;
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
}
