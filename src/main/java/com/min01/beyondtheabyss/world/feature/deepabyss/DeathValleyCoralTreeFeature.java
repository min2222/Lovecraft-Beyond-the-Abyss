package com.min01.beyondtheabyss.world.feature.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeathValleyCoralTreeFeature extends Feature<NoneFeatureConfiguration>
{
	public static final List<Direction> LIST = List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
	
	public DeathValleyCoralTreeFeature(Codec<NoneFeatureConfiguration> p_65786_) 
	{
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_)
	{
		WorldGenLevel level = p_159749_.level();
		RandomSource random = p_159749_.random();
		BlockPos pos = p_159749_.origin();
		if(level.getBlockState(pos.below()).is(BTABlocks.ROT_SOIL.get()))
		{
			if(random.nextFloat() <= 0.1F)
			{
				this.placeCoral(level, random, pos, BTABlocks.DEAD_OSTEO_CORAL_BLOCK.get().defaultBlockState());
			}
			return true;
		}
		return false;
	}
	
	//TODO add fan corals;
	public void placeCoral(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state)
	{
		BlockPos initialPos = this.createStem(level, random, pos, state, random.nextInt(3, 6));
		List<BlockPos> list = this.createCoral(level, random, initialPos, state, random.nextInt(4, 6));
		list.forEach(t -> 
		{
			this.createCoral(level, random, t, state, random.nextInt(2, 5));
		});
	}
	
	public List<BlockPos> createCoral(WorldGenLevel level, RandomSource random, BlockPos initialPos, BlockState state, int length)
	{
		List<BlockPos> list = new ArrayList<>();
		for(Direction direction : LIST)
		{
			BlockPos coralPos = this.createShape(level, random, initialPos.below(random.nextInt(0, 3)), state, direction, length);
			list.add(coralPos);
		}
		return list;
	}
	
	public BlockPos createShape(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state, Direction direction, int length)
	{
		BlockPos stemPos = this.createBranch(level, random, pos.below(random.nextInt(0, length)), state, direction, length);
		BlockPos branchPos = this.createStem(level, random, stemPos, state, length);
		return branchPos;
	}
	
	public BlockPos createStem(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state, int pillarLength)
	{
		for(int i = 0; i < pillarLength; i++)
		{
			this.setBlockWhenWater(level, pos.above(i), state);
		}
		return pos.above(pillarLength);
	}
	
	public BlockPos createBranch(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state, Direction direction, int branchLength)
	{
		for(int i = 0; i < branchLength; i++)
		{
			this.setBlockWhenWater(level, pos.relative(direction, i), state);
		}
		return pos.relative(direction, branchLength);
	}
	
	public void setBlockWhenWater(WorldGenLevel level, BlockPos pos, BlockState state)
	{
		if(level.getFluidState(pos).is(FluidTags.WATER))
		{
			level.setBlock(pos, state, 2);
		}
	}
}
