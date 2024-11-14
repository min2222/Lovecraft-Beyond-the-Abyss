package com.min01.beyondtheabyss.world.structure.deepabyss;

import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class RuinPiece extends TemplateStructurePiece
{
    public RuinPiece(StructurePieceSerializationContext ctx, CompoundTag tag) 
    {
    	super(BTAStructures.RUIN_PIECE.get(), tag, ctx.structureTemplateManager(), (resourceLocation) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
    }
    
    public RuinPiece(StructureTemplateManager p_229018_, ResourceLocation p_229019_, BlockPos p_229020_, Rotation p_229021_) 
    {
    	super(BTAStructures.RUIN_PIECE.get(), 0, p_229018_, p_229019_, p_229019_.toString(), makeSettings(p_229021_), p_229020_);
    }
    
    private static StructurePlaceSettings makeSettings(Rotation p_229037_) 
    {
    	return new StructurePlaceSettings().setRotation(p_229037_).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
    }
    
    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext p_229039_, CompoundTag p_229040_)
    {
        super.addAdditionalSaveData(p_229039_, p_229040_);
        p_229040_.putString("Rot", this.getRotation().name());
    }

	@Override
	protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) 
	{
		
	}
}
