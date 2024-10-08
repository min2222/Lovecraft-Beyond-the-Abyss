package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAPlacedFeatures 
{
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BeyondtheAbyss.MODID);

	public static final RegistryObject<PlacedFeature> SPINES = PLACED_FEATURES.register("spines", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.SPINES.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.9D, 7, 12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> BONES = PLACED_FEATURES.register("bones", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.BONES.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> BONE_SPIKES = PLACED_FEATURES.register("bone_spikes", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.BONE_SPIKES.getHolder().get()), List.copyOf(List.of(CarvingMaskPlacement.forStep(Carving.AIR), BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> CORAL_TREES = PLACED_FEATURES.register("coral_trees", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.CORAL_TREES.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.7D, 3, 8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> FOSSILS = PLACED_FEATURES.register("fossils", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.FOSSILS.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.2D, 1, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
}
