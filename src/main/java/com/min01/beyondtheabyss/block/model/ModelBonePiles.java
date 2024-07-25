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

public class ModelBonePiles extends Model 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "bone_piles"), "main");
	private final ModelPart BonePiles;

	public ModelBonePiles(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.BonePiles = root.getChild("BonePiles");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition BonePiles = partdefinition.addOrReplaceChild("BonePiles", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		BonePiles.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -5.5F, 5.0F, -0.5695F, -0.7024F, 0.7809F));

		BonePiles.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -1.5F, -0.9854F, 0.5621F, -0.0131F));

		BonePiles.addOrReplaceChild("smallbone", CubeListBuilder.create().texOffs(25, 24).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(25, 17).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(25, 0).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.25F, 0.48F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		BonePiles.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}