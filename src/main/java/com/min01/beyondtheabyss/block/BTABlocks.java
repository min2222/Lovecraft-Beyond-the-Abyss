package com.min01.beyondtheabyss.block;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.BonePilesBlock;
import com.min01.beyondtheabyss.block.deepabyss.ColoredMetalLanternBlock;
import com.min01.beyondtheabyss.block.deepabyss.FallenSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.FangSkullBlock;
import com.min01.beyondtheabyss.block.deepabyss.FishBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.GhoulBloomBlock;
import com.min01.beyondtheabyss.block.deepabyss.JawBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.LargeSkullBlock;
import com.min01.beyondtheabyss.block.deepabyss.MetalCrateBlock;
import com.min01.beyondtheabyss.block.deepabyss.MetalWindowBlock;
import com.min01.beyondtheabyss.block.deepabyss.RibBlock;
import com.min01.beyondtheabyss.block.deepabyss.RiftwellingAltarBlock;
import com.min01.beyondtheabyss.block.deepabyss.RotSoilBlock;
import com.min01.beyondtheabyss.block.deepabyss.SittingSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.SmallBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneBaseBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneMiddleBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneTipBlock;
import com.min01.beyondtheabyss.block.deepabyss.ToothvineBlock;
import com.min01.beyondtheabyss.block.deepabyss.WhalefallBlock;
import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.MetalCrateBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;

