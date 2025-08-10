package com.min01.beyondtheabyss.world.structure;

import com.min01.beyondtheabyss.world.BTABiomes;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class SpireHollowStructure extends AbstractCaveGenerationStructure 
{
    public static final Codec<SpireHollowStructure> CODEC = simpleCodec((settings) -> new SpireHollowStructure(settings));

    public SpireHollowStructure(StructureSettings settings)
    {
        super(settings, BTABiomes.SPIRE_HOLLOW);
    }

    @Override
    protected StructurePiece createPiece(BlockPos offset, BlockPos center, int heightBlocks, int widthBlocks, RandomState randomState)
    {
        return new SpireHollowStructurePiece(offset, center, heightBlocks, widthBlocks);
    }

    @Override
    public int getGenerateYHeight(WorldgenRandom random, int x, int y) 
    {
        return -60;
    }

    @Override
    public int getWidthRadius(WorldgenRandom random) 
    {
        return 100;
    }

    @Override
    public int getHeightRadius(WorldgenRandom random, int seaLevel)
    {
        return 80;
    }

    @Override
    public StructureType<?> type() 
    {
        return BTAStructures.SPIRE_HOLLOW.get();
    }
}
