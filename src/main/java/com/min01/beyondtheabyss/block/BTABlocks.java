package com.min01.beyondtheabyss.block;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.BlockAbyssalAltar;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityAbyssalAltar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTABlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BeyondtheAbyss.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Block> ABYSSAL_ALTAR = BLOCKS.register("abyssal_altar", () -> new BlockAbyssalAltar());
    
    public static final RegistryObject<BlockEntityType<BlockEntityAbyssalAltar>> ABYSSAL_ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("abyssal_altar", () -> BlockEntityType.Builder.of(BlockEntityAbyssalAltar::new, BTABlocks.ABYSSAL_ALTAR.get()).build(null));
    
    public static boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_)
    {
    	return false;
    }
}
