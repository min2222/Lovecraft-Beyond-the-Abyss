package com.min01.beyondtheabyss.world.feature;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ListFeatureConfiguration implements FeatureConfiguration
{
	public static final Codec<ListFeatureConfiguration> CODEC = RecordCodecBuilder.create((p_159816_) ->
	{
		return p_159816_.group(ResourceLocation.CODEC.listOf().fieldOf("structures").forGetter((p_159830_) ->
		{
			return p_159830_.structures;
		})).apply(p_159816_, ListFeatureConfiguration::new);
	});
	   
	public final List<ResourceLocation> structures;
	
	public ListFeatureConfiguration(List<ResourceLocation> structures)
	{
		this.structures = structures;
	}
}
