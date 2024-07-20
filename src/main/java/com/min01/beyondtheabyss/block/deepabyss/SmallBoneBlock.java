package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class SmallBoneBlock extends AbstractConnectedBoneBlock
{
	public SmallBoneBlock() 
	{
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(1.0F));
	}
}
