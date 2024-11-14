package com.min01.beyondtheabyss.effect.deepabyss;

import java.util.UUID;

import com.min01.beyondtheabyss.effect.BasicBTAEffect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AbyssalScalesEffect extends BasicBTAEffect
{
	public final double multiplier;
	
	public AbyssalScalesEffect(double multiplier)
	{
		super(MobEffectCategory.NEUTRAL, 657950);
		this.addAttributeModifier(Attributes.ARMOR, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.multiplier = multiplier;
	}
	
	@Override
	public double getAttributeModifierValue(int p_19430_, AttributeModifier p_19431_)
	{
		return this.multiplier * (double)(p_19430_ + 1);
	}
}
