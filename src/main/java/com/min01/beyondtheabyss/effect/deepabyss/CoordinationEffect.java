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
		super(MobEffectCategory.NEUTRAL, 4531998);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.addAttributeModifier(Attributes.ARMOR, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		this.addAttributeModifier(Attributes.MAX_HEALTH, UUID.randomUUID().toString(), 0, Operation.ADDITION);
	}

	@Override
	public void applyEffectTick(LivingEntity p_19467_, int p_19468_)
	{
		List<LivingEntity> list = p_19467_.level.getEntitiesOfClass(LivingEntity.class, p_19467_.getBoundingBox().inflate(p_19468_ * 4));
		list.removeIf(t -> t == p_19467_ || t.isAlliedTo(p_19467_) || !t.isAlive());
		this.multiplier = Math.min(list.size(), p_19468_ * 2);
		if(p_19467_.hasEffect(this))
		{
			this.addAttributeModifiers(p_19467_, p_19467_.getAttributes(), p_19468_);
		}
	}
	
	@Override
	public double getAttributeModifierValue(int p_19430_, AttributeModifier p_19431_)
	{
		return this.multiplier * (double)(p_19430_ + 1);
	}
}
