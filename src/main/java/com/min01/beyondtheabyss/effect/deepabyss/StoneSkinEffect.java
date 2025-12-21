package com.min01.beyondtheabyss.effect.deepabyss;

import java.util.UUID;

import com.min01.beyondtheabyss.effect.BasicBTAEffect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StoneSkinEffect extends BasicBTAEffect
{
	public StoneSkinEffect()
	{
		super(MobEffectCategory.BENEFICIAL, 657950);
		this.addAttributeModifier(Attributes.ARMOR, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID.randomUUID().toString(), 0, Operation.ADDITION);
	}
	
	@Override
	public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier)
	{
		return 4 * (pAmplifier + 1);
	}
}
