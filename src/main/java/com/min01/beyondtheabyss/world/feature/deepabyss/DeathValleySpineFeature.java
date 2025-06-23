package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
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
			switch(random.nextInt(0, 3))
			{
			case 0:
				this.placeSpine(level, pos, 1);
				break;
			case 1:
				if(level.getFluidState(pos.above()).is(FluidTags.WATER))
				{
					this.placeSpine(level, pos, 2);
				}
				break;
			case 2:
				if(level.getFluidState(pos.above()).is(FluidTags.WATER) && level.getFluidState(pos.above(2)).is(FluidTags.WATER))
				{
					this.placeSpine(level, pos, 3);
				}
				break;
			}
			return true;
		}
		return false;
	}
	
	public void placeSpine(WorldGenLevel level, BlockPos pos, int length)
	{
		BlockState tip = BTABlocks.SPINE_BONE_TIP.get().defaultBlockState();
		BlockState middle = BTABlocks.SPINE_BONE_MIDDLE.get().defaultBlockState();
		BlockState base = BTABlocks.SPINE_BONE_BASE.get().defaultBlockState();
		if(length == 1)
		{
			level.setBlock(pos, tip, 2);
		}
		else if(length == 2)
		{
			level.setBlock(pos, middle, 2);
			level.setBlock(pos.above(1), tip, 2);
		}
		else if(length == 3)
		{
			level.setBlock(pos, base, 2);
			level.setBlock(pos.above(1), middle, 2);
			level.setBlock(pos.above(2), tip, 2);
		}
	}
}
