package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BTATags 
{
	public static class BTAEntity
	{
		public static final TagKey<EntityType<?>> ABYSS_CREATURES = create("abyss_creatures");
		public static final TagKey<EntityType<?>> EVERGREEN_CREATURES = create("evergreen_creatures");
		public static final TagKey<EntityType<?>> CITY_CREATURES = create("city_creatures");
		public static final TagKey<EntityType<?>> MINI_BOSSES = create("mini_bosses");
		public static final TagKey<EntityType<?>> FAR_RANGE_TICKING = create("far_range_ticking");
		
		private static TagKey<EntityType<?>> create(String name) 
		{
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, name));
		}
	}
	
	public static class BTAItems
	{
		public static final TagKey<Item> DIVING_SET = create("diving_set");
		public static final TagKey<Item> TEETH = create("teeth");
		public static final TagKey<Item> MUTAVORE_CONSUMABLE = create("mutavore_consumable");
		public static final TagKey<Item> MEATS = create("meats");
		
		private static TagKey<Item> create(String name) 
		{
			return TagKey.create(Registries.ITEM, new ResourceLocation(BeyondtheAbyss.MODID, name));
		}
	}
	
	public static class BTABlocks
	{
		public static final TagKey<Block> SPIRE_HOLLOW_REPLACEABLES = create("spire_hollow_replaceables");
		public static final TagKey<Block> DEATH_VALLEY_REPLACEABLES = create("death_valley_replaceables");
		
		private static TagKey<Block> create(String name) 
		{
			return TagKey.create(Registries.BLOCK, new ResourceLocation(BeyondtheAbyss.MODID, name));
		}
	}
}
