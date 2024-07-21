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

public class ModelFangSkull extends Model 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "fang_skull"), "main");
	private final ModelPart fang_skull;

	public ModelFangSkull(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.fang_skull = root.getChild("fang_skull");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition fang_skull = partdefinition.addOrReplaceChild("fang_skull", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		fang_skull.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -5.0F, -10.0F, 7.0F, 5.0F, 11.0F, new CubeDeformation(0.01F))
		.texOffs(0, 17).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 4.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, -0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		fang_skull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}