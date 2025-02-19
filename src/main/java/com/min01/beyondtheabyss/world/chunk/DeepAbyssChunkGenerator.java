package com.min01.beyondtheabyss.world.chunk;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class DeepAbyssChunkGenerator extends NoiseBasedChunkGenerator
{
    private final SimplexNoise erosionNoise;
    
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
        this.erosionNoise = new SimplexNoise(RandomSource.create());
	}
	
    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) 
    {
        return super.fillFromNoise(executor, blender, randomState, structureManager, chunk).thenApply((generatedChunk) -> 
        {
            this.modifyTerrain(generatedChunk);
            return generatedChunk;
        });
    }
    
    //ChatGPT ahh;
    private void modifyTerrain(ChunkAccess chunk) 
    {
        ChunkPos chunkPos = chunk.getPos();
        for(int localX = 0; localX < 16; localX++) 
        {
            for(int localZ = 0; localZ < 16; localZ++) 
            {
            	BlockPos worldPos = chunkPos.getBlockAt(localX, 0, localZ);
            	int worldX = worldPos.getX();
            	int worldZ = worldPos.getZ();

                int minHeight = 30;
                int maxHeight = 150;
                
                int currentHeight = BTAUtil.getGroundPos(chunk, worldX, 150, worldZ, 0).getY();
                
                double distance = Math.sqrt(worldX * worldX + worldZ * worldZ);
                double canyonRadius = 150;
                double erosion = this.erosionNoise.getValue(worldX * 0.01, worldZ * 0.01) * 150;
                double heightVariation = Math.sin(distance / canyonRadius * Math.PI);
                double baseHeight = minHeight + erosion;
                
                double plainsNoise = this.erosionNoise.getValue(worldX * 0.005, worldZ * 0.005) * 5; 
                minHeight += plainsNoise;

                int modifiedHeight = (int) Math.round(baseHeight + heightVariation);

                modifiedHeight = Math.min(maxHeight, Math.max(minHeight, modifiedHeight));
                
                if(currentHeight > modifiedHeight)
                {
                    for(int y = modifiedHeight + 1; y < 150; y++) 
                    {
                        BlockPos pos = new BlockPos(worldX, y, worldZ);
                        //FIXME can't detect rot soil block, aka only detect abyssalith for some reason;
                        if(!chunk.getBlockState(pos).is(Blocks.WATER))
                        {
                            chunk.setBlockState(pos, Blocks.WATER.defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }
}
