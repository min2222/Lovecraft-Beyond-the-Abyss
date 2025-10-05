package com.min01.beyondtheabyss.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class PlayerAnimation
{
	public static class SkeletalGunbladeAnimation {
		public static final AnimationDefinition SWING = AnimationDefinition.Builder.withLength(0.9167F)
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(10.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(10.0F, -5.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.posVec(1.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.04F, 0.0F, 1.93F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-10.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(15.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(15.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.posVec(1.0F, 0.0F, 2.19F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-79.0899F, 20.715F, 18.501F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-47.4339F, 8.1096F, 1.7591F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(35.3149F, 8.816F, -11.3779F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(35.3149F, 8.816F, -11.3779F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(92.1887F, 68.4941F, 164.7688F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(-36.8205F, 54.8284F, 13.837F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-76.6379F, -21.4894F, -17.5835F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-76.6379F, -21.4894F, -17.5835F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.posVec(2.0F, 0.0F, -1.48F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.SCALE, 
				new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1.0F, 0.9F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.7F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.625F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 1.86F, -3.13F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.72F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 2.0F, 1.88F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.875F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.75F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.55F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition CHARGE = AnimationDefinition.Builder.withLength(3.0F)
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-77.56F, 22.78F, 2.59F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8542F, KeyframeAnimations.degreeVec(-77.04F, 21.34F, 3.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(-77.47F, 21.69F, 2.01F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8958F, KeyframeAnimations.degreeVec(-78.04F, 21.17F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-77.92F, 22.3F, 3.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9375F, KeyframeAnimations.degreeVec(-77.92F, 21.68F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-77.16F, 22.02F, 2.21F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9792F, KeyframeAnimations.degreeVec(-78.71F, 21.51F, 2.22F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(-78.09F, 21.14F, 2.21F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0208F, KeyframeAnimations.degreeVec(-77.53F, 21.65F, 1.87F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-77.96F, 21.23F, 3.77F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0625F, KeyframeAnimations.degreeVec(-78.34F, 22.24F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-78.24F, 22.91F, 2.68F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1042F, KeyframeAnimations.degreeVec(-78.85F, 21.97F, 2.38F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(-78.82F, 21.87F, 3.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1458F, KeyframeAnimations.degreeVec(-77.98F, 21.53F, 2.35F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-78.51F, 22.69F, 2.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1875F, KeyframeAnimations.degreeVec(-77.75F, 22.48F, 3.52F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-77.84F, 22.57F, 1.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2292F, KeyframeAnimations.degreeVec(-77.56F, 22.44F, 2.55F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(-77.29F, 21.51F, 3.52F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2708F, KeyframeAnimations.degreeVec(-78.47F, 22.36F, 2.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-78.53F, 22.62F, 2.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3125F, KeyframeAnimations.degreeVec(-77.53F, 21.93F, 2.93F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-76.94F, 21.25F, 1.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3542F, KeyframeAnimations.degreeVec(-77.31F, 22.31F, 1.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(-78.68F, 22.08F, 3.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3958F, KeyframeAnimations.degreeVec(-77.02F, 22.23F, 2.23F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-77.03F, 22.79F, 2.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4375F, KeyframeAnimations.degreeVec(-77.34F, 22.41F, 2.32F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-77.26F, 22.11F, 3.28F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4792F, KeyframeAnimations.degreeVec(-78.2F, 22.73F, 3.49F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(-77.23F, 21.72F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5208F, KeyframeAnimations.degreeVec(-78.25F, 22.6F, 2.06F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-77.4F, 21.69F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5625F, KeyframeAnimations.degreeVec(-78.57F, 21.69F, 3.13F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-77.8F, 22.72F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6042F, KeyframeAnimations.degreeVec(-78.56F, 21.54F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(-78.23F, 22.0F, 3.21F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6458F, KeyframeAnimations.degreeVec(-78.29F, 21.92F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-77.55F, 21.78F, 3.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6875F, KeyframeAnimations.degreeVec(-77.63F, 21.8F, 2.93F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-78.01F, 21.81F, 3.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7292F, KeyframeAnimations.degreeVec(-77.97F, 21.65F, 2.83F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(-77.5F, 22.7F, 3.04F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7708F, KeyframeAnimations.degreeVec(-77.13F, 21.47F, 3.51F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-78.2F, 21.49F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8125F, KeyframeAnimations.degreeVec(-77.34F, 21.27F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-78.31F, 22.43F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8542F, KeyframeAnimations.degreeVec(-78.15F, 22.64F, 2.75F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(-77.4F, 22.14F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8958F, KeyframeAnimations.degreeVec(-77.6F, 21.84F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-77.64F, 21.09F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9375F, KeyframeAnimations.degreeVec(-78.0F, 21.77F, 2.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-77.52F, 21.71F, 2.23F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9792F, KeyframeAnimations.degreeVec(-77.9F, 21.72F, 2.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(-77.25F, 21.82F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0208F, KeyframeAnimations.degreeVec(-77.89F, 22.26F, 2.58F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-77.9F, 22.01F, 3.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0625F, KeyframeAnimations.degreeVec(-78.31F, 22.51F, 2.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-77.03F, 22.11F, 3.37F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1042F, KeyframeAnimations.degreeVec(-78.13F, 21.76F, 2.45F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(-77.95F, 22.25F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1458F, KeyframeAnimations.degreeVec(-77.92F, 22.22F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-77.88F, 21.6F, 1.86F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1875F, KeyframeAnimations.degreeVec(-77.36F, 22.71F, 3.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-77.71F, 22.46F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2292F, KeyframeAnimations.degreeVec(-78.51F, 21.62F, 2.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(-78.69F, 21.54F, 2.94F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2708F, KeyframeAnimations.degreeVec(-77.63F, 22.85F, 1.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-77.92F, 21.52F, 2.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3125F, KeyframeAnimations.degreeVec(-78.77F, 21.95F, 2.73F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-77.43F, 22.54F, 2.1F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3542F, KeyframeAnimations.degreeVec(-78.19F, 21.96F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(-77.82F, 22.06F, 3.15F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3958F, KeyframeAnimations.degreeVec(-77.87F, 21.62F, 2.83F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-77.72F, 21.41F, 3.01F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4375F, KeyframeAnimations.degreeVec(-77.38F, 21.84F, 2.68F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-78.35F, 21.57F, 1.94F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4792F, KeyframeAnimations.degreeVec(-77.72F, 21.22F, 3.15F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(-78.36F, 22.34F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5208F, KeyframeAnimations.degreeVec(-78.66F, 21.47F, 3.01F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-77.93F, 22.22F, 3.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5625F, KeyframeAnimations.degreeVec(-78.19F, 22.56F, 2.98F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(-77.61F, 22.61F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6042F, KeyframeAnimations.degreeVec(-78.13F, 22.34F, 2.63F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(-78.12F, 22.9F, 3.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6458F, KeyframeAnimations.degreeVec(-78.5F, 22.37F, 2.63F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-78.09F, 22.61F, 2.25F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6875F, KeyframeAnimations.degreeVec(-78.06F, 21.39F, 2.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7083F, KeyframeAnimations.degreeVec(-78.63F, 22.77F, 2.68F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7292F, KeyframeAnimations.degreeVec(-77.46F, 21.38F, 3.13F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(-78.25F, 21.46F, 1.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7708F, KeyframeAnimations.degreeVec(-78.24F, 21.62F, 3.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-78.32F, 21.52F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8125F, KeyframeAnimations.degreeVec(-77.13F, 22.7F, 2.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8333F, KeyframeAnimations.degreeVec(-78.74F, 22.03F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8542F, KeyframeAnimations.degreeVec(-77.52F, 21.37F, 2.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.875F, KeyframeAnimations.degreeVec(-77.67F, 21.47F, 2.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8958F, KeyframeAnimations.degreeVec(-78.88F, 22.69F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-78.5F, 22.7F, 2.51F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9375F, KeyframeAnimations.degreeVec(-77.25F, 21.08F, 3.77F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9583F, KeyframeAnimations.degreeVec(-77.16F, 22.88F, 3.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9792F, KeyframeAnimations.degreeVec(-78.88F, 22.22F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(3.0F, KeyframeAnimations.degreeVec(-77.7F, 21.49F, 3.28F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5833F, KeyframeAnimations.posVec(-1.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-86.9225F, 0.5577F, -90.0796F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-85.81F, 2.14F, -88.15F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8542F, KeyframeAnimations.degreeVec(-85.17F, 1.98F, -88.96F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(-85.53F, 1.87F, -88.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8958F, KeyframeAnimations.degreeVec(-85.01F, 2.3F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-84.95F, 2.49F, -88.96F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9375F, KeyframeAnimations.degreeVec(-84.95F, 1.66F, -88.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-85.51F, 2.39F, -88.59F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9792F, KeyframeAnimations.degreeVec(-84.99F, 2.47F, -88.21F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(-85.12F, 1.93F, -88.45F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0208F, KeyframeAnimations.degreeVec(-85.77F, 2.2F, -88.95F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-84.95F, 2.01F, -88.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0625F, KeyframeAnimations.degreeVec(-85.1F, 1.69F, -88.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-85.39F, 1.76F, -88.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1042F, KeyframeAnimations.degreeVec(-85.18F, 2.18F, -88.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(-85.69F, 1.74F, -88.05F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1458F, KeyframeAnimations.degreeVec(-85.2F, 2.48F, -88.49F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-85.54F, 1.66F, -88.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1875F, KeyframeAnimations.degreeVec(-84.94F, 2.13F, -88.16F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-85.41F, 1.85F, -88.64F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2292F, KeyframeAnimations.degreeVec(-85.1F, 2.03F, -88.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(-85.05F, 2.55F, -88.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2708F, KeyframeAnimations.degreeVec(-84.91F, 2.44F, -88.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-85.41F, 2.27F, -88.14F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3125F, KeyframeAnimations.degreeVec(-85.45F, 2.1F, -88.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-85.1F, 1.82F, -88.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3542F, KeyframeAnimations.degreeVec(-84.94F, 2.62F, -88.65F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(-85.64F, 1.8F, -88.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3958F, KeyframeAnimations.degreeVec(-85.59F, 1.81F, -88.05F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-85.03F, 2.25F, -88.75F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4375F, KeyframeAnimations.degreeVec(-85.24F, 1.92F, -88.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-85.49F, 2.39F, -88.72F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4792F, KeyframeAnimations.degreeVec(-85.78F, 2.6F, -88.77F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(-85.78F, 2.27F, -88.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5208F, KeyframeAnimations.degreeVec(-85.22F, 1.74F, -88.85F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-84.9F, 2.44F, -88.09F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5625F, KeyframeAnimations.degreeVec(-85.75F, 2.54F, -88.35F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-85.13F, 2.54F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6042F, KeyframeAnimations.degreeVec(-85.44F, 1.79F, -88.87F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(-85.22F, 2.33F, -88.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6458F, KeyframeAnimations.degreeVec(-85.0F, 2.63F, -88.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-85.41F, 2.34F, -88.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6875F, KeyframeAnimations.degreeVec(-85.54F, 1.95F, -88.35F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-85.74F, 2.13F, -88.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7292F, KeyframeAnimations.degreeVec(-85.63F, 2.36F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(-85.13F, 2.37F, -88.15F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7708F, KeyframeAnimations.degreeVec(-85.54F, 1.89F, -88.56F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-85.4F, 2.49F, -88.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8125F, KeyframeAnimations.degreeVec(-85.42F, 2.58F, -88.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-85.1F, 2.16F, -88.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8542F, KeyframeAnimations.degreeVec(-85.46F, 1.83F, -88.04F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(-85.34F, 2.21F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8958F, KeyframeAnimations.degreeVec(-85.39F, 1.95F, -88.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-85.49F, 2.17F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9375F, KeyframeAnimations.degreeVec(-85.59F, 2.47F, -88.6F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-85.35F, 2.0F, -88.56F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9792F, KeyframeAnimations.degreeVec(-85.48F, 2.03F, -88.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(-85.33F, 2.21F, -88.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0208F, KeyframeAnimations.degreeVec(-84.99F, 2.31F, -88.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-85.57F, 1.87F, -88.14F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0625F, KeyframeAnimations.degreeVec(-85.31F, 2.12F, -88.47F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-85.05F, 2.02F, -88.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1042F, KeyframeAnimations.degreeVec(-85.3F, 2.3F, -88.68F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(-85.4F, 2.04F, -88.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1458F, KeyframeAnimations.degreeVec(-85.57F, 2.04F, -88.35F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-85.49F, 2.21F, -88.09F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1875F, KeyframeAnimations.degreeVec(-85.29F, 2.31F, -88.85F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-85.42F, 2.35F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2292F, KeyframeAnimations.degreeVec(-85.56F, 2.18F, -88.6F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(-85.31F, 1.79F, -88.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2708F, KeyframeAnimations.degreeVec(-85.5F, 2.41F, -88.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-85.52F, 2.2F, -88.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3125F, KeyframeAnimations.degreeVec(-85.15F, 2.08F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-85.54F, 2.28F, -88.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3542F, KeyframeAnimations.degreeVec(-85.17F, 1.96F, -88.63F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(-85.27F, 1.9F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3958F, KeyframeAnimations.degreeVec(-85.75F, 1.84F, -88.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-85.58F, 2.45F, -88.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4375F, KeyframeAnimations.degreeVec(-85.52F, 1.9F, -88.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-85.0F, 2.38F, -88.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4792F, KeyframeAnimations.degreeVec(-85.74F, 2.52F, -88.56F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(-85.55F, 1.71F, -88.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5208F, KeyframeAnimations.degreeVec(-85.03F, 2.33F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-85.37F, 1.77F, -88.29F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5625F, KeyframeAnimations.degreeVec(-85.58F, 2.15F, -88.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(-85.01F, 2.29F, -88.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6042F, KeyframeAnimations.degreeVec(-85.16F, 1.86F, -88.92F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(-85.29F, 1.7F, -88.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6458F, KeyframeAnimations.degreeVec(-85.49F, 2.54F, -88.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-85.5F, 2.41F, -88.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6875F, KeyframeAnimations.degreeVec(-85.52F, 1.88F, -88.51F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7083F, KeyframeAnimations.degreeVec(-85.65F, 2.5F, -88.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7292F, KeyframeAnimations.degreeVec(-85.28F, 1.94F, -88.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(-85.24F, 2.33F, -88.22F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7708F, KeyframeAnimations.degreeVec(-85.27F, 2.36F, -88.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-84.94F, 1.67F, -88.6F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8125F, KeyframeAnimations.degreeVec(-85.51F, 1.98F, -88.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8333F, KeyframeAnimations.degreeVec(-85.43F, 1.97F, -88.45F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8542F, KeyframeAnimations.degreeVec(-85.11F, 2.39F, -88.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.875F, KeyframeAnimations.degreeVec(-85.19F, 1.85F, -88.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8958F, KeyframeAnimations.degreeVec(-84.94F, 1.91F, -88.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-85.12F, 2.1F, -88.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9375F, KeyframeAnimations.degreeVec(-85.2F, 2.47F, -88.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9583F, KeyframeAnimations.degreeVec(-85.83F, 2.09F, -89.05F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9792F, KeyframeAnimations.degreeVec(-85.18F, 1.78F, -88.16F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(3.0F, KeyframeAnimations.degreeVec(-85.32F, 1.76F, -88.34F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.08F, -0.54F, 0.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0.32F, -2.14F, 1.65F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.67F, -4.48F, 2.09F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.75F, KeyframeAnimations.degreeVec(1.01F, -6.82F, 2.23F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7917F, KeyframeAnimations.degreeVec(1.25F, -8.42F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.33F, -8.96F, 2.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8542F, KeyframeAnimations.degreeVec(1.91F, -8.95F, 2.05F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(1.96F, -8.49F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8958F, KeyframeAnimations.degreeVec(1.73F, -8.52F, 2.29F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(1.65F, -8.91F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9375F, KeyframeAnimations.degreeVec(1.88F, -8.79F, 2.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(1.84F, -8.9F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9792F, KeyframeAnimations.degreeVec(1.32F, -9.16F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.13F, -9.1F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0208F, KeyframeAnimations.degreeVec(1.76F, -8.39F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(1.23F, -9.28F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0625F, KeyframeAnimations.degreeVec(1.33F, -9.19F, 2.28F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(1.44F, -8.48F, 2.57F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1042F, KeyframeAnimations.degreeVec(1.86F, -9.01F, 2.49F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(1.8F, -8.66F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1458F, KeyframeAnimations.degreeVec(1.59F, -8.61F, 2.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(1.97F, -9.25F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1875F, KeyframeAnimations.degreeVec(1.42F, -9.26F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(1.98F, -8.98F, 2.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2292F, KeyframeAnimations.degreeVec(1.19F, -8.79F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(1.91F, -9.02F, 2.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2708F, KeyframeAnimations.degreeVec(1.8F, -9.1F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(1.53F, -9.27F, 2.16F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3125F, KeyframeAnimations.degreeVec(1.61F, -8.88F, 3.01F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.91F, -8.92F, 2.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3542F, KeyframeAnimations.degreeVec(2.1F, -9.54F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(1.97F, -8.7F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3958F, KeyframeAnimations.degreeVec(1.27F, -8.67F, 2.64F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(1.93F, -8.96F, 2.98F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4375F, KeyframeAnimations.degreeVec(2.03F, -9.3F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(1.72F, -9.33F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4792F, KeyframeAnimations.degreeVec(1.69F, -9.48F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.79F, -9.06F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5208F, KeyframeAnimations.degreeVec(1.32F, -9.39F, 2.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(1.83F, -9.44F, 2.32F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5625F, KeyframeAnimations.degreeVec(1.3F, -9.1F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(1.7F, -9.16F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6042F, KeyframeAnimations.degreeVec(1.53F, -9.46F, 2.6F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(1.91F, -8.93F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6458F, KeyframeAnimations.degreeVec(1.29F, -8.92F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.29F, -9.1F, 2.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6875F, KeyframeAnimations.degreeVec(1.83F, -8.88F, 2.47F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(1.83F, -9.31F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7292F, KeyframeAnimations.degreeVec(1.2F, -9.22F, 2.45F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(1.6F, -9.03F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7708F, KeyframeAnimations.degreeVec(1.79F, -9.1F, 2.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(1.55F, -8.93F, 2.25F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8125F, KeyframeAnimations.degreeVec(1.41F, -9.4F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1.52F, -8.8F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8542F, KeyframeAnimations.degreeVec(1.52F, -9.05F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(1.68F, -9.35F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8958F, KeyframeAnimations.degreeVec(1.74F, -8.89F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(1.65F, -8.48F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9375F, KeyframeAnimations.degreeVec(1.43F, -8.78F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(1.57F, -8.54F, 2.19F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9792F, KeyframeAnimations.degreeVec(1.21F, -8.58F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.35F, -8.95F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0208F, KeyframeAnimations.degreeVec(1.09F, -8.85F, 2.72F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(1.33F, -8.83F, 2.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0625F, KeyframeAnimations.degreeVec(1.94F, -8.95F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(1.66F, -8.48F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1042F, KeyframeAnimations.degreeVec(1.44F, -9.21F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(1.37F, -8.69F, 2.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1458F, KeyframeAnimations.degreeVec(1.6F, -9.03F, 2.59F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(1.79F, -8.83F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1875F, KeyframeAnimations.degreeVec(1.8F, -8.86F, 1.95F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(1.61F, -9.1F, 2.64F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2292F, KeyframeAnimations.degreeVec(1.31F, -8.66F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(1.63F, -8.49F, 2.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2708F, KeyframeAnimations.degreeVec(1.75F, -9.16F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(1.96F, -8.36F, 2.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3125F, KeyframeAnimations.degreeVec(1.73F, -9.01F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(1.62F, -8.25F, 2.32F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3542F, KeyframeAnimations.degreeVec(1.81F, -8.97F, 2.6F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(1.57F, -8.58F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3958F, KeyframeAnimations.degreeVec(1.73F, -8.7F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(1.18F, -8.28F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4375F, KeyframeAnimations.degreeVec(1.35F, -8.79F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(1.56F, -8.32F, 2.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4792F, KeyframeAnimations.degreeVec(1.88F, -8.28F, 2.02F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(1.5F, -8.99F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5208F, KeyframeAnimations.degreeVec(1.31F, -8.83F, 2.38F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(1.84F, -8.06F, 2.29F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5625F, KeyframeAnimations.degreeVec(1.66F, -8.11F, 2.28F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(1.19F, -8.35F, 2.91F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6042F, KeyframeAnimations.degreeVec(1.27F, -8.12F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(1.44F, -8.67F, 2.03F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6458F, KeyframeAnimations.degreeVec(1.88F, -8.08F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6667F, KeyframeAnimations.degreeVec(1.32F, -8.37F, 2.57F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6875F, KeyframeAnimations.degreeVec(1.23F, -8.13F, 2.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7083F, KeyframeAnimations.degreeVec(1.39F, -8.65F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7292F, KeyframeAnimations.degreeVec(1.21F, -8.08F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(1.45F, -8.73F, 2.87F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7708F, KeyframeAnimations.degreeVec(1.32F, -8.81F, 2.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(1.28F, -8.32F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8125F, KeyframeAnimations.degreeVec(1.78F, -8.9F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8333F, KeyframeAnimations.degreeVec(1.24F, -8.66F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8542F, KeyframeAnimations.degreeVec(1.68F, -8.64F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.875F, KeyframeAnimations.degreeVec(1.53F, -8.54F, 2.59F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8958F, KeyframeAnimations.degreeVec(1.62F, -8.37F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9167F, KeyframeAnimations.degreeVec(1.8F, -8.2F, 2.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9375F, KeyframeAnimations.degreeVec(1.72F, -8.85F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9583F, KeyframeAnimations.degreeVec(1.2F, -8.76F, 2.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9792F, KeyframeAnimations.degreeVec(1.8F, -8.95F, 1.95F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(3.0F, KeyframeAnimations.degreeVec(1.82F, -8.74F, 1.93F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0.34F, -0.91F, -0.1F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(1.86F, -5.11F, -0.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(2.38F, -6.66F, -0.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.degreeVec(2.55F, -7.27F, -0.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.2083F, KeyframeAnimations.degreeVec(2.58F, -7.49F, -0.56F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.25F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.92F, -8.42F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8542F, KeyframeAnimations.degreeVec(1.44F, -8.69F, 2.38F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(1.11F, -8.73F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8958F, KeyframeAnimations.degreeVec(1.54F, -8.87F, 2.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(1.79F, -8.64F, 2.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9375F, KeyframeAnimations.degreeVec(1.1F, -8.5F, 2.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(1.14F, -8.38F, 2.56F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9792F, KeyframeAnimations.degreeVec(1.7F, -8.07F, 2.72F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.12F, -8.9F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0208F, KeyframeAnimations.degreeVec(1.53F, -8.12F, 2.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(1.15F, -8.73F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0625F, KeyframeAnimations.degreeVec(1.92F, -8.35F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(1.8F, -8.16F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1042F, KeyframeAnimations.degreeVec(1.72F, -8.51F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(1.16F, -8.73F, 2.37F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1458F, KeyframeAnimations.degreeVec(0.99F, -8.44F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(1.76F, -8.92F, 2.52F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1875F, KeyframeAnimations.degreeVec(1.85F, -8.57F, 2.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(1.59F, -8.35F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2292F, KeyframeAnimations.degreeVec(1.76F, -8.9F, 2.19F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(1.64F, -9.01F, 2.48F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2708F, KeyframeAnimations.degreeVec(1.93F, -8.6F, 2.73F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(1.72F, -8.19F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3125F, KeyframeAnimations.degreeVec(1.76F, -8.88F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.69F, -8.19F, 2.94F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3542F, KeyframeAnimations.degreeVec(1.07F, -9.02F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(1.86F, -8.68F, 2.75F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3958F, KeyframeAnimations.degreeVec(1.19F, -8.39F, 3.07F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(1.9F, -8.38F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4375F, KeyframeAnimations.degreeVec(1.6F, -8.43F, 2.73F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(1.04F, -8.43F, 2.22F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4792F, KeyframeAnimations.degreeVec(1.52F, -8.59F, 2.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.15F, -8.76F, 2.55F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5208F, KeyframeAnimations.degreeVec(1.52F, -9.0F, 2.73F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(1.83F, -8.19F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5625F, KeyframeAnimations.degreeVec(1.57F, -8.35F, 2.91F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(1.58F, -8.87F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6042F, KeyframeAnimations.degreeVec(1.56F, -9.0F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(1.76F, -8.79F, 2.91F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6458F, KeyframeAnimations.degreeVec(1.91F, -8.34F, 2.48F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.55F, -8.74F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6875F, KeyframeAnimations.degreeVec(1.67F, -8.66F, 2.68F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(1.52F, -8.79F, 2.29F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7292F, KeyframeAnimations.degreeVec(1.56F, -8.75F, 3.06F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(1.84F, -8.35F, 2.45F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7708F, KeyframeAnimations.degreeVec(1.46F, -8.52F, 2.95F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(1.75F, -8.83F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8125F, KeyframeAnimations.degreeVec(1.59F, -8.83F, 2.65F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1.67F, -9.03F, 2.37F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8542F, KeyframeAnimations.degreeVec(1.48F, -8.46F, 2.72F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(1.53F, -8.55F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8958F, KeyframeAnimations.degreeVec(1.26F, -8.63F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(1.24F, -8.2F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9375F, KeyframeAnimations.degreeVec(1.42F, -8.81F, 2.48F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(1.64F, -8.57F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9792F, KeyframeAnimations.degreeVec(1.25F, -8.39F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.81F, -8.35F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0208F, KeyframeAnimations.degreeVec(1.7F, -8.56F, 2.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(1.11F, -8.69F, 2.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0625F, KeyframeAnimations.degreeVec(1.23F, -8.29F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(1.17F, -8.82F, 2.91F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1042F, KeyframeAnimations.degreeVec(1.69F, -8.64F, 2.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(1.53F, -8.51F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1458F, KeyframeAnimations.degreeVec(1.0F, -8.24F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(1.53F, -8.54F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1875F, KeyframeAnimations.degreeVec(1.28F, -8.74F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(1.65F, -8.13F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2292F, KeyframeAnimations.degreeVec(1.46F, -8.67F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(1.68F, -8.59F, 2.03F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2708F, KeyframeAnimations.degreeVec(1.13F, -8.95F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(1.51F, -8.53F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3125F, KeyframeAnimations.degreeVec(1.35F, -8.17F, 2.22F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(1.83F, -8.9F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3542F, KeyframeAnimations.degreeVec(1.28F, -8.75F, 2.14F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(1.78F, -8.81F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3958F, KeyframeAnimations.degreeVec(1.6F, -8.43F, 2.47F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(1.78F, -8.16F, 2.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4375F, KeyframeAnimations.degreeVec(1.67F, -8.2F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(1.32F, -8.73F, 2.13F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4792F, KeyframeAnimations.degreeVec(1.71F, -8.35F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(1.16F, -8.37F, 2.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5208F, KeyframeAnimations.degreeVec(1.23F, -8.09F, 2.77F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(1.36F, -8.77F, 1.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5625F, KeyframeAnimations.degreeVec(1.12F, -8.27F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(1.43F, -8.78F, 2.51F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6042F, KeyframeAnimations.degreeVec(1.26F, -8.57F, 2.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(1.14F, -8.98F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6458F, KeyframeAnimations.degreeVec(1.66F, -8.56F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6667F, KeyframeAnimations.degreeVec(1.45F, -8.2F, 2.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.6875F, KeyframeAnimations.degreeVec(1.35F, -8.83F, 2.04F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7083F, KeyframeAnimations.degreeVec(1.87F, -8.3F, 2.49F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7292F, KeyframeAnimations.degreeVec(1.95F, -8.66F, 2.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(1.25F, -8.59F, 2.02F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7708F, KeyframeAnimations.degreeVec(1.02F, -8.81F, 2.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(1.14F, -8.66F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8125F, KeyframeAnimations.degreeVec(1.82F, -8.09F, 2.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8333F, KeyframeAnimations.degreeVec(1.8F, -8.22F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8542F, KeyframeAnimations.degreeVec(1.15F, -8.44F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.875F, KeyframeAnimations.degreeVec(1.28F, -8.56F, 2.59F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.8958F, KeyframeAnimations.degreeVec(1.42F, -8.71F, 2.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9167F, KeyframeAnimations.degreeVec(1.39F, -8.12F, 2.06F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9375F, KeyframeAnimations.degreeVec(1.4F, -8.77F, 2.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9583F, KeyframeAnimations.degreeVec(1.34F, -8.72F, 2.52F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.9792F, KeyframeAnimations.degreeVec(1.71F, -8.66F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(3.0F, KeyframeAnimations.degreeVec(1.32F, -8.06F, 2.69F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(1.47F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(7.89F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(9.91F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.degreeVec(10.46F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.2083F, KeyframeAnimations.degreeVec(10.4F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 1.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition SHOOT_LIGHT = AnimationDefinition.Builder.withLength(0.4167F)
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(5.02F, -7.49F, -0.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(8.35F, -7.49F, -0.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.02F, -7.49F, -0.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.2917F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.008F, 4.9981F, 0.2173F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.02F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-81.44F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-88.77F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(-96.1F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-97.52F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.posVec(-0.92F, -1.0F, 0.16F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.5F, -1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.posVec(-0.08F, -1.0F, 1.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.posVec(-1.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-86.92F, 0.56F, -90.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-86.81F, 11.26F, -89.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-86.76F, 5.54F, -89.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(-86.48F, -28.57F, -90.92F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-86.21F, -35.32F, -92.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-86.15F, -36.88F, -92.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-86.9225F, 0.5577F, -90.0796F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.posVec(0.0F, -0.87F, 0.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, -0.64F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 3.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition SHOOT_BEAM = AnimationDefinition.Builder.withLength(2.7917F)
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.02F, -7.49F, -0.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.2917F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.91F, -8.95F, 2.05F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(1.73F, -8.52F, 2.29F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(1.88F, -8.79F, 2.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.625F, KeyframeAnimations.degreeVec(1.32F, -9.16F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.76F, -8.39F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(1.44F, -8.48F, 2.57F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.75F, KeyframeAnimations.degreeVec(1.8F, -8.66F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7917F, KeyframeAnimations.degreeVec(1.97F, -9.25F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.98F, -8.98F, 2.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(1.91F, -9.02F, 2.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(1.53F, -9.27F, 2.16F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(1.91F, -8.92F, 2.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.97F, -8.7F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(1.93F, -8.96F, 2.98F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(1.72F, -9.33F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(1.79F, -9.06F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(1.83F, -9.44F, 2.32F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(1.7F, -9.16F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(1.91F, -8.93F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(1.29F, -9.1F, 2.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.83F, -9.31F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(1.6F, -9.03F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(1.55F, -8.93F, 2.25F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(1.52F, -8.8F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.68F, -9.35F, 2.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(1.65F, -8.48F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(1.57F, -8.54F, 2.19F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(1.35F, -8.95F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.33F, -8.83F, 2.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(1.66F, -8.48F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(1.37F, -8.69F, 2.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(1.79F, -8.83F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1.61F, -9.1F, 2.64F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(1.63F, -8.49F, 2.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(1.96F, -8.36F, 2.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(1.62F, -8.25F, 2.32F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.57F, -8.58F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(1.18F, -8.28F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(1.56F, -8.32F, 2.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(1.5F, -8.99F, 2.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(1.31F, -8.83F, 2.38F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(1.66F, -8.11F, 2.28F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(1.27F, -8.12F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(1.88F, -8.08F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(1.23F, -8.13F, 2.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(1.21F, -8.08F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(1.32F, -8.81F, 2.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(1.78F, -8.9F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(1.68F, -8.64F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(1.62F, -8.37F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(1.72F, -8.85F, 2.74F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(1.8F, -8.95F, 1.95F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(10.008F, 4.9981F, 0.2173F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.44F, -8.69F, 2.38F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(1.54F, -8.87F, 2.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(1.1F, -8.5F, 2.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.625F, KeyframeAnimations.degreeVec(1.7F, -8.07F, 2.72F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.6667F, KeyframeAnimations.degreeVec(1.53F, -8.12F, 2.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(1.8F, -8.16F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.75F, KeyframeAnimations.degreeVec(1.16F, -8.73F, 2.37F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7917F, KeyframeAnimations.degreeVec(1.76F, -8.92F, 2.52F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(1.59F, -8.35F, 2.43F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(1.64F, -9.01F, 2.48F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(1.72F, -8.19F, 2.78F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(1.69F, -8.19F, 2.94F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(1.86F, -8.68F, 2.75F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(1.9F, -8.38F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(1.04F, -8.43F, 2.22F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(1.15F, -8.76F, 2.55F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(1.83F, -8.19F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(1.58F, -8.87F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(1.76F, -8.79F, 2.91F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(1.55F, -8.74F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(1.52F, -8.79F, 2.29F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(1.84F, -8.35F, 2.45F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(1.75F, -8.83F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(1.67F, -9.03F, 2.37F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.53F, -8.55F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(1.24F, -8.2F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(1.64F, -8.57F, 2.34F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(1.81F, -8.35F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(1.11F, -8.69F, 2.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(1.17F, -8.82F, 2.91F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(1.53F, -8.51F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(1.53F, -8.54F, 2.36F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(1.65F, -8.13F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(1.68F, -8.59F, 2.03F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(1.51F, -8.53F, 2.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(1.83F, -8.9F, 2.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.78F, -8.81F, 2.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(1.78F, -8.16F, 2.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(1.32F, -8.73F, 2.13F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(1.16F, -8.37F, 2.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(1.23F, -8.09F, 2.77F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(1.12F, -8.27F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(1.26F, -8.57F, 2.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(1.66F, -8.56F, 2.2F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(1.35F, -8.83F, 2.04F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(1.95F, -8.66F, 2.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(1.02F, -8.81F, 2.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(1.82F, -8.09F, 2.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(1.15F, -8.44F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(1.42F, -8.71F, 2.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(1.4F, -8.77F, 2.33F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(1.71F, -8.66F, 2.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(2.52F, -7.49F, -0.33F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.posVec(0.0F, -1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.02F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-81.44F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-88.77F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(-96.1F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-97.52F, 20.71F, 5.99F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(-77.04F, 21.34F, 3.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-78.04F, 21.17F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-77.92F, 21.68F, 2.12F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.625F, KeyframeAnimations.degreeVec(-78.71F, 21.51F, 2.22F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-77.53F, 21.65F, 1.87F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-78.24F, 22.91F, 2.68F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.75F, KeyframeAnimations.degreeVec(-78.82F, 21.87F, 3.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-78.51F, 22.69F, 2.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-77.84F, 22.57F, 1.81F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(-77.29F, 21.51F, 3.52F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-78.53F, 22.62F, 2.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-76.94F, 21.25F, 1.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(-78.68F, 22.08F, 3.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-77.03F, 22.79F, 2.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-77.26F, 22.11F, 3.28F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(-77.23F, 21.72F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-77.4F, 21.69F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-77.8F, 22.72F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(-78.23F, 22.0F, 3.21F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-77.55F, 21.78F, 3.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-78.01F, 21.81F, 3.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(-77.5F, 22.7F, 3.04F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-78.2F, 21.49F, 2.4F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-78.31F, 22.43F, 2.76F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(-77.4F, 22.14F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-77.64F, 21.09F, 2.79F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-77.52F, 21.71F, 2.23F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(-77.25F, 21.82F, 2.7F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-77.9F, 22.01F, 3.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-77.03F, 22.11F, 3.37F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(-77.95F, 22.25F, 2.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-77.88F, 21.6F, 1.86F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-77.71F, 22.46F, 2.5F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(-78.69F, 21.54F, 2.94F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-77.92F, 21.52F, 2.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-77.43F, 22.54F, 2.1F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(-77.82F, 22.06F, 3.15F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-77.72F, 21.41F, 3.01F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-78.35F, 21.57F, 1.94F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(-78.36F, 22.34F, 2.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-78.66F, 21.47F, 3.01F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-78.19F, 22.56F, 2.98F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(-78.13F, 22.34F, 2.63F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-78.5F, 22.37F, 2.63F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-78.06F, 21.39F, 2.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(-77.46F, 21.38F, 3.13F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-78.24F, 21.62F, 3.39F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-77.13F, 22.7F, 2.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(-77.52F, 21.37F, 2.17F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-78.88F, 22.69F, 2.24F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(-77.25F, 21.08F, 3.77F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(-78.88F, 22.22F, 2.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(-1.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.posVec(-0.92F, -1.0F, 0.16F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.posVec(-0.5F, -1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.posVec(-0.08F, -1.0F, 1.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, -1.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.posVec(-1.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.posVec(-1.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-86.92F, 0.56F, -90.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-86.81F, 11.26F, -89.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-86.76F, 5.54F, -89.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(-86.48F, -28.57F, -90.92F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-86.21F, -35.32F, -92.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-86.15F, -36.88F, -92.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-86.9225F, 0.5577F, -90.0796F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(-85.17F, 1.98F, -88.96F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-85.01F, 2.3F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-84.95F, 1.66F, -88.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.625F, KeyframeAnimations.degreeVec(-84.99F, 2.47F, -88.21F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-85.77F, 2.2F, -88.95F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-85.39F, 1.76F, -88.9F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.75F, KeyframeAnimations.degreeVec(-85.69F, 1.74F, -88.05F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-85.54F, 1.66F, -88.88F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-85.41F, 1.85F, -88.64F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.875F, KeyframeAnimations.degreeVec(-85.05F, 2.55F, -88.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-85.41F, 2.27F, -88.14F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-85.1F, 1.82F, -88.66F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(-85.64F, 1.8F, -88.08F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-85.03F, 2.25F, -88.75F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-85.49F, 2.39F, -88.72F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.125F, KeyframeAnimations.degreeVec(-85.78F, 2.27F, -88.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-84.9F, 2.44F, -88.09F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-85.13F, 2.54F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.25F, KeyframeAnimations.degreeVec(-85.22F, 2.33F, -88.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-85.41F, 2.34F, -88.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.3333F, KeyframeAnimations.degreeVec(-85.74F, 2.13F, -88.18F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.375F, KeyframeAnimations.degreeVec(-85.13F, 2.37F, -88.15F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-85.4F, 2.49F, -88.11F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-85.1F, 2.16F, -88.44F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5F, KeyframeAnimations.degreeVec(-85.34F, 2.21F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-85.49F, 2.17F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-85.35F, 2.0F, -88.56F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.625F, KeyframeAnimations.degreeVec(-85.33F, 2.21F, -88.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-85.57F, 1.87F, -88.14F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-85.05F, 2.02F, -88.53F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.75F, KeyframeAnimations.degreeVec(-85.4F, 2.04F, -88.71F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-85.49F, 2.21F, -88.09F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-85.42F, 2.35F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.875F, KeyframeAnimations.degreeVec(-85.31F, 1.79F, -88.41F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-85.52F, 2.2F, -88.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-85.54F, 2.28F, -88.26F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0F, KeyframeAnimations.degreeVec(-85.27F, 1.9F, -88.61F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-85.58F, 2.45F, -88.54F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-85.0F, 2.38F, -88.84F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.125F, KeyframeAnimations.degreeVec(-85.55F, 1.71F, -88.82F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-85.03F, 2.33F, -88.27F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-85.58F, 2.15F, -88.97F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.25F, KeyframeAnimations.degreeVec(-85.16F, 1.86F, -88.92F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-85.49F, 2.54F, -88.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-85.52F, 1.88F, -88.51F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.375F, KeyframeAnimations.degreeVec(-85.28F, 1.94F, -88.8F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-85.27F, 2.36F, -88.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.4583F, KeyframeAnimations.degreeVec(-85.51F, 1.98F, -88.46F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5F, KeyframeAnimations.degreeVec(-85.11F, 2.39F, -88.67F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-84.94F, 1.91F, -88.3F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(-85.2F, 2.47F, -88.42F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.625F, KeyframeAnimations.degreeVec(-85.18F, 1.78F, -88.16F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7083F, KeyframeAnimations.degreeVec(-86.0279F, -6.3133F, -89.6379F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-86.9225F, 0.5577F, -90.0796F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0417F, KeyframeAnimations.posVec(0.0F, -0.87F, 0.31F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, -0.64F, 2.69F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, -1.0F, 3.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition BRING_OUT = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-80.6542F, 19.9942F, 6.0882F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-77.3968F, -1.2762F, -75.7837F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition PUT_DOWN = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-80.6542F, 19.9942F, 6.0882F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-77.3968F, -1.2762F, -75.7837F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition TRANSFORM_OPEN = AnimationDefinition.Builder.withLength(1.0F)
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-79.1531F, 30.5479F, 7.9889F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();

		public static final AnimationDefinition TRANSFORM_CLOSE = AnimationDefinition.Builder.withLength(1.0F)
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.125F, KeyframeAnimations.degreeVec(-79.1531F, 30.5479F, 7.9889F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-80.0224F, 20.7115F, 5.9869F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(-94.3633F, 1.3438F, -91.0151F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("Body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("LeftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.addAnimation("RightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			))
			.build();
	}
	
	public static class ToothShotgunAnimation
	{
		public static final AnimationDefinition SHOTGUN_FIRE = AnimationDefinition.Builder.withLength(0.4583F)
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-87.5184F, 0.0F, 7.3847F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-87.5095F, -0.2178F, 4.9953F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0F, 32.5F, 45.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-80.2603F, 30.037F, 44.4987F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-77.7603F, 30.037F, 44.4987F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-80.0F, 32.5F, 45.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(-5.0F, -3.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0417F, KeyframeAnimations.posVec(-5.0F, -2.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.0833F, KeyframeAnimations.posVec(-5.0F, -2.0F, -8.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(-5.0F, -3.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.build();
		
		public static final AnimationDefinition SHOTGUN_HOLD = AnimationDefinition.Builder.withLength(0.0F)
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0F, 32.5F, 45.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(-5.0F, -3.0F, -9.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.build();
		
		public static final AnimationDefinition SHOTGUN_RUNNING = AnimationDefinition.Builder.withLength(0.5F).looping()
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.0F, -72.5F, 60.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.degreeVec(-165.0F, -65.0F, 65.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-165.0F, -62.5F, 65.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.degreeVec(-160.0F, -70.0F, 62.5F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-160.0F, -72.5F, 60.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(-5.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(-4.0F, -4.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-4.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(-5.0F, -4.05F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-5.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-120.0F, 45.0F, 85.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.125F, KeyframeAnimations.posVec(2.0F, -6.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.79F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(1.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.posVec(1.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.build();
		
		public static final AnimationDefinition SHOTGUN_HOLD_TO_RUN = AnimationDefinition.Builder.withLength(0.5F)
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-158.7355F, -6.5944F, 25.6842F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-183.75F, -36.25F, 30.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-160.0F, -72.5F, 60.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.posVec(-5.0F, -4.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(-5.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(-5.0F, -4.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.4167F, KeyframeAnimations.posVec(-5.0F, -5.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-80.0F, 32.5F, 45.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-120.0F, 45.0F, 85.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.addAnimation("LeftArm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
					new Keyframe(0.0F, KeyframeAnimations.posVec(-5.0F, -3.0F, -9.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.1667F, KeyframeAnimations.posVec(1.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.2917F, KeyframeAnimations.posVec(1.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.3333F, KeyframeAnimations.posVec(1.0F, -8.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.375F, KeyframeAnimations.posVec(1.0F, -7.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
				))
				.build();
	}
}
