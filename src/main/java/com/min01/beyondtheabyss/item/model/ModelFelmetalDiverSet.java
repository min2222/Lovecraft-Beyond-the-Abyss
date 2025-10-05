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

public class ModelFelmetalDiverSet<T extends LivingEntity> extends HumanoidModel<T> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "felmetal_diver_set"), "main");
	private final ModelPart all;
	public final ModelPart Body;
	public final ModelPart Head;
	public final ModelPart LeftArm;
	public final ModelPart LeftFeet;
	public final ModelPart LeftLeg;
	public final ModelPart RightArm;
	public final ModelPart RightFeet;
	public final ModelPart RightLeg;

	public ModelFelmetalDiverSet(ModelPart root)
	{
		super(root);
		this.all = root.getChild("all");
		this.Body = this.all.getChild("Body");
		this.Head = this.all.getChild("Head");
		this.LeftArm = this.all.getChild("LeftArm");
		this.LeftFeet = this.all.getChild("LeftFeet");
		this.LeftLeg = this.LeftFeet.getChild("LeftLeg");
		this.RightArm = this.all.getChild("RightArm");
		this.RightFeet = this.all.getChild("RightFeet");
		this.RightLeg = this.RightFeet.getChild("RightLeg");
	}
	
	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition Body = all.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(33, 20).addBox(-5.0F, -0.25F, -2.5F, 10.0F, 9.0F, 5.0F, new CubeDeformation(0.6F))
		.texOffs(58, 35).addBox(-2.5F, -1.6F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(33, 35).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(58, 0).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		Body.addOrReplaceChild("OxygenTank", CubeListBuilder.create().texOffs(17, 49).addBox(-2.0F, -3.75F, 1.125F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 49).addBox(-1.0F, -2.75F, 1.125F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 37).addBox(-3.5F, -3.25F, -6.375F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(37, 0).addBox(-2.5F, -1.75F, -0.975F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, 3.475F));

		PartDefinition Head = all.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.5F, -9.0F, -4.5F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(38, 69).addBox(4.5F, -7.0F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(38, 69).mirror().addBox(-5.5F, -7.0F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(72, 59).addBox(-2.5F, -7.0F, 4.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 71).addBox(-3.5F, -7.0F, -4.65F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.75F, 0.0F));

		Head.addOrReplaceChild("HatLayer_r1", CubeListBuilder.create().texOffs(17, 46).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		all.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(64, 18).addBox(-1.0F, 7.0F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.4F))
		.texOffs(0, 65).addBox(-1.0F, 5.2F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.4F))
		.texOffs(58, 8).mirror().addBox(-1.5F, -2.3F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(51, 69).addBox(-1.5F, 2.1F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(15, 71).addBox(3.9F, -2.3F, -2.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(3.0F, -10.0F, 0.0F));

		PartDefinition LeftFeet = all.addOrReplaceChild("LeftFeet", CubeListBuilder.create().texOffs(17, 52).addBox(-2.5F, 5.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.31F))
		.texOffs(58, 42).addBox(-2.5F, 3.4F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.31F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		LeftFeet.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(38, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		all.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(55, 52).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(58, 8).addBox(-3.5F, -2.3F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(68, 69).addBox(-1.5F, 2.1F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(72, 49).addBox(-4.9F, -2.3F, -2.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.2F))
		.texOffs(64, 26).addBox(-4.0F, 7.0F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.4F))
		.texOffs(19, 65).addBox(-4.0F, 5.2F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-7.0F, -10.0F, 0.0F));

		PartDefinition RightFeet = all.addOrReplaceChild("RightFeet", CubeListBuilder.create().texOffs(17, 52).mirror().addBox(-2.5F, 5.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(58, 42).mirror().addBox(-2.5F, 3.4F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

		RightFeet.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(38, 52).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

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
