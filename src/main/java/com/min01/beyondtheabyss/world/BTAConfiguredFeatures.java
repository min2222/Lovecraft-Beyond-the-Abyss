package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAConfiguredFeatures 
{
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BeyondtheAbyss.MODID);
	
	//public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> CORAL_TREE = CONFIGURED_FEATURES.register("coral_tree", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(BTAFeatures.CORAL_TREE.get(), FeatureConfiguration.NONE)))));
    public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> ABYSS_CORALS = CONFIGURED_FEATURES.register("abyss_corals", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(BTAFeatures.CORAL_TREE.get(), FeatureConfiguration.NONE), PlacementUtils.inlinePlaced(BTAFeatures.CORAL_CLAW.get(), FeatureConfiguration.NONE), PlacementUtils.inlinePlaced(BTAFeatures.CORAL_MUSHROOM.get(), FeatureConfiguration.NONE)))));
    public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> BONES = CONFIGURED_FEATURES.register("bones", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(BTAFeatures.BONES.get(), FeatureConfiguration.NONE)))));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> SPINES = CONFIGURED_FEATURES.register("spines", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, spinePatch(32)));
    
    public static RandomPatchConfiguration spinePatch(int p_195204_) 
    {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.filtered(BTAFeatures.SPINES.get(), new NoneFeatureConfiguration(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE));
    }
}
