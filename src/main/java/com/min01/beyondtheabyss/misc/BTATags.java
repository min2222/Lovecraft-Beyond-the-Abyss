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
		public static final TagKey<EntityType<?>> DEEP_ABYSS_CREATURES = create("deep_abyss_creatures");
		public static final TagKey<EntityType<?>> DEATH_VALLEY_CREATURES = create("death_valley_creatures");
		public static final TagKey<EntityType<?>> EVERGREEN_CREATURES = create("evergreen_creatures");
		public static final TagKey<EntityType<?>> MIRRORED_CITY_CREATURES = create("mirrored_city_creatures");
		public static final TagKey<EntityType<?>> MOON_CREATURES = create("moon_creatures");
		public static final TagKey<EntityType<?>> ENDLESS_DESERT_CREATURES = create("endless_desert_creatures");
		public static final TagKey<EntityType<?>> PURGATORY_CREATURES = create("purgatory_creatures");
		public static final TagKey<EntityType<?>> OUTER_SPACE_CREATURES = create("outer_space_creatures");
		public static final TagKey<EntityType<?>> MINI_BOSSES = create("mini_bosses");
		
		private static TagKey<EntityType<?>> create(String name) 
		{
			return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
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
			return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
		}
	}
	
	public static class BTABlocks
	{
		public static final TagKey<Block> SPIRE_HOLLOW_REPLACEABLES = create("spire_hollow_replaceables");
		public static final TagKey<Block> DEATH_VALLEY_REPLACEABLES = create("death_valley_replaceables");
		
		private static TagKey<Block> create(String name) 
		{
			return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
		}
	}
}
