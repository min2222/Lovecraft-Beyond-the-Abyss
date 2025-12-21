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
	public static final ResourceLocation STRUCTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "hut");
	
	public HutStructure(StructureSettings pSettings)
	{
		super(pSettings);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext pContext)
	{
		return onTopOfChunkCenter(pContext, Heightmap.Types.WORLD_SURFACE_WG, builder -> 
		{
			StructureTemplateManager manager = pContext.structureTemplateManager();
			ChunkPos chunkPos = pContext.chunkPos();
			BlockPos blockPos = chunkPos.getWorldPosition();
			RandomSource random = pContext.random();
			Rotation rotation = Util.getRandom(Rotation.values(), random);
			StructureTemplate template = manager.getOrCreate(STRUCTURE_LOCATION);
			HutStructurePiece piece = new HutStructurePiece(manager, STRUCTURE_LOCATION, blockPos);
			BTAUtil.moveStructurePiece(pContext, Heightmap.Types.WORLD_SURFACE_WG, piece, template, rotation, Mirror.NONE, t -> 
			{
				piece.move(0, t + 2, 0);
			});
			builder.addPiece(piece);
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