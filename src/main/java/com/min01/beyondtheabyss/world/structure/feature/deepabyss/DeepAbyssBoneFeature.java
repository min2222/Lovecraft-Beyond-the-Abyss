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

public class DeepAbyssBoneFeature extends Feature<NoneFeatureConfiguration>
{
	public DeepAbyssBoneFeature(Codec<NoneFeatureConfiguration> p_65786_)
	{
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) 
	{
		WorldGenLevel level = p_159749_.level();
		BlockPos blockPos = p_159749_.origin().offset(-3, 0, -3);
		RandomSource random = p_159749_.random();
		StructureTemplateManager manager = level.getLevel().getStructureManager();
		ResourceLocation bonePiles = new ResourceLocation(String.format("%s:features/deepabyss/bone_piles_%d", BeyondtheAbyss.MODID, random.nextInt(2) + 1));
		ResourceLocation bonePillars = new ResourceLocation(String.format("%s:features/deepabyss/bone_pillars_%d", BeyondtheAbyss.MODID, random.nextInt(3) + 1));
		ResourceLocation fossils = new ResourceLocation(String.format("%s:features/deepabyss/fossil_%d", BeyondtheAbyss.MODID, random.nextInt(6) + 1));
		ResourceLocation[] location = new ResourceLocation[] {bonePiles, bonePillars, fossils};
		StructureTemplate template = manager.getOrCreate(location[random.nextInt(3)]);
		BlockState blockState = level.getBlockState(blockPos);
		boolean canPlace = level.getBlockState(blockPos.below()).is(BTABlocks.ROT_SOIL.get()) 
				&& level.getBlockState(blockPos.below().offset(-5, 0, 0)).is(BTABlocks.ROT_SOIL.get()) 
				&& level.getBlockState(blockPos.below().offset(5, 0, 0)).is(BTABlocks.ROT_SOIL.get())
				&& level.getBlockState(blockPos.below().offset(0, 0, -5)).is(BTABlocks.ROT_SOIL.get())
				&& level.getBlockState(blockPos.below().offset(0, 0, 5)).is(BTABlocks.ROT_SOIL.get());
		if(blockState.is(Blocks.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER) && canPlace && blockPos.getY() > -380 && blockPos.getY() <= -320 && random.nextFloat() < 0.1F) 
		{
	    	StructurePlaceSettings settings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.getRandom(random));
	    	template.placeInWorld(level, blockPos, blockPos, settings, random, 3);
			return true;
		}
		else
		{
			return false;
		}
	}
}
