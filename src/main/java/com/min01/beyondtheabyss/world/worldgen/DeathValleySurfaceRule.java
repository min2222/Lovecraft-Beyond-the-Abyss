package com.min01.beyondtheabyss.world.worldgen;

import java.util.Arrays;
import java.util.List;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.RotSoilBlock;
import com.min01.beyondtheabyss.block.deepabyss.RotSoilBlock.SoilType;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class DeathValleySurfaceRule extends SurfaceRules
{
	public static record StateRule(BlockState state) implements SurfaceRules.SurfaceRule
	{
		@Override
		public BlockState tryApply(int pX, int pY, int pZ) 
		{
			if(this.state.is(BTABlocks.ROT_SOIL.get()))
			{
				return this.randomizeSoil(this.state);
			}
			return this.state;
		}

		public BlockState randomizeSoil(BlockState state) 
		{
			SoilType type = SoilType.VARIANT_1;
			if(Math.random() <= 0.1F)
			{
				List<SoilType> types = Arrays.asList(SoilType.VARIANT_2, SoilType.VARIANT_3, SoilType.VARIANT_4, SoilType.VARIANT_8, SoilType.VARIANT_9);
				int random = (int) Math.floor(Math.random() * types.size());
				type = types.get(random);
			}
			else if(Math.random() <= 0.05F)
			{
				List<SoilType> types = Arrays.asList(SoilType.VARIANT_5, SoilType.VARIANT_6, SoilType.VARIANT_7, SoilType.VARIANT_10);
				int random = (int) Math.floor(Math.random() * types.size());
				type = types.get(random);
			}
			return state.setValue(RotSoilBlock.SOIL_TYPE, type);
		}
	}
}
