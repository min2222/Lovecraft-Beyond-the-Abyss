package com.min01.beyondtheabyss.world.structure.deepabyss;

import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class RuinPiece extends TemplateStructurePiece
{
	private boolean isInterior;
	private boolean isTransformed;
	
    public RuinPiece(StructurePieceSerializationContext ctx, CompoundTag tag) 
    {
    	super(BTAStructures.RUIN_PIECE.get(), tag, ctx.structureTemplateManager(), (resourceLocation) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
    }
    
    public RuinPiece(StructureTemplateManager p_229018_, ResourceLocation p_229019_, BlockPos p_229020_, Rotation p_229021_, boolean isInterior) 
    {
    	super(BTAStructures.RUIN_PIECE.get(), 0, p_229018_, p_229019_, p_229019_.toString(), makeSettings(p_229021_), p_229020_);
    	this.isInterior = isInterior;
    }
    
    private static StructurePlaceSettings makeSettings(Rotation p_229037_) 
    {
    	return (new StructurePlaceSettings()).setRotation(p_229037_).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
    }
    
    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext p_229039_, CompoundTag p_229040_)
    {
        super.addAdditionalSaveData(p_229039_, p_229040_);
        p_229040_.putString("Rot", this.placeSettings.getRotation().name());
    }
    
    @Override
    public void postProcess(WorldGenLevel p_226899_, StructureManager p_226900_, ChunkGenerator p_226901_, RandomSource p_226902_, BoundingBox p_226903_, ChunkPos p_226904_, BlockPos p_226905_) 
    {
    	if(this.isInterior && !this.isTransformed)
    	{
    		this.templatePosition = this.templatePosition.offset(1, 0, 1);
    		this.isTransformed = true;
    	}
    	super.postProcess(p_226899_, p_226900_, p_226901_, p_226902_, p_226903_, p_226904_, p_226905_);
    }

	@Override
	protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) 
	{
		
	}
}
