package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleyBonePatchFeature extends Feature<NoneFeatureConfiguration>
{
	public DeathValleyBonePatchFeature(Codec<NoneFeatureConfiguration> pCodec) 
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
				this.placeSpine(level, pos, random.nextInt(1, 4));
			}
			else
			{
				BlockState state = BTABlocks.CRACKED_BONE_BLOCK.get().defaultBlockState();
				if(random.nextBoolean())
				{
					state = BTABlocks.SMALL_BONE.get().defaultBlockState();
				}
				this.placePillar(level, pos, random.nextInt(3, 5), state);
			}
			return true;
		}
		return false;
	}
	
	public void placePillar(WorldGenLevel level, BlockPos pos, int length, BlockState bone)
	{
		MutableBlockPos mutable = new MutableBlockPos();
		mutable.set(pos);
		for(int i = 0; i < length; i++)
		{
			if(level.getBlockState(mutable).is(Blocks.WATER))
			{
				level.setBlock(mutable, bone, 2);
				mutable.set(pos.above(i + 1));
			}
		}
	}
	
	public void placeSpine(WorldGenLevel level, BlockPos pos, int length)
	{
		BlockState tip = BTABlocks.SPINE_BONE_TIP.get().defaultBlockState();
		BlockState middle = BTABlocks.SPINE_BONE_MIDDLE.get().defaultBlockState();
		BlockState base = BTABlocks.SPINE_BONE_BASE.get().defaultBlockState();
		if(length == 1)
		{
			if(level.getBlockState(pos).is(Blocks.WATER))
			{
				level.setBlock(pos, tip, 2);
			}
		}
		else if(length == 2)
		{
			if(level.getBlockState(pos).is(Blocks.WATER) && level.getBlockState(pos.above(1)).is(Blocks.WATER))
			{
				level.setBlock(pos, middle, 2);
				level.setBlock(pos.above(1), tip, 2);
			}
		}
		else if(length == 3)
		{
			if(level.getBlockState(pos).is(Blocks.WATER) && level.getBlockState(pos.above(1)).is(Blocks.WATER) && level.getBlockState(pos.above(2)).is(Blocks.WATER))
			{
				level.setBlock(pos, base, 2);
				level.setBlock(pos.above(1), middle, 2);
				level.setBlock(pos.above(2), tip, 2);
			}
		}
	}
}
