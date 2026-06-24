package com.min01.beyondtheabyss.item;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.AnimatableBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.BiocrafterBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.ChainTrapBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.deepabyss.ClamOfGuidanceItem;
import com.min01.beyondtheabyss.item.deepabyss.FelmetalDivingSetItem;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.item.deepabyss.GhidruthFleshItem;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.deepabyss.SubmarineItem;
import com.min01.beyondtheabyss.item.deepabyss.ToothShotgunItem;
import com.min01.beyondtheabyss.misc.BTAFoods;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
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
    
	public static final DeferredRegister<Item> DEEP_ABYSS_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Item> EVERGREEN_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Item> MIRRORED_CITY_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Item> MOON_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Item> ENDLESS_DESERT_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Item> PURGATORY_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Item> OUTER_SPACE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BeyondtheAbyss.MODID);
	
	//spawn eggs
	public static final RegistryObject<Item> MYSTERIOUS_GUY_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "myterious_guy_spawn_egg", () -> BTAEntities.MYSTERIOUS_GUY.get(), 5588543, 4339505);
	public static final RegistryObject<Item> GHIDRUTH_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "ghidruth_spawn_egg", () -> BTAEntities.GHIDRUTH.get(), 862018, 10239048);
	//public static final RegistryObject<Item> FORNEUS_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "forneus_spawn_egg", () -> BTAEntities.FORNEUS_HEAD.get(), 0, 0);
	public static final RegistryObject<Item> GNASHER_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "gnasher_spawn_egg", () -> BTAEntities.GNASHER.get(), 1318679, 3019282);
	public static final RegistryObject<Item> SIAMSERPENT_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "siamserpent_spawn_egg", () -> BTAEntities.SIAMSERPENT_HEAD.get(), 7035974, 9537638);
	public static final RegistryObject<Item> SPINE_WORM_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "spine_worm_spawn_egg", () -> BTAEntities.SPINE_WORM_HEAD.get(), 4068636, 15131359);
	public static final RegistryObject<Item> GLOOMFISH_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "gloomfish_spawn_egg", () -> BTAEntities.GLOOMFISH.get(), 526088, 12060438);
	//public static final RegistryObject<Item> KORMOS_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "kormos_spawn_egg", () -> BTAEntities.KORMOS.get(), 9338740, 4605533);
	public static final RegistryObject<Item> CORPSE_ANGLER_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "corpse_angler_spawn_egg", () -> BTAEntities.CORPSE_ANGLER.get(), 6239541, 1864119);
	public static final RegistryObject<Item> MUTAVORE_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "mutavore_spawn_egg", () -> BTAEntities.MUTAVORE.get(), 7692894, 4921891);
	public static final RegistryObject<Item> FULGASTRA_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "fulgastra_spawn_egg", () -> BTAEntities.FULGASTRA.get(), 4596012, 46834);
	public static final RegistryObject<Item> NECROSHELL_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "necroshell_spawn_egg", () -> BTAEntities.NECROSHELL.get(), 3286815, 8878945);
	public static final RegistryObject<Item> LITHOSHRIMP_SPAWN_EGG = registerSpawnEgg(DEEP_ABYSS_ITEMS, "lithoshrimp_spawn_egg", () -> BTAEntities.LITHOSHRIMP.get(), 2761774, 6051698);
	
	public static final RegistryObject<Item> OVERSEER_SPAWN_EGG = registerSpawnEgg(MIRRORED_CITY_ITEMS, "overseer_spawn_egg", () -> BTAEntities.OVERSEER.get(), 3752532, 7174016);
	public static final RegistryObject<Item> OBSERVER_SPAWN_EGG = registerSpawnEgg(MIRRORED_CITY_ITEMS, "observer_spawn_egg", () -> BTAEntities.OBSERVER.get(), 3752532, 2699068);

	public static final RegistryObject<Item> DUNE_DEVOURER_SPAWN_EGG = registerSpawnEgg(ENDLESS_DESERT_ITEMS, "dune_devourer_spawn_egg", () -> BTAEntities.DUNE_DEVOURER_HEAD.get(), 9595205, 5058351);
	
	//materials
	public static final RegistryObject<Item> GHIDRUTH_SCALE = DEEP_ABYSS_ITEMS.register("ghidruth_scale", () -> new Item(new Item.Properties().rarity(RARITY_DEEP_ABYSS)));
	public static final RegistryObject<Item> GNASHER_EYE = DEEP_ABYSS_ITEMS.register("gnasher_eye", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GNASHER_TOOTH = DEEP_ABYSS_ITEMS.register("gnasher_tooth", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GHOUL_BLOOM_SEED_POD = DEEP_ABYSS_ITEMS.register("ghoul_bloom_seed_pod", () -> new ItemNameBlockItem(BTABlocks.GHOUL_BLOOM.get(), new Item.Properties().food(BTAFoods.GHOUL_BLOOM)));
	public static final RegistryObject<Item> COOKED_GHOUL_BLOOM_SEED_POD = DEEP_ABYSS_ITEMS.register("cooked_ghoul_bloom_seed_pod", () -> new Item(new Item.Properties().food(BTAFoods.COOKED_GHOUL_BLOOM)));
	public static final RegistryObject<Item> FIBER = DEEP_ABYSS_ITEMS.register("fiber", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ABERRANT_FLESH = DEEP_ABYSS_ITEMS.register("aberrant_flesh", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CHARGE_CORE = DEEP_ABYSS_ITEMS.register("charge_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SPLITTING_GEL = DEEP_ABYSS_ITEMS.register("splitting_gel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SPINE_WORM_MANDIBLE = DEEP_ABYSS_ITEMS.register("spine_worm_mandible", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SLASHER_BLADE = DEEP_ABYSS_ITEMS.register("slasher_blade", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BLASTER_SKULL = DEEP_ABYSS_ITEMS.register("blaster_skull", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SERPENT_HEART = DEEP_ABYSS_ITEMS.register("serpent_heart", () -> new Item(new Item.Properties()));
	
	//armors
	public static final RegistryObject<Item> FELMETAL_DIVING_HELMET = DEEP_ABYSS_ITEMS.register("felmetal_diving_helmet", () -> new FelmetalDivingSetItem(ArmorItem.Type.HELMET));
	public static final RegistryObject<Item> FELMETAL_DIVING_SUIT = DEEP_ABYSS_ITEMS.register("felmetal_diving_suit", () -> new FelmetalDivingSetItem(ArmorItem.Type.CHESTPLATE));
	public static final RegistryObject<Item> FELMETAL_DIVING_LEGGINGS = DEEP_ABYSS_ITEMS.register("felmetal_diving_leggings", () -> new FelmetalDivingSetItem(ArmorItem.Type.LEGGINGS));
	public static final RegistryObject<Item> FELMETAL_DIVING_BOOTS = DEEP_ABYSS_ITEMS.register("felmetal_diving_boots", () -> new FelmetalDivingSetItem(ArmorItem.Type.BOOTS));
	
	//misc
	public static final RegistryObject<Item> CLAM_OF_GUIDANCE = DEEP_ABYSS_ITEMS.register("clam_of_guidance", () -> new ClamOfGuidanceItem());
	public static final RegistryObject<Item> HEART_OF_FORNEUS = DEEP_ABYSS_ITEMS.register("heart_of_forneus", () -> new Item(new Item.Properties().rarity(RARITY_DEEP_ABYSS)));
	public static final RegistryObject<Item> FLYING_FISH = DEEP_ABYSS_ITEMS.register("flying_fish", () -> new FlyingFishItem());
	public static final RegistryObject<Item> SUBMARINE = DEEP_ABYSS_ITEMS.register("submarine", () -> new SubmarineItem());
	
	//foods
	public static final RegistryObject<Item> RAW_GHIDRUTH_FLESH = DEEP_ABYSS_ITEMS.register("raw_ghidruth_flesh", () -> new GhidruthFleshItem(BTAFoods.RAW_GHIDRUTH_FLESH, true));
	public static final RegistryObject<Item> COOKED_GHIDRUTH_FLESH = DEEP_ABYSS_ITEMS.register("cooked_ghidruth_flesh", () -> new GhidruthFleshItem(BTAFoods.COOKED_GHIDRUTH_FLESH, false));
	public static final RegistryObject<Item> RAW_GNASHER = DEEP_ABYSS_ITEMS.register("raw_gnasher", () -> new BasicBTAFoodItem(BTAFoods.RAW_GNASHER));
	public static final RegistryObject<Item> COOKED_GNASHER = DEEP_ABYSS_ITEMS.register("cooked_gnasher", () -> new BasicBTAFoodItem(BTAFoods.COOKED_GNASHER));
	public static final RegistryObject<Item> RAW_LITHOSHRIMP = DEEP_ABYSS_ITEMS.register("raw_lithoshrimp", () -> new BasicBTAFoodItem(BTAFoods.RAW_LITHOSHRIMP));
	public static final RegistryObject<Item> COOKED_LITHOSHRIMP = DEEP_ABYSS_ITEMS.register("cooked_lithoshrimp", () -> new BasicBTAFoodItem(BTAFoods.COOKED_LITHOSHRIMP));
	
	//blocks
	public static final RegistryObject<Item> RIFTWELLING_ALTAR = registerCustomRendererBlockItem(DEEP_ABYSS_ITEMS, "riftwelling_altar", () -> BTABlocks.RIFTWELLING_ALTAR.get(), () -> new RiftwellingAltarBlockEntity(BlockPos.ZERO, BTABlocks.RIFTWELLING_ALTAR.get().defaultBlockState()), new Item.Properties().rarity(RARITY_DEEP_ABYSS));
	public static final RegistryObject<Item> DEEP_ABYSSALITH = registerBlockItem(DEEP_ABYSS_ITEMS, "deep_abyssalith", () -> BTABlocks.DEEP_ABYSSALITH.get(), new Item.Properties());
	public static final RegistryObject<Item> ABYSSALITH = registerBlockItem(DEEP_ABYSS_ITEMS, "abyssalith", () -> BTABlocks.ABYSSALITH.get(), new Item.Properties());
	public static final RegistryObject<Item> SMALL_BONE = registerBlockItem(DEEP_ABYSS_ITEMS, "small_bone", () -> BTABlocks.SMALL_BONE.get(), new Item.Properties());
	public static final RegistryObject<Item> JAW_BONE = registerBlockItem(DEEP_ABYSS_ITEMS, "jaw_bone", () -> BTABlocks.JAW_BONE.get(), new Item.Properties());
	public static final RegistryObject<Item> RIB = registerBlockItem(DEEP_ABYSS_ITEMS, "rib", () -> BTABlocks.RIB.get(), new Item.Properties());
	public static final RegistryObject<Item> FISH_BONE = registerBlockItem(DEEP_ABYSS_ITEMS, "fish_bone", () -> BTABlocks.FISH_BONE.get(), new Item.Properties());
	public static final RegistryObject<Item> FANG_SKULL = registerBlockItem(DEEP_ABYSS_ITEMS, "fang_skull", () -> BTABlocks.FANG_SKULL.get(), new Item.Properties());
	public static final RegistryObject<Item> LARGE_SKULL = registerBlockItem(DEEP_ABYSS_ITEMS, "large_skull", () -> BTABlocks.LARGE_SKULL.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINE_BONE_TIP = registerBlockItem(DEEP_ABYSS_ITEMS, "spine_bone_tip", () -> BTABlocks.SPINE_BONE_TIP.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINE_BONE_MIDDLE = registerBlockItem(DEEP_ABYSS_ITEMS, "spine_bone_middle", () -> BTABlocks.SPINE_BONE_MIDDLE.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINE_BONE_BASE = registerBlockItem(DEEP_ABYSS_ITEMS, "spine_bone_base", () -> BTABlocks.SPINE_BONE_BASE.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_PILES = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_piles", () -> BTABlocks.BONE_PILES.get(), new Item.Properties());
	public static final RegistryObject<Item> SITTING_SKELETON = registerBlockItem(DEEP_ABYSS_ITEMS, "sitting_skeleton", () -> BTABlocks.SITTING_SKELETON.get(), new Item.Properties());
	public static final RegistryObject<Item> FALLEN_SKELETON = registerBlockItem(DEEP_ABYSS_ITEMS, "fallen_skeleton", () -> BTABlocks.FALLEN_SKELETON.get(), new Item.Properties());
	public static final RegistryObject<Item> BLANK_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "blank_rune_stone", () -> BTABlocks.BLANK_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> SOUL_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "soul_rune_stone", () -> BTABlocks.SOUL_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> WATER_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "water_rune_stone", () -> BTABlocks.WATER_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> WAVE_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "wave_rune_stone", () -> BTABlocks.WAVE_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> CONDUIT_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "conduit_rune_stone", () -> BTABlocks.CONDUIT_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> GUARDIAN_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "guardian_rune_stone", () -> BTABlocks.GUARDIAN_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> PEACE_RUNE_STONE = registerBlockItem(DEEP_ABYSS_ITEMS, "peace_rune_stone", () -> BTABlocks.PEACE_RUNE_STONE.get(), new Item.Properties());
	public static final RegistryObject<Item> ORIVINE = registerBlockItem(DEEP_ABYSS_ITEMS, "orivine", () -> BTABlocks.ORIVINE.get(), new Item.Properties());
	public static final RegistryObject<Item> ORIVINE_SLAB = registerBlockItem(DEEP_ABYSS_ITEMS, "orivine_slab", () -> BTABlocks.ORIVINE_SLAB.get(), new Item.Properties());
	public static final RegistryObject<Item> ORIVINE_STAIRS = registerBlockItem(DEEP_ABYSS_ITEMS, "orivine_stairs", () -> BTABlocks.ORIVINE_STAIRS.get(), new Item.Properties());
	public static final RegistryObject<Item> ENERGIZED_ORIVINE = registerBlockItem(DEEP_ABYSS_ITEMS, "energized_orivine", () -> BTABlocks.ENERGIZED_ORIVINE.get(), new Item.Properties());
	public static final RegistryObject<Item> ENERGIZED_ORIVINE_SLAB = registerBlockItem(DEEP_ABYSS_ITEMS, "energized_orivine_slab", () -> BTABlocks.ENERGIZED_ORIVINE_SLAB.get(), new Item.Properties());
	public static final RegistryObject<Item> ENERGIZED_ORIVINE_STAIRS = registerBlockItem(DEEP_ABYSS_ITEMS, "energized_orivine_stairs", () -> BTABlocks.ENERGIZED_ORIVINE_STAIRS.get(), new Item.Properties());
	public static final RegistryObject<Item> ORIVINE_PILLAR = registerBlockItem(DEEP_ABYSS_ITEMS, "orivine_pillar", () -> BTABlocks.ORIVINE_PILLAR.get(), new Item.Properties());
	public static final RegistryObject<Item> ROT_SOIL = registerBlockItem(DEEP_ABYSS_ITEMS, "rot_soil", () -> BTABlocks.ROT_SOIL.get(), new Item.Properties());
	public static final RegistryObject<Item> COMPACT_ROT_SOIL = registerBlockItem(DEEP_ABYSS_ITEMS, "compact_rot_soil", () -> BTABlocks.COMPACT_ROT_SOIL.get(), new Item.Properties());
	public static final RegistryObject<Item> CHISELED_BONE_BLOCK = registerBlockItem(DEEP_ABYSS_ITEMS, "chiseled_bone_block", () -> BTABlocks.CHISELED_BONE_BLOCK.get(), new Item.Properties());
	public static final RegistryObject<Item> CRACKED_BONE_BLOCK = registerBlockItem(DEEP_ABYSS_ITEMS, "cracked_bone_block", () -> BTABlocks.CRACKED_BONE_BLOCK.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_BRICKS = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_bricks", () -> BTABlocks.BONE_BRICKS.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_PILLAR = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_pillar", () -> BTABlocks.BONE_PILLAR.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_FENCE = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_fence", () -> BTABlocks.BONE_FENCE.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_FENCE_GATE = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_fence_gate", () -> BTABlocks.BONE_FENCE_GATE.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_SLAB = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_slab", () -> BTABlocks.BONE_SLAB.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_STAIRS = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_stairs", () -> BTABlocks.BONE_STAIRS.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_LADDER = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_ladder", () -> BTABlocks.BONE_LADDER.get(), new Item.Properties());
	public static final RegistryObject<Item> BONE_TORCH = DEEP_ABYSS_ITEMS.register("bone_torch", () -> new StandingAndWallBlockItem(BTABlocks.BONE_TORCH.get(), BTABlocks.BONE_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item> BONE_LEVER = registerBlockItem(DEEP_ABYSS_ITEMS, "bone_lever", () -> BTABlocks.BONE_LEVER.get(), new Item.Properties());
	public static final RegistryObject<Item> CHAIN_TRAP = registerCustomRendererBlockItem(DEEP_ABYSS_ITEMS, "chain_trap", () -> BTABlocks.CHAIN_TRAP.get(), () -> new ChainTrapBlockEntity(BlockPos.ZERO, BTABlocks.CHAIN_TRAP.get().defaultBlockState()), new Item.Properties());
 	public static final RegistryObject<Item> TOOTHVINE = registerBlockItem(DEEP_ABYSS_ITEMS, "toothvine", () -> BTABlocks.TOOTHVINE.get(), new Item.Properties());
	public static final RegistryObject<Item> BIOCRAFTER = registerCustomRendererBlockItem(DEEP_ABYSS_ITEMS, "biocrafter", () -> BTABlocks.BIOCRAFTER.get(), () -> new BiocrafterBlockEntity(BlockPos.ZERO, BTABlocks.BIOCRAFTER.get().defaultBlockState()), new Item.Properties());
	public static final RegistryObject<Item> OYSTER_CORAL = registerBlockItem(DEEP_ABYSS_ITEMS, "oyster_coral", () -> BTABlocks.OYSTER_CORAL.get(), new Item.Properties());
	public static final RegistryObject<Item> OSTEO_CORAL = registerBlockItem(DEEP_ABYSS_ITEMS, "osteo_coral", () -> BTABlocks.OSTEO_CORAL.get(), new Item.Properties());
	public static final RegistryObject<Item> SPINYWEED = registerBlockItem(DEEP_ABYSS_ITEMS, "spinyweed", () -> BTABlocks.SPINYWEED.get(), new Item.Properties());
	public static final RegistryObject<Item> DEEPWEED = registerBlockItem(DEEP_ABYSS_ITEMS, "deepweed", () -> BTABlocks.DEEPWEED.get(), new Item.Properties());
	public static final RegistryObject<Item> GLARING_BARNACLE = registerCustomRendererBlockItem(DEEP_ABYSS_ITEMS, "glaring_barnacle", () -> BTABlocks.GLARING_BARNACLE.get(), () -> new AnimatableBlockEntity(BlockPos.ZERO, BTABlocks.GLARING_BARNACLE.get().defaultBlockState()), new Item.Properties());

	public static final RegistryObject<Item> GUTS_CORAL = registerBlockItem(MIRRORED_CITY_ITEMS, "guts_coral", () -> BTABlocks.GUTS_CORAL.get(), new Item.Properties());
	public static final RegistryObject<Item> RAFFLESIA_ANEMONE = registerBlockItem(MIRRORED_CITY_ITEMS, "rafflesia_anemone", () -> BTABlocks.RAFFLESIA_ANEMONE.get(), new Item.Properties());
	
	public static final RegistryObject<Item> MOONSTONE = registerBlockItem(MOON_ITEMS, "moonstone", () -> BTABlocks.MOONSTONE.get(), new Item.Properties());
	
	public static final RegistryObject<Item> MOLTEN_STONE = registerBlockItem(PURGATORY_ITEMS, "molten_stone", () -> BTABlocks.MOLTEN_STONE.get(), new Item.Properties());
	
	//weapons
	public static final RegistryObject<Item> SKELETAL_GUNBLADE = DEEP_ABYSS_ITEMS.register("skeletal_gunblade", () -> new SkeletalGunbladeItem(new Item.Properties().durability(1500).rarity(RARITY_DEEP_ABYSS)));
	public static final RegistryObject<Item> TOOTH_SHOTGUN = DEEP_ABYSS_ITEMS.register("tooth_shotgun", () -> new ToothShotgunItem(new Item.Properties().durability(1100).rarity(RARITY_DEEP_ABYSS)));

	//tools
	public static final RegistryObject<Item> FLASHLIGHT = DEEP_ABYSS_ITEMS.register("flashlight", () -> new FlashlightItem());
	
	public static RegistryObject<Item> registerSpawnEgg(DeferredRegister<Item> registry, String name, Supplier<? extends EntityType<? extends Mob>> type, int color1, int color2)
	{
		return registry.register(name, () -> new ForgeSpawnEggItem(type, color1, color2, new Item.Properties()));
	}
	
	public static RegistryObject<Item> registerCustomRendererBlockItem(DeferredRegister<Item> registry, String name, Supplier<Block> block, Supplier<BlockEntity> blockEntity, Item.Properties properties)
	{
		return registry.register(name, () -> new CustomRendererBlockItem(block.get(), properties, blockEntity));
	}
	
	public static RegistryObject<Item> registerBlockItem(DeferredRegister<Item> registry, String name, Supplier<Block> block, Item.Properties properties)
	{
		return registry.register(name, () -> new BlockItem(block.get(), properties));
	}
}