package com.min01.beyondtheabyss.world.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class DeepAbyssChunkGenerator extends NoiseBasedChunkGenerator
{
	public static final Codec<DeepAbyssChunkGenerator> CODEC = RecordCodecBuilder.create((p_224323_) ->
	{
		return commonCodec(p_224323_).and(p_224323_.group(RegistryOps.retrieveRegistry(Registry.NOISE_REGISTRY).forGetter((p_188716_) ->
		{
			return p_188716_.noises;
		}), BiomeSource.CODEC.fieldOf("biome_source").forGetter((p_188711_) -> 
		{
			return p_188711_.biomeSource;
		}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((p_224278_) ->
		{
			return p_224278_.settings;
		}))).apply(p_224323_, p_224323_.stable(DeepAbyssChunkGenerator::new));
	});
	
	public DeepAbyssChunkGenerator(Registry<StructureSet> p_224206_, Registry<NoiseParameters> p_224207_, BiomeSource p_224208_, Holder<NoiseGeneratorSettings> p_224209_) 
	{
		super(p_224206_, p_224207_, p_224208_, p_224209_);
		Aquifer.FluidStatus fluidStatus = new Aquifer.FluidStatus(p_224209_.value().seaLevel(), p_224209_.value().defaultFluid());
		this.globalFluidPicker = (p_224274_, p_224275_, p_224276_) ->
		{
			return fluidStatus;
		};
	}
    
	@Override
	public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState random, ChunkAccess chunkAccess) 
	{
	    super.buildSurface(region, structureManager, random, chunkAccess);
    }
}
