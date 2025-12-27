package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleyOsteoCoralPatchFeature extends Feature<NoneFeatureConfiguration>
{
	public DeathValleyOsteoCoralPatchFeature(Codec<NoneFeatureConfiguration> pCodec) 
	{
		super(pCodec);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext)
	{
		WorldGenLevel level = pContext.level();
		BlockPos pos = pContext.origin();
		if(level.getBlockState(pos.below()).is(BTABlocks.ROT_SOIL.get()))
		{
			level.setBlock(pos, BTABlocks.OSTEO_CORAL.get().defaultBlockState(), 2);
		}
		return false;
	}
}