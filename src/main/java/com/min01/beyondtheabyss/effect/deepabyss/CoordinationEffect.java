package com.min01.beyondtheabyss.effect.deepabyss;

import java.util.List;
import java.util.UUID;

import com.min01.beyondtheabyss.effect.BasicBTAEffect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CoordinationEffect extends BasicBTAEffect
{
	public double multiplier;
	
	public CoordinationEffect()
	{
		super(MobEffectCategory.BENEFICIAL, 4531998);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.addAttributeModifier(Attributes.ARMOR, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.addAttributeModifier(Attributes.MAX_HEALTH, UUID.randomUUID().toString(), 0, Operation.ADDITION);
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier)
	{
		List<LivingEntity> list = pLivingEntity.level.getEntitiesOfClass(LivingEntity.class, pLivingEntity.getBoundingBox().inflate(pAmplifier * 4), t -> t != pLivingEntity && !t.isAlliedTo(pLivingEntity) && t.isAlive());
		this.multiplier = Math.min(list.size(), pAmplifier * 2);
		if(pLivingEntity.hasEffect(this))
		{
			this.addAttributeModifiers(pLivingEntity, pLivingEntity.getAttributes(), pAmplifier);
		}
	}
	
	@Override
	public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier)
	{
		return this.multiplier * (double)(pAmplifier + 1);
	}
}
