package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class BTATags 
{
	public static class BTAEntity
	{
		public static final TagKey<EntityType<?>> ABYSS_CREATURE = create("abyss_creature");
		public static final TagKey<EntityType<?>> EVERGREEN_CREATURE = create("evergreen_creature");
		
		private static TagKey<EntityType<?>> create(String p_203849_) 
		{
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
	
	public static class BTAItems
	{
		public static final TagKey<Item> DIVING_SET = create("diving_set");
		
		private static TagKey<Item> create(String p_203849_) 
		{
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
}
