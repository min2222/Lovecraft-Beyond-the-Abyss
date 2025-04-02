package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.chunk.DeepAbyssChunkGenerator;
import com.min01.beyondtheabyss.world.chunk.MirroredCityChunkGenerator;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAChunkGenerators 
{
    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registry.CHUNK_GENERATOR_REGISTRY, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Codec<DeepAbyssChunkGenerator>> DEEP_ABYSS = CHUNK_GENERATORS.register("deep_abyss", () -> DeepAbyssChunkGenerator.CODEC);
    //public static final RegistryObject<Codec<EvergreenChunkGenerator>> EVERGREEN = CHUNK_GENERATORS.register("evergreen", () -> EvergreenChunkGenerator.CODEC);
    public static final RegistryObject<Codec<MirroredCityChunkGenerator>> MIRRORED_CITY = CHUNK_GENERATORS.register("mirrored_city", () -> MirroredCityChunkGenerator.CODEC);
}
