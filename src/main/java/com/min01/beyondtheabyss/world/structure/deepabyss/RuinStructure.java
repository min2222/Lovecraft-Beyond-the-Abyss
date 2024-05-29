package com.min01.beyondtheabyss.world.structure.deepabyss;

import java.util.Optional;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class RuinStructure extends Structure
{
	public static final Codec<RuinStructure> CODEC = simpleCodec(RuinStructure::new);
	private static final ResourceLocation[] RESOURCE_LOCATIONS = new ResourceLocation[]{new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/ruin_1")};
	
	public RuinStructure(StructureSettings p_226558_)
	{
		super(p_226558_);
	}

	@Override
	public Optional<GenerationStub> findGenerationPoint(GenerationContext p_226571_)
	{
		return onTopOfChunkCenter(p_226571_, Heightmap.Types.OCEAN_FLOOR_WG, (p_229979_) ->
		{
			generatePieces(p_229979_, p_226571_);
		});
	}
	
	private static void generatePieces(StructurePiecesBuilder p_229981_, Structure.GenerationContext p_229982_)
	{
		BlockPos blockpos = new BlockPos(p_229982_.chunkPos().getMinBlockX(), 90, p_229982_.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(p_229982_.random());
		addPieces(p_229982_.structureTemplateManager(), blockpos, rotation, p_229981_, p_229982_.random());
	}
	
	public static void addPieces(StructureTemplateManager p_228995_, BlockPos p_228996_, Rotation p_228997_, StructurePieceAccessor p_228998_, RandomSource p_228999_)
	{
        int i = p_228999_.nextInt(RESOURCE_LOCATIONS.length);
        p_228998_.addPiece(new RuinPiece(p_228995_, new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/ruin_base"), p_228996_, p_228997_, false));
        p_228998_.addPiece(new RuinPiece(p_228995_, RESOURCE_LOCATIONS[i], p_228996_, p_228997_, true));
        //p_228998_.addPiece(new RuinPiece(p_228995_, aresourcelocation[i], p_228996_, p_228997_));
        //p_228998_.addPiece(new RuinPiece(p_228995_, aresourcelocation[i], p_228996_, p_228997_));
	}

	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.RUIN.get();
	}
}
