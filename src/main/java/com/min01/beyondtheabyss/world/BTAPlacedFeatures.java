package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BTAPlacedFeatures
{
	public static final ResourceKey<PlacedFeature> SPINE = register("spine");
	public static final ResourceKey<PlacedFeature> BONE = register("bone");
	public static final ResourceKey<PlacedFeature> BONE_SPIKE = register("bone_spike");
	public static final ResourceKey<PlacedFeature> CORAL_TREE = register("coral_tree");
	public static final ResourceKey<PlacedFeature> FOSSIL = register("fossil");
	public static final ResourceKey<PlacedFeature> GHOUL_BLOOM_PATCH = register("ghoul_bloom_patch");
	public static final ResourceKey<PlacedFeature> TOOTHVINE_PATCH = register("toothvine_patch");
	public static final ResourceKey<PlacedFeature> STONE_SPIKE = register("stone_spike");
	
	private static ResourceKey<PlacedFeature> register(String p_209839_) 
	{
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
	}
	
	public static void bootstrap(BootstapContext<PlacedFeature> context) 
	{
		HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
		context.register(SPINE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.SPINE), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.9D, 7, 12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(BONE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.BONE), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(BONE_SPIKE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.BONE_SPIKE), List.copyOf(List.of(CarvingMaskPlacement.forStep(Carving.AIR), BiomeFilter.biome()))));
		context.register(CORAL_TREE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.CORAL_TREE), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.7D, 3, 8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(FOSSIL, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.FOSSIL), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.2D, 1, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(GHOUL_BLOOM_PATCH, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.GHOUL_BLOOM_PATCH), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-1.1D, 8, 13), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(TOOTHVINE_PATCH, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.TOOTHVINE_PATCH), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-1.0D, 7, 13), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(STONE_SPIKE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.STONE_SPIKE), List.copyOf(List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	}
}
