package com.min01.beyondtheabyss.effect;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.deepabyss.AbyssalScalesEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEffects
{
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<MobEffect> ABYSSAL_SCALES = EFFECTS.register("abyssal_scales", () -> new AbyssalScalesEffect(4));
	
	public static final RegistryObject<MobEffect> AIR_SWIM = EFFECTS.register("air_swim", () -> new BasicBTAEffect(MobEffectCategory.BENEFICIAL, 3407871));
	public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", () -> new BleedingEffect());
	public static final RegistryObject<MobEffect> BLACK_BLEEDING = EFFECTS.register("black_bleeding", () -> new BasicBTAEffect(MobEffectCategory.HARMFUL, 3997955));
	public static final RegistryObject<MobEffect> HALLUCINATION = EFFECTS.register("hallucination", () -> new BasicBTAEffect(MobEffectCategory.HARMFUL, 8109500));
	public static final RegistryObject<MobEffect> LUNGSPORE = EFFECTS.register("lungspore", () -> new BasicBTAEffect(MobEffectCategory.HARMFUL, 3759198));
}
