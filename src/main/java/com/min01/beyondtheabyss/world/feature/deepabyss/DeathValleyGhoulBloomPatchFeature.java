package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.GhoulBloomBlock;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleyGhoulBloomPatchFeature extends Feature<NoneFeatureConfiguration>
{
	public DeathValleyGhoulBloomPatchFeature(Codec<NoneFeatureConfiguration> p_65786_) 
	{
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_)
	{
		WorldGenLevel level = p_159749_.level();
		BlockPos pos = p_159749_.origin();
		RandomSource random = p_159749_.random();
		if(level.getBlockState(pos.below()).is(BTABlocks.ROT_SOIL.get()))
		{
			if(random.nextBoolean())
			{
				this.placeGhoulBloom(level, pos, false);
				return true;
			}
			else if(level.getFluidState(pos.above()).is(FluidTags.WATER))
			{
				this.placeGhoulBloom(level, pos, true);
				return true;
			}
		}
		return false;
	}
	
	public void placeGhoulBloom(WorldGenLevel level, BlockPos pos, boolean isTall)
	{
		if(!isTall)
		{
 			level.setBlock(pos, BTABlocks.GHOUL_BLOOM.get().defaultBlockState().setValue(GhoulBloomBlock.GROWN, true), 2);
		}
		else
		{
			
		}
	}
}
