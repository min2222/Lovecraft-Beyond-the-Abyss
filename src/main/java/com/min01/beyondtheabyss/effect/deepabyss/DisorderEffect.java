package com.min01.beyondtheabyss.effect.deepabyss;

import com.min01.beyondtheabyss.effect.BasicBTAEffect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class DisorderEffect extends BasicBTAEffect
{
	public DisorderEffect() 
	{
		super(MobEffectCategory.HARMFUL, 5463623);
	}
	
	@Override
	public boolean isInstantenous() 
	{
		return true;
	}
	
	@Override
	public void applyInstantenousEffect(Entity p_19462_, Entity p_19463_, LivingEntity p_19464_, int p_19465_, double p_19466_)
	{
		if(p_19464_ instanceof PathfinderMob mob)
		{
			mob.setTarget(null);
	        Vec3 vec3 = DefaultRandomPos.getPosAway(mob, 16 + p_19465_, 7 + p_19465_, p_19462_.position());
	        if(vec3 != null)
	        {
	            mob.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F + p_19465_);
	        }
		}
	}
}
