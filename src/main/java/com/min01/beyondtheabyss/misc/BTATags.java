package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BTATags 
{
	public static final TagKey<Item> DIVING_SET = createItem("diving_set");
	public static final TagKey<Item> TEETH = createItem("teeth");
	public static final TagKey<Item> MUTAVORE_CONSUMABLE = createItem("mutavore_consumable");
	public static final TagKey<Item> MEATS = createItem("meats");
	
	public static final TagKey<Block> SPIRE_HOLLOW_REPLACEABLES = createBlock("spire_hollow_replaceables");
	public static final TagKey<Block> DEATH_VALLEY_REPLACEABLES = createBlock("death_valley_replaceables");
	
	public static final TagKey<EntityType<?>> DEEP_ABYSS_CREATURES = createEntityType("deep_abyss_creatures");
	public static final TagKey<EntityType<?>> DEATH_VALLEY_CREATURES = createEntityType("death_valley_creatures");
	public static final TagKey<EntityType<?>> MINI_BOSSES = createEntityType("mini_bosses");
	
	public static TagKey<Item> createItem(String name) 
	{
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
	}
	
	public static TagKey<Block> createBlock(String name) 
	{
		return BlockTags.create(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
	}
	
	public static TagKey<EntityType<?>> createEntityType(String name)
	{
		return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name));
	}
}
