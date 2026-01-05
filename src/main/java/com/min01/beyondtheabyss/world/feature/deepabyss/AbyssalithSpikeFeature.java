package com.min01.beyondtheabyss.world.feature.deepabyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AbyssalithSpikeFeature extends Feature<NoneFeatureConfiguration> 
{
	public AbyssalithSpikeFeature(Codec<NoneFeatureConfiguration> pCodec) 
	{
		super(pCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) 
	{
	    WorldGenLevel level = context.level();
	    BlockPos origin = context.origin();
	    origin = BTAUtil.getGroundPos(level, origin.getX(), origin.getY(), origin.getZ());
	    RandomSource random = level.getRandom();
	    BlockState state = BTABlocks.ABYSSALITH.get().defaultBlockState();

	    int length = random.nextInt(9, 12);
	    int heightStep = 3;
	    int width = random.nextInt(9, 12);
	    int direction = random.nextInt(4);

	    int dx = (direction == 0 || direction == 1) ? 1 : -1;
	    int dz = (direction == 0 || direction == 2) ? 1 : -1;

	    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

	    double stepSize = 0.15;
	    int steps = (int)(length / stepSize);

	    if(level.getBlockState(origin.below()).is(BTABlocks.ABYSSALITH.get()) && origin.getY() >= 20 && origin.getY() < 150)
	    {
		    for(int i = 0; i < steps; i++)
		    {
		        double step = i * stepSize;

		        int x = origin.getX() + (int)Math.round(dx * step);
		        int z = origin.getZ() + (int)Math.round(dz * step);
		        int y = origin.getY() + (int)Math.round(step * heightStep);

		        double maxWidth = width;
		        double taperFactor = 0.1;
		        double progress = (double)i / steps;

		        double currentWidth = maxWidth * (1.0 - progress * (1.0 - taperFactor));
		        int localWidth = Math.max(1, (int)Math.floor(currentWidth));

		        for(int xOffset = -localWidth; xOffset <= localWidth; xOffset++)
		        {
		            for(int zOffset = -localWidth; zOffset <= localWidth; zOffset++)
		            {
		                double dist = Math.sqrt(xOffset * xOffset + zOffset * zOffset);
		                if(dist <= currentWidth)
		                {
		                    pos.set(x + xOffset, y, z + zOffset);
		                    level.setBlock(pos, state, 2);
		                }
		            }
		        }
		    }
	    }
	    return true;
	}
}
