package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AbyssalithSpikeFeature extends Feature<NoneFeatureConfiguration> 
{
	public AbyssalithSpikeFeature(Codec<NoneFeatureConfiguration> p_66003_) 
	{
		super(p_66003_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) 
	{
		BlockPos blockPos = p_159882_.origin();
		RandomSource random = p_159882_.random();
		WorldGenLevel worldGenLevel;
		
		for(worldGenLevel = p_159882_.level(); worldGenLevel.getBlockState(blockPos).is(Blocks.WATER) && blockPos.getY() > worldGenLevel.getMinBuildHeight() + 2; blockPos = blockPos.below())
		{
			
		}
		
		if(!worldGenLevel.getBlockState(blockPos).is(BTABlocks.ABYSSALITH.get())) 
		{
			return false;
		}
		else
		{
			blockPos = blockPos.above(random.nextInt(4));
			int i = random.nextInt(4) + 7;
			int j = i / 4 + random.nextInt(2);
			for(int k = 0; k < i; ++k) 
			{
				float f = (1.0F - (float) k / (float) i) * (float) j;
				int l = Mth.ceil(f);

				for(int i1 = -l; i1 <= l; ++i1) 
				{
					float f1 = (float) Mth.abs(i1) - 0.25F;
					for(int j1 = -l; j1 <= l; ++j1)
					{
						float f2 = (float) Mth.abs(j1) - 0.25F;
						if((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(random.nextFloat() > 0.75F)))
						{
							BlockState blockstate = worldGenLevel.getBlockState(blockPos.offset(i1, k, j1));
							if(blockstate.is(Blocks.WATER))
							{
								this.setBlock(worldGenLevel, blockPos.offset(i1, k, j1), BTABlocks.ABYSSALITH.get().defaultBlockState());
							}

							if(k != 0 && l > 1)
							{
								blockstate = worldGenLevel.getBlockState(blockPos.offset(i1, -k, j1));
								if(blockstate.is(Blocks.WATER))
								{
									this.setBlock(worldGenLevel, blockPos.offset(i1, -k, j1), BTABlocks.ABYSSALITH.get().defaultBlockState());
								}
							}
						}
					}
				}
			}

			int k1 = j - 1;
			if(k1 < 0)
			{
				k1 = 0;
			} 
			else if (k1 > 1) 
			{
				k1 = 1;
			}

			for(int l1 = -k1; l1 <= k1; ++l1)
			{
				for(int i2 = -k1; i2 <= k1; ++i2) 
				{
					BlockPos blockpos1 = blockPos.offset(l1, -1, i2);
					int j2 = 50;
					if(Math.abs(l1) == 1 && Math.abs(i2) == 1)
					{
						j2 = random.nextInt(5);
					}

					while(blockpos1.getY() > 35) 
					{
						BlockState blockstate1 = worldGenLevel.getBlockState(blockpos1);
						if(!blockstate1.is(Blocks.WATER)) 
						{
							break;
						}

						this.setBlock(worldGenLevel, blockpos1, BTABlocks.ABYSSALITH.get().defaultBlockState());
						blockpos1 = blockpos1.below();
						--j2;
						if(j2 <= 0)
						{
							blockpos1 = blockpos1.below(random.nextInt(5) + 1);
							j2 = random.nextInt(5);
						}
					}
				}
			}

			return true;
		}
	}
}
