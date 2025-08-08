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
		
		private static ResourceKey<Structure> create(String name) 
		{
			return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BeyondtheAbyss.MODID, name));
		}
	}
}
