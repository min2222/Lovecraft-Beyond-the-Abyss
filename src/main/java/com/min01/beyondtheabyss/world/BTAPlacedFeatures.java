package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAPlacedFeatures 
{
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BeyondtheAbyss.MODID);

	public static final RegistryObject<PlacedFeature> SPINE = PLACED_FEATURES.register("spine", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.SPINE.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.9D, 7, 12), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> BONE = PLACED_FEATURES.register("bone", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.BONE.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> BONE_SPIKE = PLACED_FEATURES.register("bone_spike", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.BONE_SPIKE.getHolder().get()), List.copyOf(List.of(CarvingMaskPlacement.forStep(Carving.AIR), BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> CORAL_TREE = PLACED_FEATURES.register("coral_tree", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.CORAL_TREE.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.7D, 3, 8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> FOSSIL = PLACED_FEATURES.register("fossil", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.FOSSIL.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-0.2D, 1, 5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> GHOUL_BLOOM_PATCH = PLACED_FEATURES.register("ghoul_bloom_patch", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.GHOUL_BLOOM_PATCH.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-1.1D, 8, 13), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> TOOTHVINE_PATCH = PLACED_FEATURES.register("toothvine_patch", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.TOOTHVINE_PATCH.getHolder().get()), List.copyOf(List.of(NoiseThresholdCountPlacement.of(-1.0D, 7, 13), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> STONE_SPIKE = PLACED_FEATURES.register("stone_spike", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.STONE_SPIKE.getHolder().get()), List.copyOf(List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
}
