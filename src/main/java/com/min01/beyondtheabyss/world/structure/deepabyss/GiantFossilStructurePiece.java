package com.min01.beyondtheabyss.world.structure.deepabyss;

import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class GiantFossilStructurePiece extends TemplateStructurePiece 
{
	public static final RandomSource RAND = RandomSource.create();
	public static final StructurePlaceSettings SETTINGS = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRotation(Util.getRandom(Rotation.values(), RAND));

    public GiantFossilStructurePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pTemplatePosition) 
    {
    	super(BTAStructures.GIANT_FOSSIL_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(), SETTINGS, pTemplatePosition);
    }

    public GiantFossilStructurePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) 
    {
    	super(BTAStructures.GIANT_FOSSIL_PIECE.get(), pTag, pStructureTemplateManager, pPlaceSettingsFactory -> 
    	{
    		return SETTINGS;
    	});
    }

	@Override
	protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox)
	{
		
	}
}
