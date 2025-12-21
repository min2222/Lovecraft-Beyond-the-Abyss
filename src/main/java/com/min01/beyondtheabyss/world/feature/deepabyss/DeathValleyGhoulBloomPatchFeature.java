package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.GhoulBloomBlock;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleyGhoulBloomPatchFeature extends Feature<NoneFeatureConfiguration>
{
	public DeathValleyGhoulBloomPatchFeature(Codec<NoneFeatureConfiguration> pCodec) 
	{
		super(pCodec);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext)
	{
		WorldGenLevel level = pContext.level();
		BlockPos pos = pContext.origin();
		RandomSource random = pContext.random();
		if(level.getBlockState(pos.below()).is(BTABlocks.ROT_SOIL.get()))
		{
			if(random.nextBoolean())
			{
	 			level.setBlock(pos, BTABlocks.GHOUL_BLOOM.get().defaultBlockState().setValue(GhoulBloomBlock.GROWN, true), 2);
				return true;
			}
		}
		return false;
	}
}
