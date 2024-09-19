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
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BTAPlacedFeatures
{
	//public static final ResourceKey<PlacedFeature> CORAL_TREE = register("coral_tree");
	public static final ResourceKey<PlacedFeature> BONES = register("bones");
	public static final ResourceKey<PlacedFeature> ABYSS_CORALS = register("abyss_corals");
	
	private static ResourceKey<PlacedFeature> register(String p_209839_) 
	{
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
	}
	
	public static void bootstrap(BootstapContext<PlacedFeature> context) 
	{
		HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
		//context.register(CORAL_TREE, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.CORAL_TREE), List.copyOf(List.of(NoiseBasedCountPlacement.of(20, 400.0D, 0.0D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(BONES, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.BONES), List.copyOf(List.of(NoiseBasedCountPlacement.of(10, 200.0D, 0.1D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
		context.register(ABYSS_CORALS, new PlacedFeature(features.getOrThrow(BTAConfiguredFeatures.ABYSS_CORALS), List.copyOf(List.of(NoiseBasedCountPlacement.of(20, 400.0D, 0.0D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	}
}
