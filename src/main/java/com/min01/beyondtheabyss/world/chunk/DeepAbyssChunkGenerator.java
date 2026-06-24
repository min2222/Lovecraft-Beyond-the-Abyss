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
    public static final Codec<DeepAbyssChunkGenerator> CODEC = RecordCodecBuilder.create(builder ->
    {
    	return builder.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(t -> 
    	{
    		return t.biomeSource;
    	}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(t -> 
    	{
    		return t.generatorSettings();
    	})).apply(builder, builder.stable(DeepAbyssChunkGenerator::new));
    });

	public DeepAbyssChunkGenerator(BiomeSource pBiomeSource, Holder<NoiseGeneratorSettings> holder) 
	{
		super(pBiomeSource, holder);
		Aquifer.FluidStatus fluidStatus = new Aquifer.FluidStatus(holder.value().seaLevel(), holder.value().defaultFluid());
		this.globalFluidPicker = Suppliers.memoize(() -> 
		{
			return (pX, pY, pZ) -> 
			{
				return fluidStatus;
			};
		});
	}
}
