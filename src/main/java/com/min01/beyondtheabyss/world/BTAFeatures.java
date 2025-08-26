package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.deepabyss.AbyssalithSpikeFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyBonePatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyDeepweedPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyGhoulBloomPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleySpinyweedPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyToothvinePatchFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAFeatures 
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BONE_PATCH = FEATURES.register("bone_patch", () -> new DeathValleyBonePatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GHOUL_BLOOM_PATCH = FEATURES.register("ghoul_bloom_patch", () -> new DeathValleyGhoulBloomPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> DEEPWEED_PATCH = FEATURES.register("deepweed_patch", () -> new DeathValleyDeepweedPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPINYWEED_PATCH = FEATURES.register("spinyweed_patch", () -> new DeathValleySpinyweedPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> TOOTHVINE_PATCH = FEATURES.register("toothvine_patch", () -> new DeathValleyToothvinePatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ABYSSALITH_SPIKE = FEATURES.register("abyssalith_spike", () -> new AbyssalithSpikeFeature(NoneFeatureConfiguration.CODEC));
}
