package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.ToothvinePlantBlock;
import com.min01.beyondtheabyss.block.deepabyss.ToothvinePlantBlock.VineState;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleyToothvinePatchFeature extends Feature<NoneFeatureConfiguration>
{
	public DeathValleyToothvinePatchFeature(Codec<NoneFeatureConfiguration> p_65786_) 
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
			this.placeVine(level, pos, random, random.nextInt(2, 16));
			return true;
		}
		return false;
	}

	public void placeVine(WorldGenLevel level, BlockPos pos, RandomSource random, int length)
	{
		level.setBlock(pos, BTABlocks.TOOTHVINE_PLANT.get().defaultBlockState().setValue(ToothvinePlantBlock.VINE_STATE, VineState.BASE), 2);
		for(int i = 0; i < length; i++)
		{
			if(level.getFluidState(pos.above(i + 1)).is(FluidTags.WATER))
			{
				if(i == length - 1)
				{
					level.setBlock(pos.above(length), BTABlocks.TOOTHVINE.get().defaultBlockState(), 2);
				}
				else
				{
					level.setBlock(pos.above(i + 1), BTABlocks.TOOTHVINE_PLANT.get().defaultBlockState().setValue(ToothvinePlantBlock.VINE_STATE, random.nextBoolean() ? VineState.VARIANT_1 : VineState.VARIANT_2), 2);
				}
			}
			else
			{
				level.setBlock(pos.above(i), BTABlocks.TOOTHVINE.get().defaultBlockState(), 2);
				break;
			}
		}
	}
	
}
