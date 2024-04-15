package com.min01.beyondtheabyss.misc;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class BTATags 
{
	public static class BTAEntity
	{
		public static final TagKey<EntityType<?>> ABYSS_CREATURE = create("abyss_creature");
		public static final TagKey<EntityType<?>> EVERGREEN_CREATURE = create("evergreen_creature");
		
		private static TagKey<EntityType<?>> create(String p_203849_) 
		{
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(p_203849_));
		}
	}
}
