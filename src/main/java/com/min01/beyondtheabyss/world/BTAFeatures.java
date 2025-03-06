package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyBoneFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyGhoulBloomPatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleySpineFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyToothvinePatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.ListFeatureConfiguration;
import com.min01.beyondtheabyss.world.feature.deepabyss.AbyssalithSpikeFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAFeatures 
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPINE = FEATURES.register("spine", () -> new DeathValleySpineFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ListFeatureConfiguration>> BONE = FEATURES.register("bone", () -> new DeathValleyBoneFeature(ListFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GHOUL_BLOOM_PATCH = FEATURES.register("ghoul_bloom_patch", () -> new DeathValleyGhoulBloomPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> TOOTHVINE_PATCH = FEATURES.register("toothvine_patch", () -> new DeathValleyToothvinePatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ABYSSALITH_SPIKE = FEATURES.register("abyssalith_spike", () -> new AbyssalithSpikeFeature(NoneFeatureConfiguration.CODEC));
}
