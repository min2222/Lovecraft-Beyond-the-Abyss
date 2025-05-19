package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ModelFlashlight extends Model 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "flashlight"), "main");
	private final ModelPart root;

	public ModelFlashlight(ModelPart root) 
	{
		super(RenderType::entityTranslucent);
		this.root = root.getChild("diving flashlight");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		partdefinition.addOrReplaceChild("diving flashlight", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -11.0F, 7.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.01F))
		.texOffs(0, 12).addBox(-10.5F, -11.5F, 5.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(-10.5F, -11.5F, 5.35F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-8.5F, -13.0F, 8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-8.5F, -13.0F, 12.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 19).addBox(-8.5F, -13.0F, 9.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}