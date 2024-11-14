package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModelGhidruthDiverSet<T extends LivingEntity> extends HumanoidModel<T> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth_diver_set"), "main");
	public final ModelPart Head;
	public final ModelPart Body;
	public final ModelPart LeftArm;
	public final ModelPart RightArm;
	public final ModelPart LeftFeet;
	public final ModelPart RightFeet;
	public final ModelPart LeftLeg;
	public final ModelPart RightLeg;

	public ModelGhidruthDiverSet(ModelPart root) 
	{
		super(root);
		this.Head = root.getChild("Head");
		this.Body = root.getChild("Body");
		this.LeftArm = root.getChild("LeftArm");
		this.RightArm = root.getChild("RightArm");
		this.LeftFeet = root.getChild("LeftFeet");
		this.RightFeet = root.getChild("RightFeet");
		this.LeftLeg = root.getChild("LeftLeg");
		this.RightLeg = root.getChild("RightLeg");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
		.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
		.texOffs(96, 68).addBox(2.0F, -13.0F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(60, 71).addBox(4.0F, -8.3F, -4.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(65, 24).addBox(4.5F, -7.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(65, 24).mirror().addBox(-5.5F, -7.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(64, 8).addBox(-3.5F, -7.5F, -6.5F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(56, 24).addBox(-2.5F, -6.5F, 4.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(66, 17).addBox(-3.5F, -7.5F, -5.5F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(66, 2).addBox(-2.5F, -6.5F, -7.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Head.addOrReplaceChild("HatLayer_r1", CubeListBuilder.create().texOffs(64, 79).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, -3.0F, 0.1745F, 0.0F, 0.2618F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 5.0F, 5.0F, new CubeDeformation(0.6F))
		.texOffs(4, 54).addBox(0.5F, 1.0F, 1.6F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(4, 50).addBox(-3.5F, 4.0F, 2.1F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 67).addBox(-3.5F, 1.0F, 2.6F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 42).addBox(-2.5F, -1.6F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 64).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(1, 80).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Body.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(24, 73).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, -3.0F, 0.1745F, 0.0F, 0.0F));

		Body.addOrReplaceChild("BodyLayer_r2", CubeListBuilder.create().texOffs(38, 71).addBox(-0.5F, -6.5F, -2.0F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

		Body.addOrReplaceChild("BodyLayer_r3", CubeListBuilder.create().texOffs(38, 71).addBox(-0.5F, -6.5F, -2.0F, 1.0F, 12.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

		Body.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(34, 66).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 6.0F, 3.1F, 0.2182F, 0.0F, 0.0F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(64, 48).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		LeftArm.addOrReplaceChild("LeftArmLayer_r1", CubeListBuilder.create().texOffs(68, 82).addBox(-1.0F, -2.0F, -0.4F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2815F, -2.9514F, 2.355F, -2.8956F, -0.6873F, 3.0206F));

		LeftArm.addOrReplaceChild("LeftArmLayer_r2", CubeListBuilder.create().texOffs(68, 82).addBox(-0.8F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5873F, -2.0733F, -0.1545F, -0.8565F, -1.4711F, 1.0243F));

		LeftArm.addOrReplaceChild("LeftArmLayer_r3", CubeListBuilder.create().texOffs(64, 79).addBox(-1.5F, -6.5F, -1.1F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.5F, -2.0F, 0.1102F, -0.4556F, 0.1102F));

		LeftArm.addOrReplaceChild("LeftArmLayer_r4", CubeListBuilder.create().texOffs(66, 81).addBox(-0.8F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, -2.5F, 0.31F, 0.2141F, 0.0268F));

		LeftArm.addOrReplaceChild("LeftArmLayer_r5", CubeListBuilder.create().texOffs(66, 88).addBox(2.0F, -6.0F, 0.0F, 4.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(74, 79).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(1.3F, 4.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

		partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(56, 36).addBox(-5.0F, -9.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(-1.0F, -5.0F, 0.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(42, 23).addBox(-8.0F, -2.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 48).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		partdefinition.addOrReplaceChild("LeftFeet", CubeListBuilder.create().texOffs(64, 100).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(64, 75).addBox(-2.0F, 10.0F, -4.6F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("RightFeet", CubeListBuilder.create().texOffs(80, 100).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(64, 75).mirror().addBox(-2.0F, 10.0F, -4.6F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(-2.0F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(64, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(64, 53).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(80, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(96, 63).addBox(-6.0F, 1.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 53).mirror().addBox(-2.0F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		this.Head.copyFrom(this.head);
		this.Body.copyFrom(this.body);
		this.LeftArm.copyFrom(this.leftArm);
		this.RightArm.copyFrom(this.rightArm);
		this.LeftLeg.copyFrom(this.leftLeg);
		this.RightLeg.copyFrom(this.rightLeg);
		this.LeftFeet.copyFrom(this.leftLeg);
		this.RightFeet.copyFrom(this.rightLeg);
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftFeet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightFeet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}