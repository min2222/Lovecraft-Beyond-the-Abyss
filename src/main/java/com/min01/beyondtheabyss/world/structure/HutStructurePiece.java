package com.min01.beyondtheabyss.world.structure;

import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class HutStructurePiece extends TemplateStructurePiece 
{
	public static final StructurePlaceSettings SETTINGS = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
	
    public HutStructurePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pTemplatePosition) 
    {
    	super(BTAStructures.HUT_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(), SETTINGS, pTemplatePosition);
    }

    public HutStructurePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) 
    {
    	super(BTAStructures.HUT_PIECE.get(), pTag, pStructureTemplateManager, pPlaceSettingsFactory -> 
    	{
    		return SETTINGS;
    	});
    }

    @Override
    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) 
    {
    	super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
    	/*List<StructureTemplate.StructureBlockInfo> list = this.placeSettings.getRandomPalette(this.template.palettes, this.templatePosition).blocks();
        for(StructureTemplate.StructureBlockInfo info : StructureTemplate.processBlockInfos(pLevel, this.templatePosition, pPos, this.placeSettings, list, this.template)) 
        {
        	//TODO prevent block destroyed;
        	//BlockPos pos = info.pos();
        }*/
    }

	@Override
	protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) 
	{
		
	}
}
