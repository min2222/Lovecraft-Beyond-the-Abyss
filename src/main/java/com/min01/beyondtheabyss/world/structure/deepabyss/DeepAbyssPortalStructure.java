package com.min01.beyondtheabyss.world.structure.deepabyss;

import java.util.Optional;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.server.ServerLifecycleHooks;

public class DeepAbyssPortalStructure extends Structure
{
	public static final Codec<DeepAbyssPortalStructure> CODEC = simpleCodec(DeepAbyssPortalStructure::new);
	public static final ResourceLocation STRUCTURE_LOCATION = new ResourceLocation(BeyondtheAbyss.MODID, "deepabyss/deep_abyss_portal");
	
	public DeepAbyssPortalStructure(StructureSettings p_226558_)
	{
		super(p_226558_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext p_227387_)
	{
		BTASavedData data = BTASavedData.get(ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD));
		if(data.getAbyssPortalPos().equals(BlockPos.ZERO))
		{
			return onTopOfChunkCenter(p_227387_, Heightmap.Types.OCEAN_FLOOR_WG, (p_227390_) -> 
			{
				StructureTemplateManager manager = p_227387_.structureTemplateManager();
				ChunkPos chunkPos = p_227387_.chunkPos();
				BlockPos blockPos = chunkPos.getWorldPosition();
				StructureTemplate template = manager.getOrCreate(STRUCTURE_LOCATION);
				DeepAbyssPortalStructurePiece piece = new DeepAbyssPortalStructurePiece(manager, STRUCTURE_LOCATION, blockPos);
				BTAUtil.moveStructurePiece(p_227387_, Heightmap.Types.OCEAN_FLOOR_WG, piece, template, Rotation.NONE, Mirror.NONE, t -> 
				{
					piece.move(0, t, 0);
				});
				p_227390_.addPiece(piece);
			});
		}
		return Optional.empty();
	}
	
	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.DEEP_ABYSS_PORTAL.get();
	}
}