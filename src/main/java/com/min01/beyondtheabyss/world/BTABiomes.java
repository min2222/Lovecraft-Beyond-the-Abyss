package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BTABiomes 
{
	public static final ResourceKey<Biome> SPIRE_HOLLOW = ResourceKey.create(Registries.BIOME, new ResourceLocation(BeyondtheAbyss.MODID, "spire_hollow"));
}
