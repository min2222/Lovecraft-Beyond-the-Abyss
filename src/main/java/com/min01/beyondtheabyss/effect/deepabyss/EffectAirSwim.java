package com.min01.beyondtheabyss.effect.deepabyss;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectAirSwim extends MobEffect
{
	public EffectAirSwim()
	{
		super(MobEffectCategory.NEUTRAL, 3407871);
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) 
	{
		return duration > 0;
	}
}
