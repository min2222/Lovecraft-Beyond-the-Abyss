package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
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

public class ModelSerpentHeart extends EntityModel<EntitySiamserpentBone>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "serpent_heart"), "main");
	private final ModelPart Heart;

	public ModelSerpentHeart(ModelPart root) 
	{
		this.Heart = root.getChild("Heart");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Heart = partdefinition.addOrReplaceChild("Heart", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -2.7761F, -3.2398F, 7.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(21, 26).addBox(-2.5F, -3.7761F, -4.2398F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-3.5F, 5.2239F, -3.2398F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(29, 0).addBox(-1.5F, -0.7761F, -6.4898F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(29, 9).addBox(-1.5F, -6.7761F, -1.2398F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.7761F, -0.2602F));

		Heart.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2761F, 3.2602F, 0.829F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntitySiamserpentBone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		Heart.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}