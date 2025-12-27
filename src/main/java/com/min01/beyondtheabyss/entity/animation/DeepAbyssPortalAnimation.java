package com.min01.beyondtheabyss.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DeepAbyssPortalAnimation
{
	public static final AnimationDefinition PORTAL_IDLE = AnimationDefinition.Builder.withLength(1.0F).looping()
			.addAnimation("inner_portal", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 360.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
	
		public static final AnimationDefinition PORTAL_OPEN = AnimationDefinition.Builder.withLength(7.0F)
			.addAnimation("door1", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(5.0F, KeyframeAnimations.posVec(0.0F, -36.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("door2", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(5.0F, KeyframeAnimations.posVec(20.4012F, 10.6202F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("door3", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7917F, KeyframeAnimations.posVec(-3.23F, 1.68F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(5.0F, KeyframeAnimations.posVec(-20.4012F, 10.6202F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}
