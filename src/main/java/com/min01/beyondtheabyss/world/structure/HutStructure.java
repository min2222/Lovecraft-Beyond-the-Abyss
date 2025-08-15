package com.min01.beyondtheabyss.world.structure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.server.ServerLifecycleHooks;

public class HutStructure extends Structure
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
		BTASavedData data = BTASavedData.get(ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD));
		if(!data.isHutGenerated())
		{
			return onTopOfChunkCenter(p_227387_, Heightmap.Types.WORLD_SURFACE_WG, (p_227390_) -> 
			{
				ChunkPos chunkPos = p_227387_.chunkPos();
				ChunkGenerator chunkGenerator = p_227387_.chunkGenerator();
				RandomSource random = p_227387_.random();
				RandomState randomState = p_227387_.randomState();
				LevelHeightAccessor heightAccessor = p_227387_.heightAccessor();
				StructureTemplateManager manager = p_227387_.structureTemplateManager();
				BlockPos blockPos = chunkPos.getWorldPosition();
				StructureTemplate template = manager.getOrCreate(STRUCTURE_LOCATION);
				Rotation rotation = Util.getRandom(Rotation.values(), random);
				BlockPos blockPos1 = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
				BoundingBox boundingBox = template.getBoundingBox(blockPos, rotation, blockPos1, Mirror.NONE);
				BlockPos blockPos2 = boundingBox.getCenter();
				HutStructurePiece piece = new HutStructurePiece(manager, STRUCTURE_LOCATION, blockPos);
				int i = chunkGenerator.getBaseHeight(blockPos2.getX(), blockPos2.getZ(), Types.WORLD_SURFACE_WG, heightAccessor, randomState);
				int j = findSuitableY(random, chunkGenerator, i, piece.getBoundingBox(), heightAccessor, randomState);
				piece.move(0, j + 2, 0);
				p_227390_.addPiece(piece);
			});
		}
		return Optional.empty();
	}
	
	@Override
	public StructureType<?> type() 
	{
		return BTAStructures.HUT.get();
	}
	
	//copied from RuinedPortalStructure
	public static int findSuitableY(RandomSource p_229267_, ChunkGenerator p_229268_, int p_229271_, BoundingBox p_229273_, LevelHeightAccessor p_229274_, RandomState p_229275_)
	{
		int j = p_229274_.getMinBuildHeight() + 15;
		int i = p_229271_;
		List<BlockPos> list1 = ImmutableList.of(new BlockPos(p_229273_.minX(), 0, p_229273_.minZ()), new BlockPos(p_229273_.maxX(), 0, p_229273_.minZ()), new BlockPos(p_229273_.minX(), 0, p_229273_.maxZ()), new BlockPos(p_229273_.maxX(), 0, p_229273_.maxZ()));
		List<NoiseColumn> list = list1.stream().map((p_229280_) -> 
		{
			return p_229268_.getBaseColumn(p_229280_.getX(), p_229280_.getZ(), p_229274_, p_229275_);
		}).collect(Collectors.toList());
		Heightmap.Types heightmap$types = Heightmap.Types.WORLD_SURFACE_WG;
		int l;
		for(l = i; l > j; --l) 
		{
			int i1 = 0;
			for(NoiseColumn noisecolumn : list)
			{
				BlockState blockstate = noisecolumn.getBlock(l);
				if(heightmap$types.isOpaque().test(blockstate)) 
				{
					++i1;
					if(i1 == 3)
					{
						return l;
					}
				}
			}
		}
		return l;
	}
}