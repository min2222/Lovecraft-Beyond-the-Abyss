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
		
		private static TagKey<EntityType<?>> create(String p_203849_) 
		{
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
	
	public static class BTAItems
	{
		public static final TagKey<Item> DIVING_SET = create("diving_set");
		public static final TagKey<Item> TEETH = create("teeth");
		
		private static TagKey<Item> create(String p_203849_) 
		{
			return TagKey.create(Registries.ITEM, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
	
	public static class BTABlocks
	{
		public static final TagKey<Block> DEATH_VALLEY_REPLACEABLES = create("death_valley_replaceables");
		
		private static TagKey<Block> create(String p_203849_) 
		{
			return TagKey.create(Registries.BLOCK, new ResourceLocation(BeyondtheAbyss.MODID, p_203849_));
		}
	}
}
