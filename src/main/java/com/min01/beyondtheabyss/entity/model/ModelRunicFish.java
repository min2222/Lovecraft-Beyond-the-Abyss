package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.RunicFishAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
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

public class ModelRunicFish extends HierarchicalModel<EntityRunicFish>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "runic_fish"), "main");
	private final ModelPart root;

	public ModelRunicFish(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition RunicFish = root.addOrReplaceChild("RunicFish", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body = RunicFish.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -4.0F, -6.5F, 5.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(30, 15).addBox(0.0F, -10.0F, -4.5F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -0.5F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 2.5F, 6.5F, 0.0F, 0.0F, 0.3054F));

		Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.5F, -3.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, 6.5F, 0.0F, 0.0F, -0.3054F));

		Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 35).addBox(0.0F, -0.25F, -2.25F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -1.25F, -0.1745F, 0.0F, 0.0F));

		Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(30, 33).addBox(0.0F, -4.0F, -1.75F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, 6.25F, 0.2182F, 0.0F, 0.0F));

		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, -2.5F, -1.0F, 3.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 6.5F));

		Tail.addOrReplaceChild("Tailedge", CubeListBuilder.create().texOffs(21, 22).addBox(0.0F, -4.5F, -1.5F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

		PartDefinition Leftfin = Body.addOrReplaceChild("Leftfin", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 1.5F, -5.5F, 0.0F, -1.0472F, 0.0F));

		Leftfin.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(14, 22).addBox(0.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition Rightfin = Body.addOrReplaceChild("Rightfin", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 1.5F, -5.5F, 0.0F, 1.0472F, 0.0F));

		Rightfin.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(14, 22).mirror().addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition Leftfin2 = Body.addOrReplaceChild("Leftfin2", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 0.5F, -5.0F, 0.0F, -1.2217F, 0.0F));

		Leftfin2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(14, 22).addBox(0.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition Rightfin2 = Body.addOrReplaceChild("Rightfin2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 0.5F, -5.0F, 0.0F, 1.2217F, 0.0F));

		Rightfin2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(14, 22).mirror().addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		RunicFish.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -3.0F, -4.9F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityRunicFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("RunicFish"), netHeadYaw, headPitch);
		this.animateWalk(RunicFishAnimation.RUNIC_FISH_SWIM, limbSwing, limbSwingAmount, 1.0F, entity.isPanic() ? 3.5F : 2.5F);
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