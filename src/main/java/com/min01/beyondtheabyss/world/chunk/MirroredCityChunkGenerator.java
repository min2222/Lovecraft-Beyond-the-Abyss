package com.min01.beyondtheabyss.world.chunk;

import java.io.DataInputStream;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.min01.beyondtheabyss.world.BTAWorlds;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MirroredCityChunkGenerator extends NoiseBasedChunkGenerator
{
    public static final Codec<MirroredCityChunkGenerator> CODEC = RecordCodecBuilder.create(builder ->
    {
    	return builder.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(t -> 
    	{
    		return t.biomeSource;
    	}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(t -> 
    	{
    		return t.settings;
    	})).apply(builder, builder.stable(MirroredCityChunkGenerator::new));
    });
	
	public MirroredCityChunkGenerator(BiomeSource pBiomeSource, Holder<NoiseGeneratorSettings> holder) 
	{
		super(pBiomeSource, holder);
	}
	
	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor pExecutor, Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk) 
	{
		return CompletableFuture.completedFuture(pChunk);
	}

	@Override
	public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) 
	{
	    int chunkX = pChunk.getPos().x;
	    int chunkZ = pChunk.getPos().z;
	    String fileName = "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca";
        File regionDir = new File(FMLPaths.CONFIGDIR.get().toFile(), "beyondtheabyss/region");
        File regionFile = new File(regionDir, fileName);
	    try(RegionFile region = new RegionFile(regionFile.toPath(), regionDir.toPath(), false))
	    {
	        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
	        ServerLevel world = server.getLevel(BTAWorlds.MIRRORED_CITY);
            try(DataInputStream input = region.getChunkDataInputStream(pChunk.getPos())) 
            {
	            CompoundTag nbt = NbtIo.read(input);
	            ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
	            ProtoChunk cityChunk = ChunkSerializer.read(world.getLevel(), world.getLevel().getPoiManager(), chunkPos, nbt);
	            for(int i = 0; i < pChunk.getSections().length; i++)
	            {
	                LevelChunkSection from = cityChunk.getSections()[i];
	                LevelChunkSection to = pChunk.getSections()[i];
	                if(from != null && to != null) 
	                {
	                    for(int y = 0; y < 16; y++) 
	                    {
	                        int worldY = i * 16 + y;
	                        for(int z = 0; z < 16; z++)
	                        {
	                            for(int x = 0; x < 16; x++)
	                            {
	                            	boolean flag = from.getBlockState(x, y, z).isAir() ? worldY < 200 : true;
	                                if(flag) 
	                                {
	                                    to.setBlockState(x, y, z, from.getBlockState(x, y, z), false);
	                                }
	                            }
	                        }
	                    }
	                    to.recalcBlockCounts();
	                }
	            }
	            
	            int maxChunkHeight = pChunk.getSections().length * 16;
	            int minY = world.getMinBuildHeight();
	            int maxY = world.getMaxBuildHeight();
	            int offsetY = 10;
	            for(int sectionIndex = 0; sectionIndex < pChunk.getSections().length; sectionIndex++) 
	            {
	                LevelChunkSection from = cityChunk.getSections()[sectionIndex];
	                if(from == null) 
	                	continue;
	                for(int y = 0; y < 16; y++) 
	                {
	                    int worldY = sectionIndex * 16 + y;
	                    if(worldY < minY || worldY > maxY) 
	                    	continue;
	                    int newWorldY = offsetY + (maxY - worldY);
	                    if(newWorldY < 0 || newWorldY >= maxChunkHeight) 
	                    	continue;
	                    int newSectionIndex = newWorldY / 16;
	                    int newY = newWorldY % 16;
	                    LevelChunkSection to = pChunk.getSections()[newSectionIndex];
	                    if(to == null) 
	                    	continue;
	                    for(int z = 0; z < 16; z++) 
	                    {
	                        for(int x = 0; x < 16; x++)
	                        {
	                            BlockState state = from.getBlockState(x, y, z);
	                            boolean isAir = state.isAir();
	                            if(isAir && newWorldY < 300)
	                            {
	                            	continue;
	                            }
	                            to.setBlockState(x, newY, z, state, false);
	                        }
	                    }
	                    to.recalcBlockCounts();
	                }
	            }
	        }
	    } 
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
}
