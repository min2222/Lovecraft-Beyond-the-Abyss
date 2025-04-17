package com.min01.beyondtheabyss.effect;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.deepabyss.AbyssalScalesEffect;
import com.min01.beyondtheabyss.effect.deepabyss.CoordinationEffect;
import com.min01.beyondtheabyss.effect.deepabyss.DisorderEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEffects
{
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<MobEffect> ABYSSAL_SCALES = EFFECTS.register("abyssal_scales", () -> new AbyssalScalesEffect());
	
	public static final RegistryObject<MobEffect> AIR_SWIM = EFFECTS.register("air_swim", () -> new BasicBTAEffect(MobEffectCategory.BENEFICIAL, 3407871));
	public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", () -> new BleedingEffect());
	public static final RegistryObject<MobEffect> BLACK_BLEEDING = EFFECTS.register("black_bleeding", () -> new BasicBTAEffect(MobEffectCategory.HARMFUL, 3997955));
	public static final RegistryObject<MobEffect> HALLUCINATION = EFFECTS.register("hallucination", () -> new BasicBTAEffect(MobEffectCategory.HARMFUL, 8109500));
	public static final RegistryObject<MobEffect> LUNGSPORE = EFFECTS.register("lungspore", () -> new BasicBTAEffect(MobEffectCategory.HARMFUL, 3759198));
	public static final RegistryObject<MobEffect> DISORDER = EFFECTS.register("disorder", () -> new DisorderEffect());
	public static final RegistryObject<MobEffect> COORDINATION = EFFECTS.register("coordination", () -> new CoordinationEffect());
	
	public static final RegistryObject<Potion> DISORDER_POTION = POTIONS.register("disorder", () -> new Potion(new MobEffectInstance(DISORDER.get(), 1)));
	public static final RegistryObject<Potion> STRONG_DISORDER_POTION = POTIONS.register("strong_disorder", () -> new Potion(new MobEffectInstance(DISORDER.get(), 1, 1)));
	
	public static final RegistryObject<Potion> COORDINATION_POTION = POTIONS.register("coordination", () -> new Potion(new MobEffectInstance(COORDINATION.get(), 3600)));
	public static final RegistryObject<Potion> STRONG_COORDINATION_POTION = POTIONS.register("strong_coordination", () -> new Potion(new MobEffectInstance(COORDINATION.get(), 1800, 1)));
}
