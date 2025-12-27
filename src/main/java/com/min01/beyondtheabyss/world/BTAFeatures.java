package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.deepabyss.AbyssalithSpikeFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.BonePatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeepweedPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.GhoulBloomPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.OsteoCoralPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.SpinyweedPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.ToothvinePatchFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAFeatures 
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BONE_PATCH = FEATURES.register("bone_patch", () -> new BonePatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GHOUL_BLOOM_PATCH = FEATURES.register("ghoul_bloom_patch", () -> new GhoulBloomPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> DEEPWEED_PATCH = FEATURES.register("deepweed_patch", () -> new DeepweedPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPINYWEED_PATCH = FEATURES.register("spinyweed_patch", () -> new SpinyweedPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> OSTEO_CORAL_PATCH = FEATURES.register("osteo_coral_patch", () -> new OsteoCoralPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> TOOTHVINE_PATCH = FEATURES.register("toothvine_patch", () -> new ToothvinePatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ABYSSALITH_SPIKE = FEATURES.register("abyssalith_spike", () -> new AbyssalithSpikeFeature(NoneFeatureConfiguration.CODEC));
}
