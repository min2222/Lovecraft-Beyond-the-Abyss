package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class SmallBoneBlock extends AbstractConnectedBoneBlock
{
	public SmallBoneBlock() 
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.0F));
	}
}
