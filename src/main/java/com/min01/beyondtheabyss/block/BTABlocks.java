package com.min01.beyondtheabyss.block;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.BonePilesBlock;
import com.min01.beyondtheabyss.block.deepabyss.FallenSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.FangSkullBlock;
import com.min01.beyondtheabyss.block.deepabyss.FishBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.JawBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.LargeSkullBlock;
import com.min01.beyondtheabyss.block.deepabyss.RibBlock;
import com.min01.beyondtheabyss.block.deepabyss.RiftwellingAltarBlock;
import com.min01.beyondtheabyss.block.deepabyss.SittingSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.SmallBoneBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneBaseBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneMiddleBlock;
import com.min01.beyondtheabyss.block.deepabyss.SpineBoneTipBlock;
import com.min01.beyondtheabyss.blockentity.NoRotationLimitBlockEntity;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTABlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BeyondtheAbyss.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BeyondtheAbyss.MODID);

    public static final RegistryObject<Block> BTA_LIGHT = BLOCKS.register("bta_light", () -> new BTALightBlock(BlockBehaviour.Properties.of(Material.AIR).strength(-1.0F, 3600000.8F).noLootTable().noOcclusion().lightLevel(BTALightBlock.LIGHT_EMISSION)));
    
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
    
    public static final RegistryObject<BlockEntityType<RiftwellingAltarBlockEntity>> RIFTWELLING_ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("riftwelling_altar", () -> BlockEntityType.Builder.of(RiftwellingAltarBlockEntity::new, BTABlocks.RIFTWELLING_ALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<NoRotationLimitBlockEntity>> NO_ROTATION_LIMIT_BLOCK_ENTITY = BLOCK_ENTITIES.register("no_rotation_limit", () -> BlockEntityType.Builder.of(NoRotationLimitBlockEntity::new, 
    		BTABlocks.FANG_SKULL.get(),
    		BTABlocks.LARGE_SKULL.get(),
    		BTABlocks.BONE_PILES.get(),
    		BTABlocks.SITTING_SKELETON.get(),
    		BTABlocks.FALLEN_SKELETON.get()).build(null));
}
