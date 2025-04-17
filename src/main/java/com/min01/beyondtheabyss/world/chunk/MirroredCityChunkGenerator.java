package com.min01.beyondtheabyss.world.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class MirroredCityChunkGenerator extends NoiseBasedChunkGenerator
{
    public static final Codec<MirroredCityChunkGenerator> CODEC = RecordCodecBuilder.create((p_255585_) ->
    {
    	return p_255585_.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((p_255584_) -> 
    	{
    		return p_255584_.biomeSource;
    	}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((p_224278_) -> 
    	{
    		return p_224278_.settings;
    	})).apply(p_255585_, p_255585_.stable(MirroredCityChunkGenerator::new));
    });
	
	public MirroredCityChunkGenerator(BiomeSource p_224208_, Holder<NoiseGeneratorSettings> p_224209_) 
	{
		super(p_224208_, p_224209_);
	}
}
