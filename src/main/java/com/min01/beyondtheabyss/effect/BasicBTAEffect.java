package com.min01.beyondtheabyss.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BasicBTAEffect extends MobEffect
{
	public BasicBTAEffect(MobEffectCategory pCategory, int pColor) 
	{
		super(pCategory, pColor);
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) 
	{
		return duration > 0;
	}
}
