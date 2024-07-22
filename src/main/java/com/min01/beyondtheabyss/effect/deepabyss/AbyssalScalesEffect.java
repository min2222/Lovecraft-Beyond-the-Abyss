package com.min01.beyondtheabyss.effect.deepabyss;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AbyssalScalesEffect extends MobEffect
{
	protected final double multiplier;
	   
	public AbyssalScalesEffect(double multiplier)
	{
		super(MobEffectCategory.NEUTRAL, 657950);
		this.multiplier = multiplier;
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) 
	{
		return duration > 0;
	}
	
	@Override
	public double getAttributeModifierValue(int p_19430_, AttributeModifier p_19431_)
	{
		return this.multiplier * (double)(p_19430_ + 1);
	}
}
