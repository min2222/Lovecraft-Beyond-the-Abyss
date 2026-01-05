package com.min01.beyondtheabyss.world.feature;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class DirectionBlockStateConfiguration implements FeatureConfiguration
{
	public static final Codec<DirectionBlockStateConfiguration> CODEC = RecordCodecBuilder.create(builder -> 
	{
		return builder.group(Direction.CODEC.listOf().fieldOf("directions").forGetter(t ->
		{
			return t.directions;
		}), BlockState.CODEC.fieldOf("check").forGetter(t ->
		{
			return t.check;
		}), BlockState.CODEC.fieldOf("state").forGetter(t ->
		{
			return t.state;
		})).apply(builder, DirectionBlockStateConfiguration::new);
	});

	public final List<Direction> directions;
	public final BlockState check;
	public final BlockState state;

	public DirectionBlockStateConfiguration(List<Direction> directions, BlockState check, BlockState state)
	{
		this.directions = directions;
		this.check = check;
		this.state = state;
	}
}
