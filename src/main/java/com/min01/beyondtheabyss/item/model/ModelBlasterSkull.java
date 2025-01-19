package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
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

public class ModelBlasterSkull extends EntityModel<EntitySiamserpentHead> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "blaster_skull"), "main");
	private final ModelPart BlasterSkull;

	public ModelBlasterSkull(ModelPart root) 
	{
		this.BlasterSkull = root.getChild("BlasterSkull");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition BlasterSkull = partdefinition.addOrReplaceChild("BlasterSkull", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -2.4532F, -6.2F, 12.0F, 9.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, 6.5468F, -14.2F, 12.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(37, 55).addBox(-6.0F, -4.4532F, -14.2F, 12.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.4532F, -3.3F));

		BlasterSkull.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -2.4532F, 3.3F, 0.0F, 0.0F, 0.6109F));

		BlasterSkull.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 55).addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -2.4532F, 3.3F, 0.0F, 0.0F, -0.6109F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySiamserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		BlasterSkull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}