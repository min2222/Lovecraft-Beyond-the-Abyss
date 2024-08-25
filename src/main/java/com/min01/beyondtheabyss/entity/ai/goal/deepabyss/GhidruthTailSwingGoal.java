package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
		Vec3 headPos = this.mob.posArray[0];
		return BTAUtil.isWithinMeleeAttackRange(headPos, 2.9375F, this.mob.getTarget(), 4.0F) && headPos.distanceTo(this.mob.getTarget().position()) <= 4.0F && !this.mob.isDash() && !this.mob.isStun();
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.mob.skillUsingTickCount <= 25 && this.mob.skillUsingTickCount >= 5)
		{
			AABB tailAABB = new AABB(new Vec3(0.71875F, 0.0F, 0.734375F).reverse(), new Vec3(0.71875F, 1.75F, 0.734375F));
			AABB bodyAABB = new AABB(new Vec3(1.46875F, 0.0F, 2.53125F).reverse(), new Vec3(1.46875F, 3.1875F, 2.53125F));
			List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, tailAABB.inflate(3.5F));
			list.removeIf((living) -> living == this.mob);
			list.forEach((living) -> living.hurt(this.mob.damageSources().mobAttack(this.mob), 20));
			
			List<LivingEntity> list1 = this.mob.level.getEntitiesOfClass(LivingEntity.class, bodyAABB.inflate(3.5F));
			list1.removeIf((living) -> living == this.mob);
			list1.forEach((living) -> living.hurt(this.mob.damageSources().mobAttack(this.mob), 20));
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
}
