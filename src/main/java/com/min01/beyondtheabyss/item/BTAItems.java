package com.min01.beyondtheabyss.item;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.armor.AdvancedDivingSetItem;
import com.min01.beyondtheabyss.item.armor.DivingSetItem;
import com.min01.beyondtheabyss.item.armor.GhidruthDivingSetItem;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.item.deepabyss.GhidruthFleshItem;
import com.min01.beyondtheabyss.item.deepabyss.GuidingClamItem;
import com.min01.beyondtheabyss.item.deepabyss.RunicFishItem;
import com.min01.beyondtheabyss.item.weapon.HarpoonItem;
import com.min01.beyondtheabyss.item.weapon.SacrificialDaggerItem;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAItems 
{
    public static final Rarity RARITY_DEEP_ABYSS = Rarity.create("beyondtheabyss:deep_abyss", ChatFormatting.DARK_AQUA);
    
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
	public static final RegistryObject<Item> AMARUM_GHOST_SPAWN_EGG = registerSpawnEgg("amarum_ghost_spawn_egg", () -> BTAEntities.AMARUM_GHOST.get(), 930103, 5756886);
	public static final RegistryObject<Item> GNASHER_SPAWN_EGG = registerSpawnEgg("gnasher_spawn_egg", () -> BTAEntities.GNASHER.get(), 1318679, 3019282);
	public static final RegistryObject<Item> SIAMSERPENT_SPAWN_EGG = registerSpawnEgg("siamserpent_spawn_egg", () -> BTAEntities.SIAMSERPENT_HEAD.get(), 6584912, 9608315);
	
	//materials
	public static final RegistryObject<Item> GHIDRUTH_SCALE = ITEMS.register("ghidruth_scale", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS).rarity(RARITY_DEEP_ABYSS)));
	public static final RegistryObject<Item> OXYGEN_TANK = ITEMS.register("oxygen_tank", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	public static final RegistryObject<Item> VAMPIRE_MEMBRANE = ITEMS.register("vampire_membrane", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	public static final RegistryObject<Item> VAMPIRE_TOOTH = ITEMS.register("vampire_tooth", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MATERIALS)));
	
	//armors
	public static final RegistryObject<Item> DIVING_HELMET = ITEMS.register("diving_helmet", () -> new DivingSetItem(EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DIVING_SUIT = ITEMS.register("diving_suit", () -> new DivingSetItem(EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DIVING_LEGGINGS = ITEMS.register("diving_leggings", () -> new DivingSetItem(EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DIVING_BOOTS = ITEMS.register("diving_boots", () -> new DivingSetItem(EquipmentSlot.FEET));
	
	public static final RegistryObject<Item> ADVANCED_DIVING_HELMET = ITEMS.register("advanced_diving_helmet", () -> new AdvancedDivingSetItem(EquipmentSlot.HEAD));
	public static final RegistryObject<Item> ADVANCED_DIVING_SUIT = ITEMS.register("advanced_diving_suit", () -> new AdvancedDivingSetItem(EquipmentSlot.CHEST));
	public static final RegistryObject<Item> ADVANCED_DIVING_LEGGINGS = ITEMS.register("advanced_diving_leggings", () -> new AdvancedDivingSetItem(EquipmentSlot.LEGS));
	public static final RegistryObject<Item> ADVANCED_DIVING_BOOTS = ITEMS.register("advanced_diving_boots", () -> new AdvancedDivingSetItem(EquipmentSlot.FEET));
	
	public static final RegistryObject<Item> GHIDRUTH_DIVING_HELMET = ITEMS.register("ghidruth_diving_helmet", () -> new GhidruthDivingSetItem(EquipmentSlot.HEAD));
	public static final RegistryObject<Item> GHIDRUTH_DIVING_SUIT = ITEMS.register("ghidruth_diving_suit", () -> new GhidruthDivingSetItem(EquipmentSlot.CHEST));
	public static final RegistryObject<Item> GHIDRUTH_DIVING_LEGGINGS = ITEMS.register("ghidruth_diving_leggings", () -> new GhidruthDivingSetItem(EquipmentSlot.LEGS));
	public static final RegistryObject<Item> GHIDRUTH_DIVING_BOOTS = ITEMS.register("ghidruth_diving_boots", () -> new GhidruthDivingSetItem(EquipmentSlot.FEET));
	
	//misc
	public static final RegistryObject<Item> GUIDING_CLAM = ITEMS.register("guiding_clam", () -> new GuidingClamItem());
	public static final RegistryObject<Item> HEART_OF_FORNEUS = ITEMS.register("heart_of_forneus", () -> new Item(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MISC).rarity(RARITY_DEEP_ABYSS)));
	
	//foods
	public static final RegistryObject<Item> RAW_GHIDRUTH_FLESH = ITEMS.register("raw_ghidruth_flesh", () -> new GhidruthFleshItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).build(), true));
	public static final RegistryObject<Item> COOKED_GHIDRUTH_FLESH = ITEMS.register("cooked_ghidruth_flesh", () -> new GhidruthFleshItem(new FoodProperties.Builder().nutrition(9).saturationMod(1.0F).build(), false));
	public static final RegistryObject<Item> RUNIC_FISH = ITEMS.register("runic_fish", () -> new RunicFishItem(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).alwaysEat().fast().build()));
	public static final RegistryObject<Item> RAW_LATCHER_TAIL = ITEMS.register("raw_latcher_tail", () -> new BasicBTAFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).fast().build()));
	public static final RegistryObject<Item> COOKED_LATCHER_TAIL = ITEMS.register("cooked_latcher_tail", () -> new BasicBTAFoodItem(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).fast().build()));
	
	//blocks
	public static final RegistryObject<Item> RIFTWELLING_ALTAR = registerCustomRendererBlockItem("riftwelling_altar", () -> BTABlocks.RIFTWELLING_ALTAR.get(), () -> new RiftwellingAltarBlockEntity(BlockPos.ZERO, BTABlocks.RIFTWELLING_ALTAR.get().defaultBlockState()), new Item.Properties().rarity(RARITY_DEEP_ABYSS));
	public static final RegistryObject<Item> DEEP_ABYSSALITH = registerBlockItem("deep_abyssalith", () -> BTABlocks.DEEP_ABYSSALITH.get(), new Item.Properties());
	public static final RegistryObject<Item> ABYSSALITH = registerBlockItem("abyssalith", () -> BTABlocks.ABYSSALITH.get(), new Item.Properties());
	public static final RegistryObject<Item> SMALL_BONE = registerBlockItem("small_bone", () -> BTABlocks.SMALL_BONE.get(), new Item.Properties());
	public static final RegistryObject<Item> JAW_BONE = registerBlockItem("jaw_bone", () -> BTABlocks.JAW_BONE.get(), new Item.Properties());
	public static final RegistryObject<Item> RIB = registerBlockItem("rib", () -> BTABlocks.RIB.get(), new Item.Properties());
	public static final RegistryObject<Item> FISH_BONE = registerBlockItem("fish_bone", () -> BTABlocks.FISH_BONE.get(), new Item.Properties());
	public static final RegistryObject<Item> FANG_SKULL = registerNoRotationLimitBlockItem("fang_skull", () -> BTABlocks.FANG_SKULL.get(), new Item.Properties());
	public static final RegistryObject<Item> LARGE_SKULL = registerNoRotationLimitBlockItem("large_skull", () -> BTABlocks.LARGE_SKULL.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINE_BONE_TIP = registerBlockItem("spine_bone_tip", () -> BTABlocks.SPINE_BONE_TIP.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINE_BONE_MIDDLE = registerBlockItem("spine_bone_middle", () -> BTABlocks.SPINE_BONE_MIDDLE.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINE_BONE_BASE = registerBlockItem("spine_bone_base", () -> BTABlocks.SPINE_BONE_BASE.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_PILES = registerNoRotationLimitBlockItem("bone_piles", () -> BTABlocks.BONE_PILES.get(), new Item.Properties());
	public static final RegistryObject<Item> SITTING_SKELETON = registerNoRotationLimitBlockItem("sitting_skeleton", () -> BTABlocks.SITTING_SKELETON.get(), new Item.Properties());
	public static final RegistryObject<Item> FALLEN_SKELETON = registerNoRotationLimitBlockItem("fallen_skeleton", () -> BTABlocks.FALLEN_SKELETON.get(), new Item.Properties());
	public static final RegistryObject<Item> METAL_BRICK = registerBlockItem("metal_brick", () -> BTABlocks.METAL_BRICK.get(), new Item.Properties());
	public static final RegistryObject<Item> METAL_TILE = registerBlockItem("metal_tile", () -> BTABlocks.METAL_TILE.get(), new Item.Properties());
	public static final RegistryObject<Item> METAL_PLATE = registerBlockItem("metal_plate", () -> BTABlocks.METAL_PLATE.get(), new Item.Properties());
	public static final RegistryObject<Item> BLUE_METAL_LANTERN = registerBlockItem("blue_metal_lantern", () -> BTABlocks.BLUE_METAL_LANTERN.get(), new Item.Properties());
	public static final RegistryObject<Item> GREEN_METAL_LANTERN = registerBlockItem("green_metal_lantern", () -> BTABlocks.GREEN_METAL_LANTERN.get(), new Item.Properties());
	public static final RegistryObject<Item> PINK_METAL_LANTERN = registerBlockItem("pink_metal_lantern", () -> BTABlocks.PINK_METAL_LANTERN.get(), new Item.Properties());
	public static final RegistryObject<Item> RED_METAL_LANTERN = registerBlockItem("red_metal_lantern", () -> BTABlocks.RED_METAL_LANTERN.get(), new Item.Properties());
	public static final RegistryObject<Item> METAL_WINDOW = registerBlockItem("metal_window", () -> BTABlocks.METAL_WINDOW.get(), new Item.Properties());
	public static final RegistryObject<Item> BLANK_RUNE_STONE = registerBlockItem("blank_rune_stone", () -> BTABlocks.BLANK_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> SOUL_RUNE_STONE = registerBlockItem("soul_rune_stone", () -> BTABlocks.SOUL_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> CROSS_RUNE_STONE = registerBlockItem("cross_rune_stone", () -> BTABlocks.CROSS_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> WORD_RUNE_STONE = registerBlockItem("word_rune_stone", () -> BTABlocks.WORD_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> VISION_RUNE_STONE = registerBlockItem("vision_rune_stone", () -> BTABlocks.VISION_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> ENERGY_RUNE_STONE = registerBlockItem("energy_rune_stone", () -> BTABlocks.ENERGY_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> SPIKE_RUNE_STONE = registerBlockItem("spike_rune_stone", () -> BTABlocks.SPIKE_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> BLUE_METAL_CRATE = registerBlockItem("blue_metal_crate", () -> BTABlocks.BLUE_METAL_CRATE.get(), new Item.Properties());
	public static final RegistryObject<Item> GREEN_METAL_CRATE = registerBlockItem("green_metal_crate", () -> BTABlocks.GREEN_METAL_CRATE.get(), new Item.Properties());
	public static final RegistryObject<Item> PINK_METAL_CRATE = registerBlockItem("pink_metal_crate", () -> BTABlocks.PINK_METAL_CRATE.get(), new Item.Properties());
	public static final RegistryObject<Item> RED_METAL_CRATE = registerBlockItem("red_metal_crate", () -> BTABlocks.RED_METAL_CRATE.get(), new Item.Properties());
	public static final RegistryObject<Item> DEAD_OSTEO_CORAL_BLOCK = registerBlockItem("dead_osteo_coral_block", () -> BTABlocks.DEAD_OSTEO_CORAL_BLOCK.get(), new Item.Properties());
	public static final RegistryObject<Item> OSTEO_CORAL_BLOCK = registerBlockItem("osteo_coral_block", () -> BTABlocks.OSTEO_CORAL_BLOCK.get(), new Item.Properties());
	public static final RegistryObject<Item> DEAD_OSTEO_CORAL_FAN = ITEMS.register("dead_osteo_coral_fan", () -> new StandingAndWallBlockItem(BTABlocks.DEAD_OSTEO_CORAL_FAN.get(), BTABlocks.DEAD_OSTEO_CORAL_WALL_FAN.get(), (new Item.Properties()).tab(DeepAbyssTabs.ABYSS_BLOCKS)));
	public static final RegistryObject<Item> OSTEO_CORAL_FAN = ITEMS.register("osteo_coral_fan", () -> new StandingAndWallBlockItem(BTABlocks.OSTEO_CORAL_FAN.get(), BTABlocks.OSTEO_CORAL_WALL_FAN.get(), (new Item.Properties()).tab(DeepAbyssTabs.ABYSS_BLOCKS)));
	public static final RegistryObject<Item> WHALEFALL = registerBlockItem("whalefall", () -> BTABlocks.WHALEFALL.get(), new Item.Properties());
	public static final RegistryObject<Item> ROT_SOIL = registerBlockItem("rot_soil", () -> BTABlocks.ROT_SOIL.get(), new Item.Properties());
	public static final RegistryObject<Item> COMPACT_ROT_SOIL = registerBlockItem("compact_rot_soil", () -> BTABlocks.COMPACT_ROT_SOIL.get(), new Item.Properties());

	//weapons
	public static final RegistryObject<Item> RUSTY_HARPOON = ITEMS.register("rusty_harpoon", () -> new HarpoonItem(new Item.Properties().durability(750)));
	public static final RegistryObject<Item> GHIDRUTH_SCALE_HARPOON = ITEMS.register("ghidruth_scale_harpoon", () -> new HarpoonItem(new Item.Properties().durability(3550).rarity(RARITY_DEEP_ABYSS)));
	public static final RegistryObject<Item> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", () -> new SacrificialDaggerItem(new Item.Properties().durability(150)));
	
	//tools
	public static final RegistryObject<Item> FLASHLIGHT = ITEMS.register("flashlight", () -> new FlashlightItem());
	
	public static RegistryObject<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2)
	{
		return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, color1, color2, new Item.Properties().tab(DeepAbyssTabs.ABYSS_MOBS)));
	}
	
	public static RegistryObject<Item> registerNoRotationLimitBlockItem(String name, Supplier<Block> block, Item.Properties propertie)
	{
		return ITEMS.register(name, () -> new CustomRendererBlockItem(block.get(), propertie.tab(DeepAbyssTabs.ABYSS_BLOCKS), () -> new NoRotationLimitBlockEntity(BlockPos.ZERO, block.get().defaultBlockState())));
	}
	
	public static RegistryObject<Item> registerCustomRendererBlockItem(String name, Supplier<Block> block, Supplier<BlockEntity> blockEntity, Item.Properties propertie)
	{
		return ITEMS.register(name, () -> new CustomRendererBlockItem(block.get(), propertie.tab(DeepAbyssTabs.ABYSS_BLOCKS), blockEntity));
	}
	
	public static RegistryObject<Item> registerBlockItem(String name, Supplier<Block> block, Item.Properties propertie)
	{
		return ITEMS.register(name, () -> new BlockItem(block.get(), propertie.tab(DeepAbyssTabs.ABYSS_BLOCKS)));
	}
}
