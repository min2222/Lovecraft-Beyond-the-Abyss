package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class BTAWorlds
{
	public static final ResourceKey<Level> DEEP_ABYSS = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deep_abyss"));
}
