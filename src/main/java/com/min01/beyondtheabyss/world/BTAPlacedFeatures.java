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
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BTAPlacedFeatures
{
	public static final ResourceKey<PlacedFeature> SPINES = register("spines");
	public static final ResourceKey<PlacedFeature> BONES = register("bones");
	public static final ResourceKey<PlacedFeature> BONE_SPIKES = register("bone_spikes");
	public static final ResourceKey<PlacedFeature> CORAL_TREES = register("coral_trees");
	public static final ResourceKey<PlacedFeature> FOSSILS = register("fossils");
	
	private static ResourceKey<PlacedFeature> register(String p_209839_) 
	{
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
	}
	
	public static void bootstrap(BootstapContext<PlacedFeature> context) 
	{
		HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
		context.register(SPINES, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.SPINES), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.9D, 7, 12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(BONES, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.BONES), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(BONE_SPIKES, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.BONE_SPIKES), List.copyOf(List.of(CarvingMaskPlacement.forStep(Carving.AIR), BiomeFilter.biome()))));
		context.register(CORAL_TREES, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.CORAL_TREES), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.7D, 3, 8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(FOSSILS, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.FOSSILS), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.2D, 1, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	}
}
