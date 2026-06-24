package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.WallTorchBlock;

public class BoneWallTorchBlock extends WallTorchBlock
{
	public BoneWallTorchBlock(Properties pProperties) 
	{
		super(pProperties, ParticleTypes.FLAME);
	}
}
