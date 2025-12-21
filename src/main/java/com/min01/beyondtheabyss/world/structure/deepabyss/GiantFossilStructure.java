package com.min01.beyondtheabyss.world.structure.deepabyss;

import java.util.List;
import java.util.Optional;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
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

public class GiantFossilStructure extends Structure
{
	public static final Codec<GiantFossilStructure> CODEC = simpleCodec(GiantFossilStructure::new);
	public static final ResourceLocation RIB_LOCATION = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deepabyss/giant_rib");
	public static final ResourceLocation SKULL_LOCATION = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deepabyss/giant_skull");
	public static final ResourceLocation SPINE_LOCATION = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deepabyss/giant_spine");
	
	public GiantFossilStructure(StructureSettings pSettings)
	{
		super(pSettings);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext pContext)
	{
		return onTopOfChunkCenter(pContext, Heightmap.Types.OCEAN_FLOOR_WG, builder -> 
		{
			StructureTemplateManager manager = pContext.structureTemplateManager();
			ChunkPos chunkPos = pContext.chunkPos();
			BlockPos blockPos = chunkPos.getWorldPosition();
			RandomSource random = pContext.random();
			Rotation rotation = Util.getRandom(Rotation.values(), random);
			ResourceLocation location = Util.getRandom(List.of(RIB_LOCATION, SKULL_LOCATION, SPINE_LOCATION), random);
			StructureTemplate template = manager.getOrCreate(location);
			GiantFossilStructurePiece piece = new GiantFossilStructurePiece(manager, location, blockPos);
			BTAUtil.moveStructurePiece(pContext, Heightmap.Types.OCEAN_FLOOR_WG, piece, template, rotation, Mirror.NONE, t -> 
			{
				piece.move(0, t, 0);
			});
			builder.addPiece(piece);
		});
	}
	
	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.GIANT_FOSSIL.get();
	}
}