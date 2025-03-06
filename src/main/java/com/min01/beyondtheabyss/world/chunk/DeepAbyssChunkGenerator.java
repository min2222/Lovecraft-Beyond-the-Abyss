package com.min01.beyondtheabyss.world.chunk;

import com.google.common.base.Suppliers;
import com.min01.beyondtheabyss.misc.BTATags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class DeepAbyssChunkGenerator extends NoiseBasedChunkGenerator
{
    private final SimplexNoise erosionNoise;
    
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
        this.erosionNoise = new SimplexNoise(RandomSource.create());
		Aquifer.FluidStatus fluidStatus = new Aquifer.FluidStatus(p_224209_.value().seaLevel(), p_224209_.value().defaultFluid());
		this.globalFluidPicker = Suppliers.memoize(() -> 
		{
			return (p_224274_, p_224275_, p_224276_) -> 
			{
				return fluidStatus;
			};
		});
	}
	
    @Override
	public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState random, ChunkAccess chunkAccess) 
	{
	    super.buildSurface(region, structureManager, random, chunkAccess);
        this.makeDeathValley(chunkAccess);
        this.makeSpireHollow(chunkAccess);
    }
    
    //ChatGPT ahh;
    private void makeDeathValley(ChunkAccess chunk) 
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
    
    private void makeSpireHollow(ChunkAccess chunk) 
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
                double baseHeight = minHeight;

                double plainsNoise = this.erosionNoise.getValue(worldX * 0.005, worldZ * 0.005) * 5; 
                minHeight += plainsNoise;

                int modifiedHeight = (int) Math.round(baseHeight);
                modifiedHeight = Math.min(maxHeight, Math.max(minHeight, modifiedHeight));

                if(currentHeight > modifiedHeight) 
                {
                    for(int y = modifiedHeight + 1; y < 150; y++) 
                    {
                        mutablePos.set(worldX, y, worldZ);
                        if(chunk.getBlockState(mutablePos).is(BTATags.BTABlocks.SPIRE_HOLLOW_REPLACEABLES)) 
                        {
                            chunk.setBlockState(mutablePos, Blocks.WATER.defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }
}
