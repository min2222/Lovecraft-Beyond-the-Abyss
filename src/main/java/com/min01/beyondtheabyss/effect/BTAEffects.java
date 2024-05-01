package com.min01.beyondtheabyss.effect;

import java.util.UUID;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.deepabyss.EffectAbyssalScale;
import com.min01.beyondtheabyss.effect.deepabyss.EffectAirSwim;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEffects
{
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<MobEffect> ABYSSAL_SCALE = EFFECTS.register("abyssal_scale", () -> 
	{
		EffectAbyssalScale scale = new EffectAbyssalScale(4);
		scale.addAttributeModifier(Attributes.ARMOR, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		scale.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID.randomUUID().toString(), 0, Operation.ADDITION);
		return scale;
	});
	
	public static final RegistryObject<MobEffect> AIR_SWIM = EFFECTS.register("air_swim", () -> new EffectAirSwim());
	public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", () -> new EffectBleeding());
}
