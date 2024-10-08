package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.deepabyss.ListFeatureConfiguration;

import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAConfiguredFeatures 
{
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BeyondtheAbyss.MODID);

	public static final List<ResourceLocation> CORAL_TREE_LOCATION = List.of(
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/coral_tree_1"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/coral_tree_2"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/coral_tree_3"),
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/coral_tree_4"),
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/coral_tree_5"));
	
	public static final List<ResourceLocation> BONE_LOCATION = List.of(
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_1"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_2"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_3"),
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_4"));
	
	public static final List<ResourceLocation> BONE_SPIKE_LOCATION = List.of(
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_spike_1"));
	
	public static final List<ResourceLocation> FOSSIL_LOCATION = List.of(
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/fossil_1"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/fossil_2"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/fossil_3"));
	
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> SPINES = CONFIGURED_FEATURES.register("spines", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatch(BTAFeatures.SPINES, 32)));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> BONES = CONFIGURED_FEATURES.register("bones", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomListPatch(BTAFeatures.BONES, 32, BONE_LOCATION)));
    public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> BONE_SPIKES = CONFIGURED_FEATURES.register("bone_spikes", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(BTAFeatures.BONE_SPIKES.get(), new ListFeatureConfiguration(BONE_SPIKE_LOCATION))))));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> CORAL_TREES = CONFIGURED_FEATURES.register("coral_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomListPatch(BTAFeatures.CORAL_TREES, 32, CORAL_TREE_LOCATION)));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FOSSILS = CONFIGURED_FEATURES.register("fossils", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomListPatch(BTAFeatures.FOSSILS, 32, FOSSIL_LOCATION)));
    
    public static RandomPatchConfiguration randomListPatch(RegistryObject<Feature<ListFeatureConfiguration>> feature, int tries, List<ResourceLocation> structures) 
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.filtered(feature.get(), new ListFeatureConfiguration(structures), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE));
    }
    
    public static RandomPatchConfiguration randomPatch(RegistryObject<Feature<NoneFeatureConfiguration>> feature, int tries) 
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.filtered(feature.get(), new NoneFeatureConfiguration(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE));
    }
}
