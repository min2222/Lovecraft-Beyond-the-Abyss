package com.min01.beyondtheabyss.world.structure.deepabyss;

import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DeepAbyssPortalStructurePiece extends TemplateStructurePiece 
{
	public static final StructurePlaceSettings SETTINGS = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR);
	
    public DeepAbyssPortalStructurePiece(StructureTemplateManager p_227555_, ResourceLocation p_227556_, BlockPos p_227557_) 
    {
    	super(BTAStructures.DEEP_ABYSS_PORTAL_PIECE.get(), 0, p_227555_, p_227556_, p_227556_.toString(), SETTINGS, p_227557_);
    }

    public DeepAbyssPortalStructurePiece(StructureTemplateManager p_227561_, CompoundTag p_227562_) 
    {
    	super(BTAStructures.DEEP_ABYSS_PORTAL_PIECE.get(), p_227562_, p_227561_, (p_227589_) -> 
    	{
    		return SETTINGS;
    	});
    }

	@Override
	protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_)
	{
		
	}
}
