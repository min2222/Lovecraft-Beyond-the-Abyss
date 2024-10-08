package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DeathValleyCoralTreeFeature extends Feature<ListFeatureConfiguration>
{
	public DeathValleyCoralTreeFeature(Codec<ListFeatureConfiguration> p_65786_) 
	{
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<ListFeatureConfiguration> p_159749_)
	{
		WorldGenLevel level = p_159749_.level();
		RandomSource random = p_159749_.random();
		int number = random.nextInt(5) + 1;
		BlockPos[] posArray = new BlockPos[] { p_159749_.origin().offset(-7, 0, 4), p_159749_.origin().offset(-7, 0, 7), p_159749_.origin().offset(-9, 0, 11), p_159749_.origin().offset(-11, 0, 7), p_159749_.origin().offset(-6, 0, 9) };
		BlockPos pos = posArray[number - 1];
		if(level.getBlockState(pos.below()).is(BTABlocks.ROT_SOIL.get()) && level.getBlockState(pos.above()).is(Blocks.WATER))
		{
			if(random.nextFloat() <= 0.0035F)
			{
				ResourceLocation location = p_159749_.config().structures.get(number - 1);
				StructureTemplateManager manager = level.getLevel().getStructureManager();
				StructureTemplate template = manager.getOrCreate(location);
		    	StructurePlaceSettings settings = (new StructurePlaceSettings()).setMirror(Mirror.values()[random.nextInt(2)]).setRotation(Rotation.getRandom(random)).setKeepLiquids(false);
		    	template.placeInWorld(level, pos, pos, settings, random, 3);
				return true;
			}
		}
		return false;
	}
}
