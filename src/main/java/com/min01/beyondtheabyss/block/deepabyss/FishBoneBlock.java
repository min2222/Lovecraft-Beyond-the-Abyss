package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class FishBoneBlock extends AbstractBoneBlock
{
	public FishBoneBlock()
	{
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).strength(0.5F));
	}
}
