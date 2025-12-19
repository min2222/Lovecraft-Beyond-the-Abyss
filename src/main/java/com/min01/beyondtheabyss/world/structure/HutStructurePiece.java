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
	
    public HutStructurePiece(StructureTemplateManager p_227555_, ResourceLocation p_227556_, BlockPos p_227557_) 
    {
    	super(BTAStructures.HUT_PIECE.get(), 0, p_227555_, p_227556_, p_227556_.toString(), SETTINGS, p_227557_);
    }

    public HutStructurePiece(StructureTemplateManager p_227561_, CompoundTag p_227562_) 
    {
    	super(BTAStructures.HUT_PIECE.get(), p_227562_, p_227561_, (p_227589_) -> 
    	{
    		return SETTINGS;
    	});
    }

    @Override
    public void postProcess(WorldGenLevel p_226899_, StructureManager p_226900_, ChunkGenerator p_226901_, RandomSource p_226902_, BoundingBox p_226903_, ChunkPos p_226904_, BlockPos p_226905_) 
    {
    	super.postProcess(p_226899_, p_226900_, p_226901_, p_226902_, p_226903_, p_226904_, p_226905_);
    	/*List<StructureTemplate.StructureBlockInfo> list = this.placeSettings.getRandomPalette(this.template.palettes, this.templatePosition).blocks();
        for(StructureTemplate.StructureBlockInfo info : StructureTemplate.processBlockInfos(p_226899_, this.templatePosition, p_226905_, this.placeSettings, list, this.template)) 
        {
        	//TODO prevent block destroyed;
        	//BlockPos pos = info.pos();
        }*/
    }

	@Override
	protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) 
	{
		
	}
}
