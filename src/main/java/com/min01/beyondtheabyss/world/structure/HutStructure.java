package com.min01.beyondtheabyss.world.structure;

import java.util.Optional;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.misc.BTAResourceKeys;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class HutStructure extends OneTimePlacedStructure
{
	public static final Codec<HutStructure> CODEC = simpleCodec(HutStructure::new);
	public static final ResourceLocation STRUCTURE_LOCATION = new ResourceLocation(BeyondtheAbyss.MODID, "hut");
	
	public HutStructure(StructureSettings p_226558_)
	{
		super(p_226558_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext p_227387_)
	{
		return onTopOfChunkCenter(p_227387_, Heightmap.Types.WORLD_SURFACE_WG, (p_227390_) -> 
		{
			StructureTemplateManager manager = p_227387_.structureTemplateManager();
			ChunkPos chunkPos = p_227387_.chunkPos();
			BlockPos blockPos = chunkPos.getWorldPosition();
			RandomSource random = p_227387_.random();
			Rotation rotation = Util.getRandom(Rotation.values(), random);
			StructureTemplate template = manager.getOrCreate(STRUCTURE_LOCATION);
			HutStructurePiece piece = new HutStructurePiece(manager, STRUCTURE_LOCATION, blockPos);
			BTAUtil.moveStructurePiece(p_227387_, Heightmap.Types.WORLD_SURFACE_WG, piece, template, rotation, Mirror.NONE, t -> 
			{
				piece.move(0, t + 2, 0);
			});
			p_227390_.addPiece(piece);
		});
	}
	
	@Override
	public ResourceKey<Structure> getStructureKey() 
	{
		return BTAResourceKeys.BTAStructures.HUT;
	}
	
	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.HUT.get();
	}
}