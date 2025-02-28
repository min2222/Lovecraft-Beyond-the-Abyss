package com.min01.beyondtheabyss.world.chunk;

import com.min01.beyondtheabyss.misc.BTATags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
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
		Aquifer.FluidStatus fluidStatus = new Aquifer.FluidStatus(p_224209_.value().seaLevel(), p_224209_.value().defaultFluid());
		this.globalFluidPicker = (p_224274_, p_224275_, p_224276_) ->
		{
			return fluidStatus;
		};
        this.erosionNoise = new SimplexNoise(RandomSource.create());
	}
    
    @Override
    public void buildSurface(WorldGenRegion p_224232_, StructureManager p_224233_, RandomState p_224234_, ChunkAccess p_224235_)
    {
    	super.buildSurface(p_224232_, p_224233_, p_224234_, p_224235_);
        this.modifyTerrain(p_224235_);
    }
    
    //ChatGPT ahh;
    private void modifyTerrain(ChunkAccess chunk) 
    {
        ChunkPos chunkPos = chunk.getPos();
        
        int baseMinHeight = 30;
        int maxHeight = 150;
        
        MutableBlockPos mutablePos = new MutableBlockPos();
        for(int x = 0; x < 16; x++) 
        {
            for(int z = 0; z < 16; z++)
            {
                int worldX = chunkPos.getBlockX(x);
                int worldZ = chunkPos.getBlockZ(z);

                int minHeight = baseMinHeight;

                int currentHeight = chunk.getHeight(Types.OCEAN_FLOOR, worldX, worldZ);
                
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
                        mutablePos.set(worldX, y, worldZ);
                        if(chunk.getBlockState(mutablePos).is(BTATags.BTABlocks.DEATH_VALLEY_REPLACEABLES)) 
                        {
                            chunk.setBlockState(mutablePos, Blocks.WATER.defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }
}
