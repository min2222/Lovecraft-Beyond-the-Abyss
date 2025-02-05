package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BTAPlacedFeatures
{
	public static final ResourceKey<PlacedFeature> SPINE = register("spine");
	public static final ResourceKey<PlacedFeature> BONE = register("bone");
	public static final ResourceKey<PlacedFeature> FOSSIL = register("fossil");
	public static final ResourceKey<PlacedFeature> GHOUL_BLOOM_PATCH = register("ghoul_bloom_patch");
	public static final ResourceKey<PlacedFeature> TOOTHVINE_PATCH = register("toothvine_patch");
	
	private static ResourceKey<PlacedFeature> register(String p_209839_) 
	{
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
	}
	
	public static void bootstrap(BootstapContext<PlacedFeature> context) 
	{
		HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
		context.register(SPINE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.SPINE), List.of(CountPlacement.of(5), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome())));
		context.register(BONE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.BONE), List.copyOf(List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()))));
		context.register(FOSSIL, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.FOSSIL), List.copyOf(List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()))));
		context.register(GHOUL_BLOOM_PATCH, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.GHOUL_BLOOM_PATCH), List.copyOf(List.of(CountPlacement.of(5), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()))));
		context.register(TOOTHVINE_PATCH, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.TOOTHVINE_PATCH), List.copyOf(List.of(CountPlacement.of(10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()))));
	}
}
