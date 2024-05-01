package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;

public class LatcherFindTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T>
{
	public LatcherFindTargetGoal(Mob p_26064_, Class<T> p_26065_, boolean p_26066_, boolean p_26067_)
	{
		super(p_26064_, p_26065_, p_26066_, p_26067_);
	}
	
	@Override
	protected AABB getTargetSearchArea(double p_26069_) 
	{
		return this.mob.getBoundingBox().inflate(p_26069_, p_26069_, p_26069_);
	}
}
