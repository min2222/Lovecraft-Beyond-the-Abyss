package com.min01.beyondtheabyss.world.feature;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ListFeatureConfiguration implements FeatureConfiguration
{
	public static final Codec<ListFeatureConfiguration> CODEC = RecordCodecBuilder.create(builder ->
	{
		return builder.group(ResourceLocation.CODEC.listOf().fieldOf("structures").forGetter(t ->
		{
			return t.structures;
		})).apply(builder, ListFeatureConfiguration::new);
	});
	   
	public final List<ResourceLocation> structures;
	
	public ListFeatureConfiguration(List<ResourceLocation> structures)
	{
		this.structures = structures;
	}
}
