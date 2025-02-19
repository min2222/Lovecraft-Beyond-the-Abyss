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

public class ModelBoneTorch extends Model
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "bone_torch"), "main");
	private final ModelPart BoneTorch;

	public ModelBoneTorch(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.BoneTorch = root.getChild("BoneTorch");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition BoneTorch = partdefinition.addOrReplaceChild("BoneTorch", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -2.5625F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -4.5625F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(9, 13).addBox(-1.0F, -6.5625F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 7).addBox(-2.0F, -5.5625F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5625F, 0.0F));

		BoneTorch.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 7).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.4375F, -1.0F, 0.0F, -0.7854F, 0.0F));

		BoneTorch.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(17, 0).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.4375F, -1.0F, 0.0F, 0.7854F, 0.0F));

		BoneTorch.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 18).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.4375F, 1.0F, 0.0F, 0.7854F, 0.0F));

		BoneTorch.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.4375F, 1.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		BoneTorch.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}