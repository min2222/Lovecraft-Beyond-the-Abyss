package com.min01.beyondtheabyss.animation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector3f;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyframePlayerAnimations 
{
	public static void animate(PlayerModel<?> pModel, AnimationDefinition pAnimationDefinition, long pAccumulatedTime, float pScale, Vector3f pAnimationVecCache) 
	{
		float f = getElapsedSeconds(pAnimationDefinition, pAccumulatedTime);
		for(Map.Entry<String, List<AnimationChannel>> entry : pAnimationDefinition.boneAnimations().entrySet())
		{
			Optional<Pair<ModelPart, ModelPart>> optional = PlayerAnimations.getAnyDescendantWithName(entry.getKey());
			List<AnimationChannel> list = entry.getValue();
			optional.ifPresent(pair ->
			{
				list.forEach(channel ->
				{
					Keyframe[] akeyframe = channel.keyframes();
					int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, (index) ->
					{
						return f <= akeyframe[index].timestamp();
					}) - 1);
					int j = Math.min(akeyframe.length - 1, i + 1);
					Keyframe keyframe = akeyframe[i];
					Keyframe keyframe1 = akeyframe[j];
					float f1 = f - keyframe.timestamp();
					float f2;
					if(j != i) 
					{
						f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
					} 
					else
					{
						f2 = 0.0F;
					}
					keyframe1.interpolation().apply(pAnimationVecCache, f2, akeyframe, i, j, pScale);
					channel.target().apply(pair.getLeft(), pAnimationVecCache);
					channel.target().apply(pair.getRight(), pAnimationVecCache);
				});
			});
		}
	}

	private static float getElapsedSeconds(AnimationDefinition pAnimationDefinition, long pAccumulatedTime) 
	{
		float f = (float) pAccumulatedTime / 1000.0F;
		return pAnimationDefinition.looping() ? f % pAnimationDefinition.lengthInSeconds() : f;
	}
}
