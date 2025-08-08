package com.min01.beyondtheabyss.world.structure;

import java.util.Optional;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.server.ServerLifecycleHooks;

public class HutStructure extends Structure
{
	public static final Codec<HutStructure> CODEC = simpleCodec(HutStructure::new);
	public static final ResourceLocation STRUCTURE_LOCATION = new ResourceLocation(BeyondtheAbyss.MODID, "hut");
	
	public HutStructure(StructureSettings p_226558_)
	{
		super(p_226558_);
	}
	
	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext p_227387_)
	{
		BTASavedData data = BTASavedData.get(ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD));
		if(!data.isHutGenerated())
		{
			return onTopOfChunkCenter(p_227387_, Heightmap.Types.WORLD_SURFACE_WG, (p_227390_) -> 
			{
				ChunkPos chunkPos = p_227387_.chunkPos();
				BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), 90, chunkPos.getMinBlockZ());
				p_227390_.addPiece(new HutStructurePiece(p_227387_.structureTemplateManager(), STRUCTURE_LOCATION, blockPos));
			});
		}
		return Optional.empty();
	}
	
	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.HUT.get();
	}
}