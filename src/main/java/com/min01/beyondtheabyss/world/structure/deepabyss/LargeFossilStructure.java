package com.min01.beyondtheabyss.world.structure.deepabyss;

import java.util.Optional;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class LargeFossilStructure extends Structure
{
	public static final Codec<LargeFossilStructure> CODEC = simpleCodec(LargeFossilStructure::new);
	public static final ResourceLocation STRUCTURE_LOCATION = new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/large_fossil");
	
	public LargeFossilStructure(StructureSettings p_226558_)
	{
		super(p_226558_);
	}
	
	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext p_227387_)
	{
		return onTopOfChunkCenter(p_227387_, Heightmap.Types.OCEAN_FLOOR_WG, (p_227390_) -> 
		{
			StructureTemplateManager manager = p_227387_.structureTemplateManager();
			ChunkPos chunkPos = p_227387_.chunkPos();
			BlockPos blockPos = chunkPos.getWorldPosition();
			LargeFossilStructurePiece piece = new LargeFossilStructurePiece(manager, STRUCTURE_LOCATION, blockPos);
			p_227390_.addPiece(piece);
		});
	}
	
	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.LARGE_FOSSIL.get();
	}
}