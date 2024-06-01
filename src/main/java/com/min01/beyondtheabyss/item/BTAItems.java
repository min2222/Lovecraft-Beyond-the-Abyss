package com.min01.beyondtheabyss.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityRiftwellingAltar;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.armor.ItemAdvancedDiverSet;
import com.min01.beyondtheabyss.item.armor.ItemDiverSet;
import com.min01.beyondtheabyss.item.armor.ItemGhidruthDiverSet;
import com.min01.beyondtheabyss.item.deepabyss.ItemGhidruthFlesh;
import com.min01.beyondtheabyss.item.deepabyss.ItemGuidingClam;
import com.min01.beyondtheabyss.item.deepabyss.ItemRunicFish;
import com.min01.beyondtheabyss.item.renderer.BTABlockEntityItemRenderer;
import com.min01.beyondtheabyss.item.weapon.HarpoonItem;
import com.min01.beyondtheabyss.item.weapon.VampireDaggerItem;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
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
	
    public static final Rarity RARITY_ABYSS = Rarity.create("beyondtheabyss:abyss", ChatFormatting.DARK_AQUA);
    
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	
	//spawn eggs
	public static final RegistryObject<Item> GHIDRUTH_SPAWN_EGG = registerSpawnEgg("ghidruth_spawn_egg", () -> BTAEntities.GHIDRUTH.get(), 862018, 10239048);
	//public static final RegistryObject<Item> FORNEUS_SPAWN_EGG = registerSpawnEgg("forneus_spawn_egg", () -> BTAEntities.FORNEUS_HEAD.get(), 0, 0);
	public static final RegistryObject<Item> DEEP_VAMPIRE_SPAWN_EGG = registerSpawnEgg("deep_vampire_spawn_egg", () -> BTAEntities.DEEP_VAMPIRE.get(), 1510154, 5058091);
	public static final RegistryObject<Item> RUNIC_FISH_SPAWN_EGG = registerSpawnEgg("runic_fish_spawn_egg", () -> BTAEntities.RUNIC_FISH.get(), 2438966, 2263443);
	public static final RegistryObject<Item> LATCHER_SPAWN_EGG = registerSpawnEgg("latcher_spawn_egg", () -> BTAEntities.LATCHER.get(), 3617604, 1841189);
	public static final RegistryObject<Item> ABYSSAL_HERMIT_CRAB_SPAWN_EGG = registerSpawnEgg("abyssal_hermit_crab_spawn_egg", () -> BTAEntities.ABYSSAL_HERMIT_CRAB.get(), 4999486, 2698020);
	public static final RegistryObject<Item> ABYSSAL_BULBRAY_SPAWN_EGG = registerSpawnEgg("abyssal_bulbray_spawn_egg", () -> BTAEntities.ABYSSAL_BULBRAY.get(), 2432294, 2499894);
	public static final RegistryObject<Item> PHASMOZOA_SPAWN_EGG = registerSpawnEgg("phasmozoa_spawn_egg", () -> BTAEntities.PHASMOZOA.get(), 4686944, 8876197);
	
	//materials
	public static final RegistryObject<Item> GHIDRUTH_SCALE = ITEMS.register("ghidruth_scale", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS).rarity(RARITY_ABYSS)));
	public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	public static final RegistryObject<Item> JUGGERNAUT_SUCTION_ORGAN = ITEMS.register("juggernaut_suction_organ", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	public static final RegistryObject<Item> VAMPIRE_MEMBRANE = ITEMS.register("vampire_membrane", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	public static final RegistryObject<Item> VAMPIRE_TOOTH = ITEMS.register("vampire_tooth", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	
	//weapons
	public static final RegistryObject<Item> RUSTY_HARPOON = ITEMS.register("rusty_harpoon", () -> new HarpoonItem(new Item.Properties().durability(750), false));
	public static final RegistryObject<Item> GHIDRUTH_SCALE_HARPOON = ITEMS.register("ghidruth_scale_harpoon", () -> new HarpoonItem(new Item.Properties().durability(3550).rarity(RARITY_ABYSS), true));
	public static final RegistryObject<Item> VAMPIRE_DAGGER = ITEMS.register("vampire_dagger", () -> new VampireDaggerItem(new Item.Properties().durability(150)));
	
	//misc
	public static final RegistryObject<Item> GUIDING_CLAM = ITEMS.register("guiding_clam", () -> new ItemGuidingClam());
	public static final RegistryObject<Item> HEART_OF_FORNEUS = ITEMS.register("heart_of_forneus", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MISC).rarity(RARITY_ABYSS)));
	
	//foods
	public static final RegistryObject<Item> RAW_GHIDRUTH_FLESH = ITEMS.register("raw_ghidruth_flesh", () -> new ItemGhidruthFlesh(new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).build(), true));
	public static final RegistryObject<Item> COOKED_GHIDRUTH_FLESH = ITEMS.register("cooked_ghidruth_flesh", () -> new ItemGhidruthFlesh(new FoodProperties.Builder().nutrition(9).saturationMod(1F).build(), false));
	public static final RegistryObject<Item> RUNIC_FISH = ITEMS.register("runic_fish", () -> new ItemRunicFish(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).alwaysEat().fast().build()));
	public static final RegistryObject<Item> RAW_LATCHER_TAIL = ITEMS.register("raw_latcher_tail", () -> new BasicBTAFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).fast().build()));
	public static final RegistryObject<Item> COOKED_LATCHER_TAIL = ITEMS.register("cooked_latcher_tail", () -> new BasicBTAFoodItem(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).fast().build()));
	
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
	public static final RegistryObject<Item> RIFTWELLING_ALTAR = ITEMS.register("riftwelling_altar", () -> new BlockItem(BTABlocks.RIFTWELLING_ALTAR.get(), new Item.Properties().tab(DeepAbyssTabs.ABYSS_BLOCKS).rarity(RARITY_ABYSS))
	{
		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) 
		{
			consumer.accept(new IClientItemExtensions()
			{
				@Override
				public BlockEntityWithoutLevelRenderer getCustomRenderer() 
				{
					return new BTABlockEntityItemRenderer(new BlockEntityRiftwellingAltar(BlockPos.ZERO, BTABlocks.RIFTWELLING_ALTAR.get().defaultBlockState()), Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
				};
			});
		};
	});
	
	public static final RegistryObject<Item> DEEP_ABYSSALITH = registerBlockItem("deep_abyssalith", () -> BTABlocks.DEEP_ABYSSALITH.get(), new Item.Properties());
	public static final RegistryObject<Item> ABYSSALITH = registerBlockItem("abyssalith", () -> BTABlocks.ABYSSALITH.get(), new Item.Properties());
	
	public static RegistryObject<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2)
	{
		return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, color1, color2, new Item.Properties().tab(DeepAbyssTabs.ABYSS_MOBS)));
	}
	
	public static RegistryObject<Item> registerBlockItem(String name, Supplier<Block> block, Item.Properties propertie)
	{
		return ITEMS.register(name, () -> new BlockItem(block.get(), propertie.tab(DeepAbyssTabs.ABYSS_BLOCKS)));
	}
}
