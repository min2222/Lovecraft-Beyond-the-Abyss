package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.deepabyss.ListFeatureConfiguration;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.registries.RegistryObject;

public class BTAConfiguredFeatures 
{
	public static final List<ResourceLocation> BONE_LOCATION = List.of(
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_1"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_2"), 
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_3"),
			new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/bone_4"));

	public static final ResourceKey<ConfiguredFeature<?, ?>> SPINE = register("spine");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BONE = register("bone");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GHOUL_BLOOM_PATCH = register("ghoul_bloom_patch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TOOTHVINE_PATCH = register("toothvine_patch");
	
	private static ResourceKey<ConfiguredFeature<?, ?>> register(String p_209839_) 
	{
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
	}
	
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
		context.register(SPINE, new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatch(BTAFeatures.SPINE, 32)));
		context.register(BONE, new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomListPatch(BTAFeatures.BONE, 16, BONE_LOCATION)));
		context.register(GHOUL_BLOOM_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatch(BTAFeatures.GHOUL_BLOOM_PATCH, 48)));
		context.register(TOOTHVINE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH, randomPatch(BTAFeatures.TOOTHVINE_PATCH, 54)));
    }
    
    public static RandomPatchConfiguration randomListPatch(RegistryObject<Feature<ListFeatureConfiguration>> feature, int tries, List<ResourceLocation> structures) 
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.filtered(feature.get(), new ListFeatureConfiguration(structures), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE));
    }
    
    public static RandomPatchConfiguration randomPatch(RegistryObject<Feature<NoneFeatureConfiguration>> feature, int xzSpread, int ySpread, int tries) 
    {
    	return new RandomPatchConfiguration(tries, xzSpread, ySpread, PlacementUtils.filtered(feature.get(), new NoneFeatureConfiguration(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE));
    }
    
    public static RandomPatchConfiguration randomPatch(RegistryObject<Feature<NoneFeatureConfiguration>> feature, int tries)
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.filtered(feature.get(), new NoneFeatureConfiguration(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE));
    }
}
