package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelCorpseAngler extends HierarchicalModel<EntityCorpseAngler>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "corpse_angler"), "main");
	private final ModelPart root;

	public ModelCorpseAngler(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition hook_angler = root.addOrReplaceChild("hook_angler", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition body = hook_angler.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 131).addBox(-15.25F, 4.9055F, -38.878F, 31.0F, 30.0F, 78.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 239).addBox(-31.5F, -35.2122F, -44.7094F, 34.0F, 45.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, 1.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-31.0F, -30.0F, -40.0F, 33.0F, 46.0F, 85.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, 1.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, -39.3F));

		jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(270, 279).addBox(-18.8798F, -28.3432F, -48.3486F, 0.0F, 42.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, -29.0F, 39.3F, 0.3155F, 0.2494F, 0.0804F));

		jaw.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(270, 279).addBox(-8.6491F, -28.3432F, -40.9723F, 0.0F, 42.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, -29.0F, 39.3F, 0.3155F, -0.2494F, -0.0804F));

		jaw.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 239).addBox(-30.75F, -28.3432F, -53.8147F, 33.0F, 51.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.5F, -29.0F, 39.3F, 0.3054F, 0.0F, 0.0F));

		body.addOrReplaceChild("hook", CubeListBuilder.create().texOffs(236, 0).addBox(0.25F, -15.0945F, -47.878F, 0.0F, 30.0F, 54.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, -36.0F));

		body.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(236, 84).addBox(-6.25F, -0.0945F, -6.878F, 21.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, 23.0F, -7.0F));

		body.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(176, 279).addBox(-13.25F, -0.0945F, -6.878F, 21.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 23.0F, -7.0F));

		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(218, 131).addBox(-0.5F, -33.0F, -23.0F, 0.0F, 68.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 30.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntityCorpseAngler entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("hook_angler"), netHeadYaw, headPitch);
		BTAClientUtil.animateWalk(this, this.corpseAnglerSwim(BTAClientUtil.getElapsedSeconds(false, 0.0F, entity.swimAnimationState.getAccumulatedTime()) / 60.0F), limbSwing, limbSwingAmount, 2.5F, 2.5F);
		this.animate(entity.idleAnimationState, this.corpseAnglerIdle(BTAClientUtil.getElapsedSeconds(false, 0.0F, entity.idleAnimationState.getAccumulatedTime()) / 60.0F), ageInTicks);
		
		entity.idleAnimationState.updateTime(ageInTicks, 1.0F);
		entity.swimAnimationState.updateTime(ageInTicks, 1.0F);
	}
	
	public AnimationDefinition corpseAnglerSwim(float elapsedSeconds)
	{
		AnimationDefinition anim = AnimationDefinition.Builder.withLength(0.0F)
				.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(0.0F, Math.sin(30 + elapsedSeconds * 100) * 7, -Math.sin(-30 + elapsedSeconds * 100) * 4), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(15 + Math.sin(elapsedSeconds * 200) * 3, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("hook", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(-Math.sin(-30 + elapsedSeconds * 200) * 4, Math.sin(-30 + elapsedSeconds * 100) * 7, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("leftfin", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(10 + Math.sin(elapsedSeconds * 100) * 25, 0.0F, Math.sin(60 + elapsedSeconds * 100) * 15), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("rightfin", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(10 - Math.sin(elapsedSeconds * 100) * 25, 0.0F, Math.sin(60 + elapsedSeconds * 100) * 15), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(0.0F, Math.sin(elapsedSeconds * 100) * 14, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.build();
		return anim;
	}
	
	public AnimationDefinition corpseAnglerIdle(float elapsedSeconds)
	{
		AnimationDefinition anim = AnimationDefinition.Builder.withLength(0.0F)
				.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(Math.sin(elapsedSeconds * 100) * -1, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
						new Keyframe(0.0F, BTAClientUtil.posVec(0.0F, Math.sin(-30 + elapsedSeconds * 100) * 1, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(15 + Math.sin(-60 + elapsedSeconds * 100) * 3, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("hook", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(-Math.sin(-70 + elapsedSeconds * 100) * 4, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("leftfin", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(10 - Math.sin(elapsedSeconds * 100) * 25, 0.0F, -Math.sin(60 + elapsedSeconds * 100) * 15), AnimationChannel.Interpolations.LINEAR)
				))
				.addAnimation("rightfin", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
					new Keyframe(0.0F, BTAClientUtil.degreeVec(10 - Math.sin(elapsedSeconds * 100) * 25, 0.0F, Math.sin(60 + elapsedSeconds * 100) * 15), AnimationChannel.Interpolations.LINEAR)
				))
				.build();
		return anim;
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}