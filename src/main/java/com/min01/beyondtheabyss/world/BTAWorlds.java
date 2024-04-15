package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class BTAWorlds
{
	public static final ResourceKey<Level> DEEP_ABYSS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, "deep_abyss"));
	//public static final ResourceKey<Level> EVERGREEN = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, "evergreen"));
}
