package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Registry;
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
		
		private static TagKey<EntityType<?>> create(String p_203849_) 
		{
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
	
	public static class BTAItems
	{
		public static final TagKey<Item> DIVING_SET = create("diving_set");
		public static final TagKey<Item> TEETH = create("teeth");
		
		private static TagKey<Item> create(String p_203849_) 
		{
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
	
	public static class BTABlocks
	{
		public static final TagKey<Block> ABYSS_CARVER_REPLACEABLES = create("abyss_carver_replaceables");
		public static final TagKey<Block> DEATH_VALLEY_REPLACEABLES = create("death_valley_replaceables");
		public static final TagKey<Block> ABYSS_CORALS = create("abyss_corals");
		public static final TagKey<Block> ABYSS_CORAL_BLOCKS = create("abyss_coral_blocks");
		public static final TagKey<Block> ABYSS_WALL_CORALS = create("abyss_wall_corals");
		
		private static TagKey<Block> create(String p_203849_) 
		{
			return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
}
