package com.min01.beyondtheabyss.item;

import java.util.function.Consumer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.armor.ItemAdvancedDiverSet;
import com.min01.beyondtheabyss.item.armor.ItemDiverSet;
import com.min01.beyondtheabyss.item.armor.ItemGhidruthDiverSet;
import com.min01.beyondtheabyss.item.deepabyss.ItemGhidruthFlesh;
import com.min01.beyondtheabyss.item.deepabyss.ItemGuidingClam;
import com.min01.beyondtheabyss.item.renderer.AltarOfDeepItemRenderer;
import com.min01.beyondtheabyss.misc.BTACreativeTabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAItems 
{
	public static final ArmorMaterial DIVING_ARMOR_MATERIAL = new BTAArmorMaterial("diver", new int[]{450, 550, 500, 250}, new int[]{3, 5, 4, 2}, 10, SoundEvents.ARMOR_EQUIP_ELYTRA, 0, 0, () -> Ingredient.EMPTY);
	
	public static final ArmorMaterial ADVANCED_DIVING_ARMOR_MATERIAL = new BTAArmorMaterial("advanced_diver", new int[]{650, 750, 700, 450}, new int[]{4, 6, 5, 3}, 15, SoundEvents.ARMOR_EQUIP_ELYTRA, 1, 0, () -> Ingredient.EMPTY);

	public static final ArmorMaterial GHIDRUTH_DIVING_ARMOR_MATERIAL = new BTAArmorMaterial("ghidruth_diver", new int[]{1650, 1750, 1700, 1450}, new int[]{14, 16, 15, 13}, 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 15, 0.5F, () -> Ingredient.of(BTAItems.GHIDRUTH_SCALE.get()));
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	
	//spawn eggs
	public static final RegistryObject<Item> GHIDRUTH_SPAWN_EGG = ITEMS.register("ghidruth_spawn_egg", () -> new ForgeSpawnEggItem(() -> BTAEntities.GHIDRUTH.get(), 862018, 10239048, new Item.Properties().tab(BTACreativeTabs.ABYSS_MOBS)));
	
	//materials
	public static final RegistryObject<Item> GHIDRUTH_SCALE = ITEMS.register("ghidruth_scale", () -> new Item(new Item.Properties().tab(BTACreativeTabs.ABYSS_MATERIALS)));
	public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().stacksTo(1).tab(BTACreativeTabs.ABYSS_MATERIALS)));
	
	//misc
	public static final RegistryObject<Item> GUIDING_CLAM = ITEMS.register("guiding_clam", () -> new ItemGuidingClam());
	
	//foods
	public static final RegistryObject<Item> RAW_GHIDRUTH_FLESH = ITEMS.register("raw_ghidruth_flesh", () -> new ItemGhidruthFlesh(new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).build(), true));
	public static final RegistryObject<Item> COOKED_GHIDRUTH_FLESH = ITEMS.register("cooked_ghidruth_flesh", () -> new ItemGhidruthFlesh(new FoodProperties.Builder().nutrition(9).saturationMod(1F).build(), false));
	
	//armors
	public static final RegistryObject<Item> DIVING_HELMET = ITEMS.register("diving_helmet", () -> new ItemDiverSet(DIVING_ARMOR_MATERIAL, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DIVING_SUIT = ITEMS.register("diving_suit", () -> new ItemDiverSet(DIVING_ARMOR_MATERIAL, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DIVING_LEGGINGS = ITEMS.register("diving_leggings", () -> new ItemDiverSet(DIVING_ARMOR_MATERIAL, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DIVING_BOOTS = ITEMS.register("diving_boots", () -> new ItemDiverSet(DIVING_ARMOR_MATERIAL, EquipmentSlot.FEET));
	
	public static final RegistryObject<Item> ADVANCED_DIVING_HELMET = ITEMS.register("advanced_diving_helmet", () -> new ItemAdvancedDiverSet(ADVANCED_DIVING_ARMOR_MATERIAL, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> ADVANCED_DIVING_SUIT = ITEMS.register("advanced_diving_suit", () -> new ItemAdvancedDiverSet(ADVANCED_DIVING_ARMOR_MATERIAL, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> ADVANCED_DIVING_LEGGINGS = ITEMS.register("advanced_diving_leggings", () -> new ItemAdvancedDiverSet(ADVANCED_DIVING_ARMOR_MATERIAL, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> ADVANCED_DIVING_BOOTS = ITEMS.register("advanced_diving_boots", () -> new ItemAdvancedDiverSet(ADVANCED_DIVING_ARMOR_MATERIAL, EquipmentSlot.FEET));
	
	public static final RegistryObject<Item> GHIDRUTH_DIVING_HELMET = ITEMS.register("ghidruth_diving_helmet", () -> new ItemGhidruthDiverSet(GHIDRUTH_DIVING_ARMOR_MATERIAL, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> GHIDRUTH_DIVING_SUIT = ITEMS.register("ghidruth_diving_suit", () -> new ItemGhidruthDiverSet(GHIDRUTH_DIVING_ARMOR_MATERIAL, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> GHIDRUTH_DIVING_LEGGINGS = ITEMS.register("ghidruth_diving_leggings", () -> new ItemGhidruthDiverSet(GHIDRUTH_DIVING_ARMOR_MATERIAL, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> GHIDRUTH_DIVING_BOOTS = ITEMS.register("ghidruth_diving_boots", () -> new ItemGhidruthDiverSet(GHIDRUTH_DIVING_ARMOR_MATERIAL, EquipmentSlot.FEET));
	
	//blocks
	public static final RegistryObject<Item> ALTAR_OF_DEEP = ITEMS.register("altar_of_deep", () -> new BlockItem(BTABlocks.ALTAR_OF_DEEP.get(), new Item.Properties().tab(BTACreativeTabs.ABYSS_BLOCKS).rarity(Rarity.EPIC))
	{
		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) 
		{
			consumer.accept(new IClientItemExtensions()
			{
				@Override
				public BlockEntityWithoutLevelRenderer getCustomRenderer() 
				{
					return new AltarOfDeepItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
				};
			});
		};
	});
}
