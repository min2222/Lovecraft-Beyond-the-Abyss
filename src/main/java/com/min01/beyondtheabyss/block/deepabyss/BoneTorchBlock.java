package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.TorchBlock;

public class BoneTorchBlock extends TorchBlock
{
	public BoneTorchBlock(Properties pProperties) 
	{
		super(pProperties, ParticleTypes.FLAME);
	}
}
