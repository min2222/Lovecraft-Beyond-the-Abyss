package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityOverseer;
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

public class ModelOverseer extends HierarchicalModel<EntityOverseer>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "overseer"), "main");
	private final ModelPart root;

	public ModelOverseer(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition overseer = root.addOrReplaceChild("overseer", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 3.0F));

		PartDefinition head = overseer.addOrReplaceChild("head", CubeListBuilder.create().texOffs(384, 210).addBox(-24.0F, -13.0F, -77.0F, 48.0F, 29.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(436, 120).addBox(19.0F, -16.0F, -79.0F, 33.0F, 15.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(436, 90).addBox(-52.0F, -16.0F, -79.0F, 33.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(436, 45).addBox(-9.5F, -11.0F, -11.5F, 19.0F, 22.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(-61.5F, -10.0F, -71.5F));

		head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(436, 0).addBox(-9.5F, -11.0F, -11.5F, 19.0F, 22.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(61.5F, -10.0F, -71.5F));

		PartDefinition body = overseer.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 210).addBox(-64.0F, -16.0F, -35.0F, 128.0F, 28.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(278, 302).addBox(-34.0F, -11.0F, 29.0F, 68.0F, 14.0F, 63.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("right_cover", CubeListBuilder.create().texOffs(0, 488).mirror().addBox(0.0F, 0.0F, -31.0F, 34.0F, 6.0F, 63.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-34.0F, 3.0F, 60.0F));

		body.addOrReplaceChild("left_cover", CubeListBuilder.create().texOffs(0, 488).addBox(-34.0F, 0.0F, -31.0F, 34.0F, 6.0F, 63.0F, new CubeDeformation(0.0F)), PartPose.offset(34.0F, 3.0F, 60.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(10, 115).addBox(0.0F, -7.0F, -33.0F, 98.0F, 15.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offset(64.0F, -6.0F, -2.0F));

		left_wing.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(0, 601).addBox(0.0F, -5.0F, -33.0F, 101.0F, 9.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(0, 849).addBox(101.0F, -5.0F, -33.0F, 35.0F, 9.0F, 58.0F, new CubeDeformation(0.0F)), PartPose.offset(98.0F, -1.0F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(10, 115).mirror().addBox(-98.0F, -7.0F, -33.0F, 98.0F, 15.0F, 80.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-64.0F, -6.0F, -2.0F));

		right_wing.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(0, 601).mirror().addBox(-101.0F, -5.0F, -33.0F, 101.0F, 9.0F, 80.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 849).mirror().addBox(-136.0F, -5.0F, -33.0F, 35.0F, 9.0F, 58.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-98.0F, -1.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(278, 385).addBox(-10.0F, -9.0F, 0.0F, 20.0F, 18.0F, 76.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 92.0F));

		tail.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(0, 302).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 13.0F, 131.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 76.0F));

		return LayerDefinition.create(meshdefinition, 1024, 1024);
	}

	@Override
	public void setupAnim(EntityOverseer entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		ModelPart root = this.root.getChild("overseer");
		ModelPart head = root.getChild("head");
		BTAClientUtil.animateHead(root, netHeadYaw, headPitch);
		BTAClientUtil.animateHead(head.getChild("right_eye"), netHeadYaw, headPitch - (float) Math.toDegrees(root.xRot));
		BTAClientUtil.animateHead(head.getChild("left_eye"), -netHeadYaw, headPitch - (float) Math.toDegrees(root.xRot));
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