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
	
	public OneTimePlacedStructure(StructureSettings p_226558_) 
	{
		super(p_226558_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void afterPlace(WorldGenLevel p_226560_, StructureManager p_226561_, ChunkGenerator p_226562_, RandomSource p_226563_, BoundingBox p_226564_, ChunkPos p_226565_, PiecesContainer p_226566_)
	{
		if(p_226560_ instanceof ServerLevel level)
		{
			this.level = level;
		}
		else if(p_226560_ instanceof WorldGenRegion region)
		{
			this.level = region.getLevel();
		}
	}
	
	@Override
	public Optional<GenerationStub> findValidGenerationPoint(GenerationContext p_263060_)
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
		return super.findValidGenerationPoint(p_263060_);
	}
	
	public abstract ResourceKey<Structure> getStructureKey();
}
