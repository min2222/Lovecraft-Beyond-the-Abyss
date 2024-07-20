package com.min01.beyondtheabyss.block;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.RiftwellingAltarBlock;
import com.min01.beyondtheabyss.block.deepabyss.SmallBoneBlock;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityRiftwellingAltar;

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

    public static final RegistryObject<Block> SMALL_BONE_BLOCK = BLOCKS.register("small_bone_block", () -> new SmallBoneBlock());
    
    public static final RegistryObject<BlockEntityType<BlockEntityRiftwellingAltar>> RIFTWELLING_ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("riftwelling_altar", () -> BlockEntityType.Builder.of(BlockEntityRiftwellingAltar::new, BTABlocks.RIFTWELLING_ALTAR.get()).build(null));
}
