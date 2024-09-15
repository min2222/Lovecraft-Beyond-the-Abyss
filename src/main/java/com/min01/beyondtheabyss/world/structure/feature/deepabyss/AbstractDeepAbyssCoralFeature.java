package com.min01.beyondtheabyss.world.structure.feature.deepabyss;

import java.util.Optional;

import com.min01.beyondtheabyss.misc.BTATags.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class AbstractDeepAbyssCoralFeature extends Feature<NoneFeatureConfiguration>
{
	public AbstractDeepAbyssCoralFeature(Codec<NoneFeatureConfiguration> p_65429_)
	{
		super(p_65429_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159536_) 
	{
		RandomSource randomsource = p_159536_.random();
		WorldGenLevel worldgenlevel = p_159536_.level();
		BlockPos blockpos = p_159536_.origin();
		Optional<Block> optional = Registry.BLOCK.getTag(BTABlocks.ABYSS_CORAL_BLOCKS).flatMap((p_224980_) ->
		{
			return p_224980_.getRandomElement(randomsource);
		}).map(Holder::value);
		return optional.isEmpty() ? false : this.placeFeature(worldgenlevel, randomsource, blockpos, optional.get().defaultBlockState());
	}

	protected abstract boolean placeFeature(LevelAccessor p_224966_, RandomSource p_224967_, BlockPos p_224968_, BlockState p_224969_);

	@SuppressWarnings("deprecation")
	protected boolean placeCoralBlock(LevelAccessor p_224974_, RandomSource p_224975_, BlockPos p_224976_, BlockState p_224977_) 
	{
		BlockPos blockpos = p_224976_.above();
		BlockState blockstate = p_224974_.getBlockState(p_224976_);
		if((blockstate.is(Blocks.WATER) || blockstate.is(BTABlocks.ABYSS_CORALS)) && p_224974_.getBlockState(blockpos).is(Blocks.WATER))
		{
			p_224974_.setBlock(p_224976_, p_224977_, 3);
			if(p_224975_.nextFloat() < 0.25F) 
			{
				Registry.BLOCK.getTag(BTABlocks.ABYSS_CORALS).flatMap((p_224972_) ->
				{
					return p_224972_.getRandomElement(p_224975_);
				}).map(Holder::value).ifPresent((p_204720_) -> 
				{
					p_224974_.setBlock(blockpos, p_204720_.defaultBlockState(), 2);
				});
			}

			for(Direction direction : Direction.Plane.HORIZONTAL) 
			{
				if(p_224975_.nextFloat() < 0.2F) 
				{
					BlockPos blockpos1 = p_224976_.relative(direction);
					if(p_224974_.getBlockState(blockpos1).is(Blocks.WATER)) 
					{
						Registry.BLOCK.getTag(BTABlocks.ABYSS_WALL_CORALS).flatMap((p_224965_) -> 
						{
							return p_224965_.getRandomElement(p_224975_);
						}).map(Holder::value).ifPresent((p_204725_) -> 
						{
							BlockState blockstate1 = p_204725_.defaultBlockState();
							if (blockstate1.hasProperty(BaseCoralWallFanBlock.FACING)) 
							{
								blockstate1 = blockstate1.setValue(BaseCoralWallFanBlock.FACING, direction);
							}
							p_224974_.setBlock(blockpos1, blockstate1, 2);
						});
					}
				}
			}
			return true;
		} 
		else 
		{
			return false;
		}
	}
}
