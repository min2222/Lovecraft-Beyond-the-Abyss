package com.min01.beyondtheabyss.world.structure;

import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTABiomes;
import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

public class SpireHollowStructurePiece extends AbstractCaveGenerationStructurePiece
{
    
    public SpireHollowStructurePiece(BlockPos chunkCorner, BlockPos holeCenter, int bowlHeight, int bowlRadius)
    {
        super(BTAStructures.SPIRE_HOLLOW_PIECE.get(), chunkCorner, holeCenter, bowlHeight, bowlRadius);
    }

    public SpireHollowStructurePiece(CompoundTag tag) 
    {
        super(BTAStructures.SPIRE_HOLLOW_PIECE.get(), tag);
    }

    public SpireHollowStructurePiece(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag tag) 
    {
        this(tag);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager featureManager, ChunkGenerator chunkGen, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos)
    {
        int cornerX = this.chunkCorner.getX();
        int cornerY = this.chunkCorner.getY();
        int cornerZ = this.chunkCorner.getZ();
        boolean flag = false;
        BlockPos.MutableBlockPos carve = new BlockPos.MutableBlockPos();
        carve.set(cornerX, cornerY, cornerZ);
        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++) 
            {
                for(int y = 15; y >= 0; y--) 
                {
                    carve.set(cornerX + x, Mth.clamp(cornerY + y, level.getMinBuildHeight(), level.getMaxBuildHeight()), cornerZ + z);
                    if(this.inCircle(carve) && this.checkedGetBlock(level, carve).is(BTATags.BTABlocks.SPIRE_HOLLOW_REPLACEABLES)) 
                    {
                    	flag = true;
                        this.checkedSetBlock(level, carve, Blocks.WATER.defaultBlockState());
                    }
                }
            }
        }
        if(flag) 
        {
        	this.replaceBiomes(level, BTABiomes.SPIRE_HOLLOW, 32);
        }
    }

    private boolean inCircle(BlockPos carve) 
    {
        float wallNoise = (BTAUtil.sampleNoise3D(carve.getX(), (int) (carve.getY() * 0.1F), carve.getZ(), 40) + 1.0F) * 0.5F;
        double yDist = BTAUtil.smin(1.0F - Math.abs(this.holeCenter.getY() - carve.getY()) / (float) (this.height * 0.5F), 1.0F, 0.3F);
        double distToCenter = carve.distToLowCornerSqr(this.holeCenter.getX(), carve.getY(), this.holeCenter.getZ());
        double targetRadius = yDist * (this.radius * wallNoise) * this.radius;
        return distToCenter < targetRadius;
    }
}