package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.DirectionBlockStateConfiguration;
import com.min01.beyondtheabyss.world.feature.deepabyss.AbyssalithSpikeFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.BonePatchFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DirectionBlockFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.GlaringBarnacleFeature;
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
    public static final RegistryObject<Feature<DirectionBlockStateConfiguration>> DIRECTION_BLOCK = FEATURES.register("direction_block", () -> new DirectionBlockFeature(DirectionBlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> TOOTHVINE_PATCH = FEATURES.register("toothvine_patch", () -> new ToothvinePatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ABYSSALITH_SPIKE = FEATURES.register("abyssalith_spike", () -> new AbyssalithSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<DirectionBlockStateConfiguration>> GLARING_BARNACLE = FEATURES.register("glaring_barnacle", () -> new GlaringBarnacleFeature(DirectionBlockStateConfiguration.CODEC));
}
