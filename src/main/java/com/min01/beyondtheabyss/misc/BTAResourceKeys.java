package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BTAResourceKeys
{
	public static class BTAStructures
	{
		public static final ResourceKey<Structure> HUT = create("hut");
		public static final ResourceKey<Structure> DEEP_ABYSS_PORTAL = create("deep_abyss_portal");
		
		private static ResourceKey<Structure> create(String name) 
		{
			return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BeyondtheAbyss.MODID, name));
		}
	}
}
