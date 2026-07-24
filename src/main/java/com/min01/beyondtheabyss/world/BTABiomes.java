package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BTABiomes 
{
	public static final ResourceKey<Biome> SPIRE_HOLLOW = register("spire_hollow");
	   
	public static ResourceKey<Biome> register(String name)
	{
		return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
	}
}
