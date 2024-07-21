package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class JawBoneBlock extends AbstractBoneBlock
{
	public JawBoneBlock()
	{
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(1.5F));
	}
}
