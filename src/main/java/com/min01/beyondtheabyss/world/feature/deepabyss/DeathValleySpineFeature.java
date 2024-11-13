package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleySpineFeature extends Feature<NoneFeatureConfiguration>
{
	public DeathValleySpineFeature(Codec<NoneFeatureConfiguration> p_65786_) 
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
			if(random.nextFloat() <= 0.05F && level.getFluidState(pos.above()).is(FluidTags.WATER) && level.getFluidState(pos.above(2)).is(FluidTags.WATER))
			{
				this.placeSpine(level, pos, 3);
				return true;
			}
			if(random.nextFloat() <= 0.025F && level.getFluidState(pos.above()).is(FluidTags.WATER))
			{
				this.placeSpine(level, pos, 2);
				return true;
			}
			if(random.nextFloat() <= 0.01F)
			{
				this.placeSpine(level, pos, 1);
				return true;
			}
		}
		return false;
	}
	
	public void placeSpine(WorldGenLevel level, BlockPos pos, int length)
	{
		if(length == 1)
		{
			level.setBlock(pos, BTABlocks.SPINE_BONE_TIP.get().defaultBlockState(), 2);
		}
		else if(length == 2)
		{
			level.setBlock(pos, BTABlocks.SPINE_BONE_MIDDLE.get().defaultBlockState(), 2);
			level.setBlock(pos.above(1), BTABlocks.SPINE_BONE_TIP.get().defaultBlockState(), 2);
		}
		else if(length == 3)
		{
			level.setBlock(pos, BTABlocks.SPINE_BONE_BASE.get().defaultBlockState(), 2);
			level.setBlock(pos.above(1), BTABlocks.SPINE_BONE_MIDDLE.get().defaultBlockState(), 2);
			level.setBlock(pos.above(2), BTABlocks.SPINE_BONE_TIP.get().defaultBlockState(), 2);
		}
	}
}
