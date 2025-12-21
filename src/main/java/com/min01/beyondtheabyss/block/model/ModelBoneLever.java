package com.min01.beyondtheabyss.block.model;

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

public class ModelBoneLever extends Model
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "bone_lever"), "main");
	private final ModelPart bone;

	public ModelBoneLever(ModelPart root)
	{
		super(RenderType::entityCutoutNoCull);
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -1.25F, 8.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(15, 24).mirror().addBox(4.0F, -4.5F, -0.25F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 24).mirror().addBox(3.0F, -4.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 24).addBox(-4.0F, -4.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(15, 24).addBox(-6.0F, -4.5F, -0.25F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-5.0F, -2.5F, -0.25F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-3.0F, 3.5F, -1.25F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(21, 8).addBox(-4.0F, 3.5F, -0.25F, 8.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 23.25F, -2.2F, 1.5708F, 0.0F, 3.1416F));

		bone.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(17, 16).addBox(-2.0F, -0.5F, -5.4167F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-2.5F, -1.0F, -7.4167F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(21, 0).addBox(1.0F, -0.5F, -5.4167F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -0.8333F, -0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}