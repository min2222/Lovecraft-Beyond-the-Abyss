package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.deepabyss.GlaringBarnacleBlock;
import com.min01.beyondtheabyss.world.feature.DirectionBlockStateConfiguration;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class GlaringBarnacleFeature extends Feature<DirectionBlockStateConfiguration>
{
	public GlaringBarnacleFeature(Codec<DirectionBlockStateConfiguration> pCodec) 
	{
		super(pCodec);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<DirectionBlockStateConfiguration> pContext)
	{
		WorldGenLevel level = pContext.level();
		BlockPos pos = pContext.origin();
		DirectionBlockStateConfiguration configuration = pContext.config();
		if(!level.getBlockState(pos).isCollisionShapeFullBlock(level, pos))
		{
			for(Direction direction : configuration.directions)
			{
				if(level.getBlockState(pos.relative(direction)).is(configuration.check.getBlock()))
				{
					level.setBlock(pos, configuration.state.setValue(GlaringBarnacleBlock.FACE, AttachFace.WALL).setValue(GlaringBarnacleBlock.FACING, direction.getOpposite()), 2);
				}
				else
				{
					continue;
				}
			}
			return true;
		}
		return false;
	}
}