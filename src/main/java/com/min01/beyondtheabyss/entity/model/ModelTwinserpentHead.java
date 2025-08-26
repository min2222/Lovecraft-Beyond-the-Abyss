package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.TwinserpentAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead.HeadType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

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

public class ModelTwinserpentHead extends HierarchicalModel<EntityTwinserpentHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "iamserpent_blaster"), "main");
	private final ModelPart root;

	public ModelTwinserpentHead(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("TwinserpentSlasher", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -15.0F, -14.5F, 14.0F, 10.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-11.0F, -8.0F, -14.5F, 22.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(52, 64).addBox(-7.0F, -5.0F, -14.5F, 14.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(73, 0).addBox(0.0F, -18.0F, -5.5F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(65, 29).addBox(-2.0F, -15.0F, -40.5F, 4.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -13.0F, -42.5F, 22.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition TwinserpentBlaster = root.addOrReplaceChild("TwinserpentBlaster", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Up = TwinserpentBlaster.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(0, 66).addBox(-6.0F, -8.9532F, -15.7F, 12.0F, 9.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(58, 9).addBox(-6.0F, 0.0468F, -23.7F, 12.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(84, 37).addBox(-6.0F, -10.9532F, -23.7F, 12.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0468F, 7.2F));

		Up.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(59, 74).mirror().addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -8.9532F, -6.2F, 0.0F, 0.0F, 0.6109F));

		Up.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(59, 74).addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -8.9532F, -6.2F, 0.0F, 0.0F, -0.6109F));

		PartDefinition Jaw = TwinserpentBlaster.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -25.75F, 14.0F, 6.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-7.0F, -4.0F, -25.75F, 14.0F, 4.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 7.25F));

		Jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(65, 48).addBox(0.0F, 0.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 3.0F, -13.25F, 0.0F, 0.0F, 0.6109F));

		Jaw.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(65, 48).mirror().addBox(0.0F, 0.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 3.0F, -13.25F, 0.0F, 0.0F, -0.6109F));

		TwinserpentBlaster.addOrReplaceChild("InnerRay", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 2.5F));

		TwinserpentBlaster.addOrReplaceChild("RayofEnergy", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-6.0F)), PartPose.offset(0.0F, -5.0F, 3.5F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityTwinserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("TwinserpentSlasher"), netHeadYaw, headPitch);
		BTAClientUtil.animateHead(this.root.getChild("TwinserpentBlaster"), netHeadYaw, headPitch);
		this.root.getChild("TwinserpentSlasher").visible = entity.getHeadType() == HeadType.SLASHER;
		this.root.getChild("TwinserpentBlaster").visible = entity.getHeadType() == HeadType.BLASTER;
		this.root.getChild("TwinserpentBlaster").getChild("RayofEnergy").visible = false;
		this.root.getChild("TwinserpentBlaster").getChild("InnerRay").visible = false;
		this.root.getChild("TwinserpentBlaster").getChild("RayofEnergy").zScale += entity.getBeamLength();
		this.root.getChild("TwinserpentBlaster").getChild("InnerRay").zScale += entity.getBeamLength() + (entity.getBeamLength() / 2.0F);
		this.animate(entity.rayChargeAnimationState, TwinserpentAnimation.BlasterAnimation.BLASTER_CHARGE, ageInTicks);
		this.animate(entity.rayStartAnimationState, TwinserpentAnimation.BlasterAnimation.BLASTER_SHOOT_START, ageInTicks);
		this.animate(entity.rayLoopAnimationState, TwinserpentAnimation.BlasterAnimation.BLASTER_SHOOT_LOOP, ageInTicks);
		this.animate(entity.rayEndAnimationState, TwinserpentAnimation.BlasterAnimation.BLASTER_SHOOT_END, ageInTicks);
		this.animate(entity.blasterShotAnimationState, TwinserpentAnimation.BlasterAnimation.BLASTER_SHOT, ageInTicks);
		this.animate(entity.blasterDisabledAnimationState, TwinserpentAnimation.BlasterAnimation.BLASTER_DISABLED, ageInTicks);
		this.animate(entity.slashRightAnimationState, TwinserpentAnimation.SlasherAnimation.SLASHER_SLASH_RIGHT, ageInTicks);
		this.animate(entity.slashLeftAnimationState, TwinserpentAnimation.SlasherAnimation.SLASHER_SLASH_LEFT, ageInTicks);
		this.animate(entity.slasherChargeStartAnimationState, TwinserpentAnimation.SlasherAnimation.SLASHER_CHARGE_START, ageInTicks);
		this.animate(entity.slasherChargingAnimationState, TwinserpentAnimation.SlasherAnimation.SLASHER_CHARGING, ageInTicks);
		this.animate(entity.slasherDisabledAnimationState, TwinserpentAnimation.SlasherAnimation.SLASHER_DISABLED, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}
}