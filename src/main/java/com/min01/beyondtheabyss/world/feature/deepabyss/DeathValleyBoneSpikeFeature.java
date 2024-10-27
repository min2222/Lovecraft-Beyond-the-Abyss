package com.min01.beyondtheabyss.world.feature.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class DeathValleyBoneSpikeFeature extends Feature<ListFeatureConfiguration>
{
	public DeathValleyBoneSpikeFeature(Codec<ListFeatureConfiguration> p_65786_) 
	{
		super(p_65786_);
	}

	@Override
	public boolean place(FeaturePlaceContext<ListFeatureConfiguration> p_159749_)
	{
		WorldGenLevel level = p_159749_.level();
		BlockPos pos = p_159749_.origin();
		RandomSource random = p_159749_.random();
		boolean canPlace1 = level.getBlockState(pos.offset(1, 0, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, 1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, -1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, 0, -2)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, 1, -2)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, -1, -2)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, 0, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, 1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, -1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, 0, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, 1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, -1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 0, -3)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 1, -3)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, -1, -3)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 0, -4)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 1, -4)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, -1, -4)).is(BTABlocks.COMPACT_ROT_SOIL.get());
		boolean canPlace2 = level.getBlockState(pos.offset(1, 0, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, 1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, -1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, 0, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, 1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(2, -1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 0, -2)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 1, -2)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, -1, -2)).is(BTABlocks.COMPACT_ROT_SOIL.get());
		boolean canPlace3 = level.getBlockState(pos.offset(1, 0, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, 1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(1, -1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, 0, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, 1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, -1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, 0, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, 1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(1, -1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get());
		boolean canPlace4 = level.getBlockState(pos.offset(-1, 0, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, 1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, -1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, 0, 2)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, 1, 2)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, -1, 2)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, 0, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, 1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, -1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, 0, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, 1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, -1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 0, 3)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 1, 3)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, -1, 3)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 0, 4)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 1, 4)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, -1, 4)).is(BTABlocks.COMPACT_ROT_SOIL.get());
		boolean canPlace5 = level.getBlockState(pos.offset(-1, 0, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, 1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, -1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, 0, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, 1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-2, -1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 0, 2)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, 1, 2)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(0, -1, 2)).is(BTABlocks.COMPACT_ROT_SOIL.get());
		boolean canPlace6 = level.getBlockState(pos.offset(-1, 0, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, 1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get()) 
				&& level.getBlockState(pos.offset(-1, -1, 0)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, 0, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, 1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, -1, 1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, 0, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, 1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get())
				&& level.getBlockState(pos.offset(-1, -1, -1)).is(BTABlocks.COMPACT_ROT_SOIL.get());
		if(canPlace1 || canPlace2 || canPlace3)
		{
			if(random.nextFloat() <= 0.008F)
			{
				ResourceLocation location = p_159749_.config().structures.get(0);
				StructureTemplateManager manager = level.getLevel().getStructureManager();
				StructureTemplate template = manager.getOrCreate(location);
		    	StructurePlaceSettings settings = (new StructurePlaceSettings()).setKeepLiquids(false);
		    	template.placeInWorld(level, pos, pos, settings, random, 3);
				return true;
			}
		}
		if(canPlace4 || canPlace5 || canPlace6)
		{
			if(random.nextFloat() <= 0.008F)
			{
				ResourceLocation location = p_159749_.config().structures.get(0);
				StructureTemplateManager manager = level.getLevel().getStructureManager();
				StructureTemplate template = manager.getOrCreate(location);
		    	StructurePlaceSettings settings = (new StructurePlaceSettings()).setRotation(Rotation.CLOCKWISE_180).setKeepLiquids(false);
		    	template.placeInWorld(level, pos, pos, settings, random, 3);
				return true;
			}
		}
		return false;
	}
}
