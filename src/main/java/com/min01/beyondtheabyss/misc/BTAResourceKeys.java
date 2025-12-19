package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BTAResourceKeys
{
	public static class BTAStructures
	{
		public static final List<ResourceKey<Structure>> LIST = new ArrayList<>();
		
		public static final ResourceKey<Structure> HUT = create("hut");
		public static final ResourceKey<Structure> DEEP_ABYSS_PORTAL = create("deep_abyss_portal");
    	public static final ResourceKey<Structure> GIANT_FOSSIL = create("giant_fossil");
		
		private static ResourceKey<Structure> create(String name) 
		{
			ResourceKey<Structure> key = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BeyondtheAbyss.MODID, name));
			LIST.add(key);
			return key;
		}
		
		public static ResourceKey<Structure> getKeyByName(String name)
		{
			for(ResourceKey<Structure> key : LIST)
			{
				if(key.location().toString().equals(name))
				{
					return key;
				}
			}
			return HUT;
		}
	}
}
