package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.projectile.EntityMissile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelMissile extends EntityModel<EntityMissile> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "missile"), "main");
	private final ModelPart missile;

	public ModelMissile(ModelPart root) 
	{
		this.missile = root.getChild("missile");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition missile = partdefinition.addOrReplaceChild("missile", CubeListBuilder.create().texOffs(0, 34).addBox(-5.0F, -5.5F, -2.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-5.0F, -5.5F, -28.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -6.5F, 2.0F, 12.0F, 12.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -6.5F, -24.0F, 12.0F, 12.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(5, 52).addBox(-0.5F, -11.5F, 6.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(5, 52).addBox(-0.5F, -9.5F, 21.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, 0.0F));

		missile.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 52).mirror().addBox(0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 5.5F, 7.0F, 0.0F, 0.0F, -3.1416F));

		missile.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 52).mirror().addBox(0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 0.5F, 7.0F, 0.0F, 0.0F, -1.5708F));

		missile.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(5, 52).mirror().addBox(0.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 0.5F, 22.0F, 0.0F, 0.0F, -1.5708F));

		missile.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(5, 52).addBox(-1.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.5F, 22.0F, 0.0F, 0.0F, 1.5708F));

		missile.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(5, 52).addBox(-1.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.5F, 7.0F, 0.0F, 0.0F, 1.5708F));

		missile.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(5, 52).addBox(-1.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.5F, 7.0F, 0.0F, 0.0F, 3.1416F));

		missile.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(5, 52).addBox(-1.5F, -5.0F, -1.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.5F, 22.0F, 0.0F, 0.0F, 3.1416F));

		missile.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 48).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 24.0F, 0.0F, 0.0F, -0.7854F));

		missile.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 48).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 24.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityMissile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		missile.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}