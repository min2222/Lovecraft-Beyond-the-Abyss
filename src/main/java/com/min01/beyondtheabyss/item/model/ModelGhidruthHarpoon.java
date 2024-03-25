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

public class ModelGhidruthHarpoon extends Model 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth_harpoon"), "main");
	private final ModelPart Harpoon;

	public ModelGhidruthHarpoon(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.Harpoon = root.getChild("Harpoon");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Harpoon = partdefinition.addOrReplaceChild("Harpoon", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -24.0F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 0).addBox(1.0F, -24.0F, 0.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(9, 0).addBox(-5.0F, -24.0F, 0.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(28, 13).addBox(0.0F, -24.0F, -5.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(0.0F, -24.0F, 1.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-1.5F, -17.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-1.5F, -13.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(9, 36).addBox(0.0F, -12.0F, 1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		Harpoon.addOrReplaceChild("Tip", CubeListBuilder.create().texOffs(8, 9).addBox(-5.5F, -12.5F, 0.0F, 11.0F, 20.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(9, 0).addBox(0.0F, -12.5F, -4.5F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		Harpoon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}