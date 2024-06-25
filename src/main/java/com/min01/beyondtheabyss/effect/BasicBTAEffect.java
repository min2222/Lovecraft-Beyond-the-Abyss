package com.min01.beyondtheabyss.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BasicBTAEffect extends MobEffect
{
	public BasicBTAEffect(MobEffectCategory p_19451_, int p_19452_) 
	{
		super(p_19451_, p_19452_);
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) 
	{
		return duration > 0;
	}
}