import net.minecraft.world.level.block.BaseCoralFanBlock;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.CoralFanBlock;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTABlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BeyondtheAbyss.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Block> RIFTWELLING_ALTAR = BLOCKS.register("riftwelling_altar", () -> new RiftwellingAltarBlock());
    public static final RegistryObject<Block> ABYSSALITH = BLOCKS.register("abyssalith", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> DEEP_ABYSSALITH = BLOCKS.register("deep_abyssalith", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));

    public static final RegistryObject<Block> SMALL_BONE = BLOCKS.register("small_bone", () -> new SmallBoneBlock());
    public static final RegistryObject<Block> JAW_BONE = BLOCKS.register("jaw_bone", () -> new JawBoneBlock());
    public static final RegistryObject<Block> RIB = BLOCKS.register("rib", () -> new RibBlock());
    public static final RegistryObject<Block> FISH_BONE = BLOCKS.register("fish_bone", () -> new FishBoneBlock());
    public static final RegistryObject<Block> FANG_SKULL = BLOCKS.register("fang_skull", () -> new FangSkullBlock());
    public static final RegistryObject<Block> LARGE_SKULL = BLOCKS.register("large_skull", () -> new LargeSkullBlock());
    public static final RegistryObject<Block> SPINE_BONE_TIP = BLOCKS.register("spine_bone_tip", () -> new SpineBoneTipBlock());
    public static final RegistryObject<Block> SPINE_BONE_MIDDLE = BLOCKS.register("spine_bone_middle", () -> new SpineBoneMiddleBlock());
    public static final RegistryObject<Block> SPINE_BONE_BASE = BLOCKS.register("spine_bone_base", () -> new SpineBoneBaseBlock());
    public static final RegistryObject<Block> BONE_PILES = BLOCKS.register("bone_piles", () -> new BonePilesBlock());
    public static final RegistryObject<Block> SITTING_SKELETON = BLOCKS.register("sitting_skeleton", () -> new SittingSkeletonBlock());
    public static final RegistryObject<Block> FALLEN_SKELETON = BLOCKS.register("fallen_skeleton", () -> new FallenSkeletonBlock());
    
    public static final RegistryObject<Block> METAL_BRICK = BLOCKS.register("metal_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METAL_TILE = BLOCKS.register("metal_tile", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METAL_PLATE = BLOCKS.register("metal_plate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METAL_BRICK_STAIRS = BLOCKS.register("metal_brick_stairs", () -> new StairBlock(() -> METAL_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.copy(METAL_BRICK.get())));
    public static final RegistryObject<Block> METAL_TILE_STAIRS = BLOCKS.register("metal_tile_stairs", () -> new StairBlock(() -> METAL_TILE.get().defaultBlockState(), BlockBehaviour.Properties.copy(METAL_TILE.get())));
    public static final RegistryObject<Block> METAL_PLATE_STAIRS = BLOCKS.register("metal_plate_stairs", () -> new StairBlock(() -> METAL_PLATE.get().defaultBlockState(), BlockBehaviour.Properties.copy(METAL_PLATE.get())));
    public static final RegistryObject<Block> METAL_BRICK_SLAB = BLOCKS.register("metal_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METAL_TILE_SLAB = BLOCKS.register("metal_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METAL_PLATE_SLAB = BLOCKS.register("metal_plate_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLUE_METAL_LANTERN = BLOCKS.register("blue_metal_lantern", () -> new ColoredMetalLanternBlock());
    public static final RegistryObject<Block> GREEN_METAL_LANTERN = BLOCKS.register("green_metal_lantern", () -> new ColoredMetalLanternBlock());
    public static final RegistryObject<Block> PINK_METAL_LANTERN = BLOCKS.register("pink_metal_lantern", () -> new ColoredMetalLanternBlock());
    public static final RegistryObject<Block> RED_METAL_LANTERN = BLOCKS.register("red_metal_lantern", () -> new ColoredMetalLanternBlock());
    public static final RegistryObject<Block> METAL_WINDOW = BLOCKS.register("metal_window", () -> new MetalWindowBlock());
    public static final RegistryObject<Block> BLUE_METAL_CRATE = BLOCKS.register("blue_metal_crate", () -> new MetalCrateBlock());
    public static final RegistryObject<Block> GREEN_METAL_CRATE = BLOCKS.register("green_metal_crate", () -> new MetalCrateBlock());
    public static final RegistryObject<Block> PINK_METAL_CRATE = BLOCKS.register("pink_metal_crate", () -> new MetalCrateBlock());
    public static final RegistryObject<Block> RED_METAL_CRATE = BLOCKS.register("red_metal_crate", () -> new MetalCrateBlock());
    
    public static final RegistryObject<Block> BLANK_RUNE_STONE = BLOCKS.register("blank_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> SOUL_RUNE_STONE = BLOCKS.register("soul_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> CROSS_RUNE_STONE = BLOCKS.register("cross_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> WORD_RUNE_STONE = BLOCKS.register("word_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> VISION_RUNE_STONE = BLOCKS.register("vision_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ENERGY_RUNE_STONE = BLOCKS.register("energy_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> SPIKE_RUNE_STONE = BLOCKS.register("spike_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    
    public static final RegistryObject<Block> DEAD_OSTEO_CORAL_BLOCK = BLOCKS.register("dead_osteo_coral_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> OSTEO_CORAL_BLOCK = BLOCKS.register("osteo_coral_block", () -> new CoralBlock(DEAD_OSTEO_CORAL_BLOCK.get(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> DEAD_OSTEO_CORAL_FAN = BLOCKS.register("dead_osteo_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak()));
    public static final RegistryObject<Block> OSTEO_CORAL_FAN = BLOCKS.register("osteo_coral_fan", () -> new CoralFanBlock(DEAD_OSTEO_CORAL_FAN.get(), BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS)));
    public static final RegistryObject<Block> DEAD_OSTEO_CORAL_WALL_FAN = BLOCKS.register("dead_osteo_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak().lootFrom(() -> DEAD_OSTEO_CORAL_FAN.get())));
    public static final RegistryObject<Block> OSTEO_CORAL_WALL_FAN = BLOCKS.register("osteo_coral_wall_fan", () -> new CoralWallFanBlock(DEAD_OSTEO_CORAL_WALL_FAN.get(), BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).lootFrom(() -> OSTEO_CORAL_FAN.get())));
    
    public static final RegistryObject<Block> WHALEFALL = BLOCKS.register("whalefall", () -> new WhalefallBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL)));
    public static final RegistryObject<Block> ROT_SOIL = BLOCKS.register("rot_soil", () -> new RotSoilBlock());
    public static final RegistryObject<Block> COMPACT_ROT_SOIL = BLOCKS.register("compact_rot_soil", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> GHOUL_BLOOM = BLOCKS.register("ghoul_bloom", () -> new GhoulBloomBlock());
    public static final RegistryObject<Block> TOOTHVINE = BLOCKS.register("toothvine", () -> new ToothvineBlock());

    public static final RegistryObject<Block> CHISELED_BONE_BLOCK = BLOCKS.register("chiseled_bone_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_BLOCK = BLOCKS.register("cracked_bone_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BRICK_BLOCK = BLOCKS.register("bone_brick_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_PILLAR_BLOCK = BLOCKS.register("bone_pillar_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    
    public static final RegistryObject<Block> BONE_STAIRS = BLOCKS.register("bone_stairs", () -> new StairBlock(() -> Blocks.BONE_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_SLAB = BLOCKS.register("bone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_FENCE = BLOCKS.register("bone_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_LADDER = BLOCKS.register("bone_ladder", () ->  new LadderBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.4F).sound(SoundType.BONE_BLOCK).noOcclusion()));
    
    public static final RegistryObject<BlockEntityType<RiftwellingAltarBlockEntity>> RIFTWELLING_ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("riftwelling_altar", () -> BlockEntityType.Builder.of(RiftwellingAltarBlockEntity::new, BTABlocks.RIFTWELLING_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<NoRotationLimitBlockEntity>> NO_ROTATION_LIMIT_BLOCK_ENTITY = BLOCK_ENTITIES.register("no_rotation_limit", () -> BlockEntityType.Builder.of(NoRotationLimitBlockEntity::new, 
    		BTABlocks.FANG_SKULL.get(),
    		BTABlocks.LARGE_SKULL.get(),
    		BTABlocks.BONE_PILES.get(),
    		BTABlocks.SITTING_SKELETON.get(),
    		BTABlocks.FALLEN_SKELETON.get()).build(null));
    public static final RegistryObject<BlockEntityType<MetalCrateBlockEntity>> METAL_CRATE_BLOCK_ENTITY = BLOCK_ENTITIES.register("metal_crate", () -> BlockEntityType.Builder.of(MetalCrateBlockEntity::new, 
    		BTABlocks.BLUE_METAL_CRATE.get(),
    		BTABlocks.GREEN_METAL_CRATE.get(),
    		BTABlocks.PINK_METAL_CRATE.get(),
    		BTABlocks.RED_METAL_CRATE.get()).build(null));
}
