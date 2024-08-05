package com.min01.beyondtheabyss.world.structure.feature.deepabyss;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DeepAbyssCoralTreeFeature extends Feature<NoneFeatureConfiguration>
{
	public DeepAbyssCoralTreeFeature(Codec<NoneFeatureConfiguration> p_65786_)
	{
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_)
	{
		WorldGenLevel level = p_159749_.level();
		BlockPos blockPos = p_159749_.origin().offset(-7, 0, -7);
		RandomSource random = p_159749_.random();
		StructureTemplateManager manager = level.getLevel().getStructureManager();
		ResourceLocation location = new ResourceLocation(String.format("%s:features/deepabyss/coral_tree_%d", BeyondtheAbyss.MODID, random.nextInt(3) + 1));
		StructureTemplate template = manager.getOrCreate(location);
		BlockState blockState = level.getBlockState(blockPos);
		boolean isAbyssalith = level.getBlockState(blockPos.below()).is(BTABlocks.ABYSSALITH.get()) 
				&& level.getBlockState(blockPos.below().offset(-1, 0, 0)).is(BTABlocks.ABYSSALITH.get()) 
				&& level.getBlockState(blockPos.below().offset(1, 0, 0)).is(BTABlocks.ABYSSALITH.get())
				&& level.getBlockState(blockPos.below().offset(0, 0, -1)).is(BTABlocks.ABYSSALITH.get())
				&& level.getBlockState(blockPos.below().offset(0, 0, 1)).is(BTABlocks.ABYSSALITH.get());
		if(blockState.is(Blocks.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER) && isAbyssalith && blockPos.getY() > -380 && blockPos.getY() <= -320 && random.nextFloat() < 0.05F) 
		{
	    	StructurePlaceSettings settings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE);
	    	template.placeInWorld(level, blockPos, blockPos, settings, random, 3);
			return true;
		}
		else
		{
			return false;
		}
	}
}
