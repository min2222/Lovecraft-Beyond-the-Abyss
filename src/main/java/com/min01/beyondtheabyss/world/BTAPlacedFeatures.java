package com.min01.beyondtheabyss.world;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAPlacedFeatures 
{
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BeyondtheAbyss.MODID);
	
	//public static final RegistryObject<PlacedFeature> CORAL_TREE = PLACED_FEATURES.register("coral_tree", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.CORAL_TREE.getHolder().get()), List.copyOf(List.of(NoiseBasedCountPlacement.of(20, 400.0D, 0.0D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> ABYSS_CORALS = PLACED_FEATURES.register("abyss_corals", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.ABYSS_CORALS.getHolder().get()), List.copyOf(List.of(NoiseBasedCountPlacement.of(20, 400.0D, 0.0D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
	public static final RegistryObject<PlacedFeature> BONES = PLACED_FEATURES.register("bones", () -> new PlacedFeature(Holder.hackyErase(BTAConfiguredFeatures.BONES.getHolder().get()), List.copyOf(List.of(NoiseBasedCountPlacement.of(10, 200.0D, 0.1D), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()))));
}
