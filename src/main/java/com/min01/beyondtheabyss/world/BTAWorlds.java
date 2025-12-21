package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class BTAWorlds
{
	public static final ResourceKey<Level> DEEP_ABYSS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deep_abyss"));
	public static final ResourceKey<Level> EVERGREEN = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "evergreen"));
 	public static final ResourceKey<Level> MIRRORED_CITY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "mirrored_city"));
 	public static final ResourceKey<Level> MOON = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "moon"));
 	public static final ResourceKey<Level> ENDLESS_DESERT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "endless_desert"));
 	public static final ResourceKey<Level> PURGATORY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "purgatory"));
 	public static final ResourceKey<Level> OUTER_SPACE = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "outer_space"));
}
