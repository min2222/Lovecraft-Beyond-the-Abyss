package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentBoneEntity;
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

public class SiamserpentBoneModel extends HierarchicalModel<SiamserpentBoneEntity> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "siamserpent_bone"), "main");
	private final ModelPart root;

	public SiamserpentBoneModel(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition SiamserpentBone = root.addOrReplaceChild("SiamserpentBone", CubeListBuilder.create().texOffs(0, 60).addBox(-2.0F, -17.0F, -8.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(41, 60).addBox(0.0F, -24.0F, -8.0F, 0.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-9.0F, -15.0F, -8.0F, 18.0F, 15.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		SiamserpentBone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 46).addBox(-12.0F, 0.0F, -7.0F, 12.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -15.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		SiamserpentBone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, 0.0F, -7.0F, 12.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition Finleft = SiamserpentBone.addOrReplaceChild("Finleft", CubeListBuilder.create(), PartPose.offset(9.0F, -3.0F, 1.0F));

		Finleft.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, 0.0F, -8.0F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition Finright = SiamserpentBone.addOrReplaceChild("Finright", CubeListBuilder.create(), PartPose.offset(9.0F, -3.0F, 1.0F));

		Finright.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-16.0F, 0.0F, -8.0F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition Armleft = SiamserpentBone.addOrReplaceChild("Armleft", CubeListBuilder.create().texOffs(59, 32).addBox(-0.25F, -1.5F, -2.5F, 11.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(69, 0).addBox(10.75F, -1.5F, -3.5F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.45F, -5.5833F, -0.25F, 0.0F, 0.0F, 0.7854F));

		Armleft.addOrReplaceChild("Claw", CubeListBuilder.create().texOffs(59, 41).addBox(-1.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(14.75F, 0.0F, -2.25F));

		Armleft.addOrReplaceChild("Claw4", CubeListBuilder.create().texOffs(59, 41).addBox(-1.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.75F, 0.0F, -3.25F, 0.0F, 1.5708F, 0.0F));

		Armleft.addOrReplaceChild("Claw2", CubeListBuilder.create().texOffs(59, 41).addBox(-1.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(14.75F, 0.0F, 0.0F));

		Armleft.addOrReplaceChild("Claw3", CubeListBuilder.create().texOffs(59, 41).addBox(-1.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(14.75F, 0.0F, 2.25F));

		PartDefinition Armright = SiamserpentBone.addOrReplaceChild("Armright", CubeListBuilder.create().texOffs(59, 32).mirror().addBox(-10.75F, -1.5F, -2.5F, 11.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(69, 0).mirror().addBox(-14.75F, -1.5F, -3.5F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.45F, -5.5833F, -0.25F, 0.0F, 0.0F, -0.7854F));

		Armright.addOrReplaceChild("Claw5", CubeListBuilder.create().texOffs(59, 41).mirror().addBox(-5.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.75F, 0.0F, -2.25F));

		Armright.addOrReplaceChild("Claw6", CubeListBuilder.create().texOffs(59, 41).mirror().addBox(-5.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.75F, 0.0F, -3.25F, 0.0F, -1.5708F, 0.0F));

		Armright.addOrReplaceChild("Claw7", CubeListBuilder.create().texOffs(59, 41).mirror().addBox(-5.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.75F, 0.0F, 0.0F));

		Armright.addOrReplaceChild("Claw8", CubeListBuilder.create().texOffs(59, 41).mirror().addBox(-5.0F, -1.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.75F, 0.0F, 2.25F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(SiamserpentBoneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("SiamserpentBone"), entity.isInvert() ? netHeadYaw + 180.0F : netHeadYaw, entity.isInvert() ? -headPitch : headPitch);
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