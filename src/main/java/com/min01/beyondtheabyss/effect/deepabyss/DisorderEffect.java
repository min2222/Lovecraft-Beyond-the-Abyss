package com.min01.beyondtheabyss.effect.deepabyss;

import javax.annotation.Nullable;

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
	public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth)
	{
		if(pLivingEntity instanceof PathfinderMob mob && pSource != null)
		{
			mob.setTarget(null);
	        Vec3 vec3 = DefaultRandomPos.getPosAway(mob, 16 + pAmplifier, 7 + pAmplifier, pSource.position());
	        if(vec3 != null)
	        {
	            mob.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F + pAmplifier);
	        }
		}
	}
}
