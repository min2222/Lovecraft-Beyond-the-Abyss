package com.min01.beyondtheabyss.block;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.BiocrafterBlock;
import com.min01.beyondtheabyss.block.deepabyss.BoneFenceBlock;
import com.min01.beyondtheabyss.block.deepabyss.BoneLeverBlock;
import com.min01.beyondtheabyss.block.deepabyss.BonePilesBlock;
import com.min01.beyondtheabyss.block.deepabyss.BoneTorchBlock;
import com.min01.beyondtheabyss.block.deepabyss.BoneWallTorchBlock;
import com.min01.beyondtheabyss.block.deepabyss.ChainTrapBlock;
import com.min01.beyondtheabyss.block.deepabyss.FallenSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.FangSkullBlock;
import com.min01.beyondtheabyss.block.deepabyss.FishBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.GhoulBloomBlock;
import com.min01.beyondtheabyss.block.deepabyss.GlaringBarnacleBlock;
import com.min01.beyondtheabyss.block.deepabyss.JawBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.LargeSkullBlock;
import com.min01.beyondtheabyss.block.deepabyss.NoBonemealSeagrassBlock;
import com.min01.beyondtheabyss.block.deepabyss.NoDeadCoralPlantBlock;
import com.min01.beyondtheabyss.block.deepabyss.RibBlock;
import com.min01.beyondtheabyss.block.deepabyss.RiftwellingAltarBlock;
import com.min01.beyondtheabyss.block.deepabyss.RotSoilBlock;
import com.min01.beyondtheabyss.block.deepabyss.SittingSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.SmallBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneBaseBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneMiddleBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneTipBlock;
import com.min01.beyondtheabyss.block.deepabyss.ToothvineBlock;
import com.min01.beyondtheabyss.block.deepabyss.ToothvinePlantBlock;
import com.min01.beyondtheabyss.block.mirroredcity.RafflesiaAnemoneBlock;
import com.min01.beyondtheabyss.blockentity.AnimatableBlockEntity;
import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.BiocrafterBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.ChainTrapBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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
    
    public static final RegistryObject<Block> BLANK_RUNE_STONE = BLOCKS.register("blank_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> SOUL_RUNE_STONE = BLOCKS.register("soul_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> WATER_RUNE_STONE = BLOCKS.register("water_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> WAVE_RUNE_STONE = BLOCKS.register("wave_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> CONDUIT_RUNE_STONE = BLOCKS.register("conduit_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> GUARDIAN_RUNE_STONE = BLOCKS.register("guardian_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> PEACE_RUNE_STONE = BLOCKS.register("peace_rune_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    
    public static final RegistryObject<Block> ORIVINE = BLOCKS.register("orivine", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ORIVINE_SLAB = BLOCKS.register("orivine_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ORIVINE_STAIRS = BLOCKS.register("orivine_stairs", () -> new StairBlock(() -> ORIVINE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ENERGIZED_ORIVINE = BLOCKS.register("energized_orivine", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ENERGIZED_ORIVINE_SLAB = BLOCKS.register("energized_orivine_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ENERGIZED_ORIVINE_STAIRS = BLOCKS.register("energized_orivine_stairs", () -> new StairBlock(() -> ORIVINE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    public static final RegistryObject<Block> ORIVINE_PILLAR = BLOCKS.register("orivine_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE)));
    		
    public static final RegistryObject<Block> ROT_SOIL = BLOCKS.register("rot_soil", () -> new RotSoilBlock());
    public static final RegistryObject<Block> COMPACT_ROT_SOIL = BLOCKS.register("compact_rot_soil", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> GHOUL_BLOOM = BLOCKS.register("ghoul_bloom", () -> new GhoulBloomBlock());
    public static final RegistryObject<Block> TOOTHVINE = BLOCKS.register("toothvine", () -> new ToothvineBlock());
    public static final RegistryObject<Block> TOOTHVINE_PLANT = BLOCKS.register("toothvine_plant", () -> new ToothvinePlantBlock());
    public static final RegistryObject<Block> OYSTER_CORAL = BLOCKS.register("oyster_coral", () -> new NoDeadCoralPlantBlock(BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL).mapColor(DyeColor.PURPLE)));
    public static final RegistryObject<Block> OSTEO_CORAL = BLOCKS.register("osteo_coral", () -> new NoDeadCoralPlantBlock(BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL).mapColor(DyeColor.WHITE)));
    public static final RegistryObject<Block> SPINYWEED = BLOCKS.register("spinyweed", () -> new NoBonemealSeagrassBlock(BlockBehaviour.Properties.copy(Blocks.SEAGRASS), false));
    public static final RegistryObject<Block> DEEPWEED = BLOCKS.register("deepweed", () -> new NoBonemealSeagrassBlock(BlockBehaviour.Properties.copy(Blocks.SEAGRASS), false));
    public static final RegistryObject<Block> GLARING_BARNACLE = BLOCKS.register("glaring_barnacle", () -> new GlaringBarnacleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.SLIME_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> CHISELED_BONE_BLOCK = BLOCKS.register("chiseled_bone_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_BLOCK = BLOCKS.register("cracked_bone_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BRICKS = BLOCKS.register("bone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_PILLAR = BLOCKS.register("bone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));;
    
    public static final RegistryObject<Block> BONE_FENCE = BLOCKS.register("bone_fence", () -> new BoneFenceBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_FENCE_GATE = BLOCKS.register("bone_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(2.0F, 3.0F).sound(SoundType.BONE_BLOCK), WoodType.OAK));
    public static final RegistryObject<Block> BONE_SLAB = BLOCKS.register("bone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_STAIRS = BLOCKS.register("bone_stairs", () -> new StairBlock(() -> Blocks.BONE_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)));
    
    public static final RegistryObject<Block> BONE_LADDER = BLOCKS.register("bone_ladder", () ->  new LadderBlock(BlockBehaviour.Properties.of().strength(0.4F).sound(SoundType.BONE_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> BONE_TORCH = BLOCKS.register("bone_torch", () -> new BoneTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(t -> 
    {
        return 14;
    }).sound(SoundType.BONE_BLOCK).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> BONE_WALL_TORCH = BLOCKS.register("bone_wall_torch", () -> new BoneWallTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(t ->
    {
        return 14;
    }).sound(SoundType.BONE_BLOCK).lootFrom(BONE_TORCH).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> BONE_LEVER = BLOCKS.register("bone_lever", () -> new BoneLeverBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).sound(SoundType.BONE_BLOCK)));
    public static final RegistryObject<Block> CHAIN_TRAP = BLOCKS.register("chain_trap", () -> new ChainTrapBlock());
    public static final RegistryObject<Block> BIOCRAFTER = BLOCKS.register("biocrafter", () -> new BiocrafterBlock());

    public static final RegistryObject<Block> GUTS_CORAL = BLOCKS.register("guts_coral", () -> new NoDeadCoralPlantBlock(BlockBehaviour.Properties.copy(Blocks.BRAIN_CORAL).mapColor(DyeColor.RED)));
    public static final RegistryObject<Block> RAFFLESIA_ANEMONE = BLOCKS.register("rafflesia_anemone", () -> new RafflesiaAnemoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).sound(SoundType.SLIME_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY)));
    
    public static final RegistryObject<Block> MOONSTONE = BLOCKS.register("moonstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)));
    
    public static final RegistryObject<Block> MOLTEN_STONE = BLOCKS.register("molten_stone", () -> new MagmaBlock(BlockBehaviour.Properties.copy(Blocks.MAGMA_BLOCK)));
    
    public static final RegistryObject<BlockEntityType<RiftwellingAltarBlockEntity>> RIFTWELLING_ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("riftwelling_altar", () -> BlockEntityType.Builder.of(RiftwellingAltarBlockEntity::new, RIFTWELLING_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<NoRotationLimitBlockEntity>> NO_ROTATION_LIMIT_BLOCK_ENTITY = BLOCK_ENTITIES.register("no_rotation_limit", () -> BlockEntityType.Builder.of(NoRotationLimitBlockEntity::new, 
    		FANG_SKULL.get(),
    		LARGE_SKULL.get(),
    		BONE_PILES.get(),
    		SITTING_SKELETON.get(),
       		FALLEN_SKELETON.get(),
    		BONE_TORCH.get(),
    		BONE_WALL_TORCH.get(),
    		BONE_WALL_TORCH.get(),
    		BONE_LEVER.get()).build(null));
    public static final RegistryObject<BlockEntityType<AnimatableBlockEntity>> ANIMATABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("animatable", () -> BlockEntityType.Builder.of(AnimatableBlockEntity::new, 
    		GLARING_BARNACLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ChainTrapBlockEntity>> CHAIN_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES.register("chain_trap", () -> BlockEntityType.Builder.of(ChainTrapBlockEntity::new, CHAIN_TRAP.get()).build(null));
    public static final RegistryObject<BlockEntityType<BiocrafterBlockEntity>> BIOCRAFTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("biocrafter", () -> BlockEntityType.Builder.of(BiocrafterBlockEntity::new, BIOCRAFTER.get()).build(null));
}
