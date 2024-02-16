package com.min01.beyondtheabyss.block;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.BlockAltarOfDeep;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityAltarOfDeep;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTABlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BeyondtheAbyss.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Block> ALTAR_OF_DEEP = BLOCKS.register("altar_of_deep", () -> new BlockAltarOfDeep());
    
    public static final RegistryObject<BlockEntityType<BlockEntityAltarOfDeep>> ALTAR_OF_DEEP_BLOCK_ENTITY = BLOCK_ENTITIES.register("altar_of_deep", () -> BlockEntityType.Builder.of(BlockEntityAltarOfDeep::new, BTABlocks.ALTAR_OF_DEEP.get()).build(null));
}
