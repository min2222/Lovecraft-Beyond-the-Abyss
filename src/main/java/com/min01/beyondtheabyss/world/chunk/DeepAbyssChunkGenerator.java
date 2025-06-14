package com.min01.beyondtheabyss.world.chunk;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class DeepAbyssChunkGenerator extends NoiseBasedChunkGenerator
{
    public static final Codec<DeepAbyssChunkGenerator> CODEC = RecordCodecBuilder.create((p_255585_) ->
    {
    	return p_255585_.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((p_255584_) -> 
    	{
    		return p_255584_.biomeSource;
    	}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((p_224278_) -> 
    	{
    		return p_224278_.settings;
    	})).apply(p_255585_, p_255585_.stable(DeepAbyssChunkGenerator::new));
    });

	public DeepAbyssChunkGenerator(BiomeSource p_224208_, Holder<NoiseGeneratorSettings> p_224209_) 
	{
		super(p_224208_, p_224209_);
		Aquifer.FluidStatus fluidStatus = new Aquifer.FluidStatus(p_224209_.value().seaLevel(), p_224209_.value().defaultFluid());
		this.globalFluidPicker = Suppliers.memoize(() -> 
		{
			return (p_224274_, p_224275_, p_224276_) -> 
			{
				return fluidStatus;
			};
		});
	}
}
