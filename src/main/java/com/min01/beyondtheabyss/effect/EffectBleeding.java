package com.min01.beyondtheabyss.effect;

import com.min01.beyondtheabyss.misc.BTADamageSource;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectBleeding extends MobEffect
{
	public EffectBleeding()
	{
		super(MobEffectCategory.HARMFUL, 5570560);
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) 
	{
		return duration > 0;
	}
	
	@Override
	public void applyEffectTick(LivingEntity p_19467_, int p_19468_) 
	{
		p_19467_.hurt(BTADamageSource.BLEEDING, 0.5F);
	}
}
