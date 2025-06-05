package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.SpinewormAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
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

public class ModelSpineWormHead extends HierarchicalModel<EntitySpineWormHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "spine_worm_head"), "main");
	private final ModelPart root;

	public ModelSpineWormHead(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 28).addBox(-8.0F, -4.8725F, -8.0F, 16.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.0F, -4.8725F, -8.0F, 16.0F, 9.0F, 16.0F, new CubeDeformation(0.25F))
		.texOffs(0, 58).addBox(-5.0F, -4.8725F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(42, 58).addBox(-5.0F, 1.1175F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.1275F, 0.0F));

		head.addOrReplaceChild("top_mouth_1", CubeListBuilder.create().texOffs(64, 24).addBox(-5.5F, 0.0F, -5.0F, 11.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.8725F, 5.0F));

		head.addOrReplaceChild("top_mouth_2", CubeListBuilder.create().texOffs(64, 24).addBox(-5.5F, 0.0F, -5.0F, 11.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.8725F, 5.0F));

		head.addOrReplaceChild("bottom_mouth_1", CubeListBuilder.create().texOffs(64, 29).addBox(-5.5F, 0.0F, 0.0F, 11.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.8725F, -5.0F));

		head.addOrReplaceChild("bottom_mouth_2", CubeListBuilder.create().texOffs(64, 29).addBox(-5.5F, 0.0F, 0.0F, 11.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.8725F, -5.0F));

		head.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(64, 12).mirror().addBox(0.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(64, 0).mirror().addBox(6.0F, 0.0F, -1.5F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(64, 6).mirror().addBox(6.0F, 0.0F, -1.5F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(64, 34).mirror().addBox(20.0F, -4.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(40, 64).mirror().addBox(20.0F, -4.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(116, 0).mirror().addBox(20.0F, -6.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(11.0F, -2.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(82, 12).mirror().addBox(15.0F, -3.0F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 0.1275F, 0.0F));

		head.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(40, 64).addBox(-24.0F, -4.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(64, 12).addBox(-6.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-24.0F, 0.0F, -1.5F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(64, 6).addBox(-24.0F, 0.0F, -1.5F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(64, 34).addBox(-24.0F, -4.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(116, 0).addBox(-24.0F, -6.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 0).addBox(-12.0F, -2.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(82, 12).addBox(-16.0F, -3.0F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.1275F, 0.0F));

		head.addOrReplaceChild("center_antenna", CubeListBuilder.create().texOffs(60, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.8725F, 8.0F));

		head.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(60, 68).addBox(-31.0F, -1.0F, 0.0F, 32.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -2.8725F, 8.0F));

		head.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(60, 68).mirror().addBox(-1.0F, -1.0F, 0.0F, 32.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -2.8725F, 8.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySpineWormHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("head"), netHeadYaw, headPitch + 90.0F);
		this.animate(entity.idleAnimationState, SpinewormAnimation.SPINEWORM_IDLE, ageInTicks);
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