package com.min01.beyondtheabyss.world.structure;

import java.util.Optional;

import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraftforge.server.ServerLifecycleHooks;

public abstract class OneTimePlacedStructure extends Structure
{
	public ServerLevel level;
	
	public OneTimePlacedStructure(StructureSettings pSettings) 
	{
		super(pSettings);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void afterPlace(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pChunkGenerator, RandomSource pRandom, BoundingBox pBoundingBox, ChunkPos pChunkPos, PiecesContainer pPieces)
	{
		if(pLevel instanceof ServerLevel level)
		{
			this.level = level;
		}
		else if(pLevel instanceof WorldGenRegion region)
		{
			this.level = region.getLevel();
		}
	}
	
	@Override
	public Optional<GenerationStub> findValidGenerationPoint(GenerationContext pContext)
	{
		//TODO find a way to get proper level instead of always use overworld;
		ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);
		if(level != null)
		{
			BTASavedData data = BTASavedData.get(level);
			if(!data.getStructurePos(this.getStructureKey()).equals(BlockPos.ZERO))
			{
				return Optional.empty();
			}
		}
		return super.findValidGenerationPoint(pContext);
	}
	
	public abstract ResourceKey<Structure> getStructureKey();
}
