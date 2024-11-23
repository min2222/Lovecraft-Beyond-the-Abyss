package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DeathValleyFossilFeature extends Feature<ListFeatureConfiguration>
{
	public DeathValleyFossilFeature(Codec<ListFeatureConfiguration> p_65786_) 
	{
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<ListFeatureConfiguration> p_159749_)
	{
		WorldGenLevel level = p_159749_.level();
		BlockPos pos = p_159749_.origin();
		RandomSource random = p_159749_.random();
		if(level.getBlockState(pos.below()).is(BTABlocks.ROT_SOIL.get()))
		{
			if(random.nextFloat() <= 0.3F)
			{
				ResourceLocation location = p_159749_.config().structures.get(random.nextInt(3));
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
