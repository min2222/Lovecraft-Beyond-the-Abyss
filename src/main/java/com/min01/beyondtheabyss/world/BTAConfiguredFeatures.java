package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;

public class BTAConfiguredFeatures 
{
	public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_TREE = register("coral_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BONES = register("bones");
	
	private static ResourceKey<ConfiguredFeature<?, ?>> register(String p_209839_) 
	{
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
	}
	
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
		context.register(CORAL_TREE, new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(BTAFeatures.CORAL_TREE.get(), FeatureConfiguration.NONE)))));
		context.register(BONES, new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(BTAFeatures.BONES.get(), FeatureConfiguration.NONE)))));
    }	
}
