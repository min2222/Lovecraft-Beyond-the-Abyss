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

public class ModelHarpoon extends Model
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "harpoon"), "main");
	private final ModelPart Harpoon;

	public ModelHarpoon(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.Harpoon = root.getChild("Harpoon");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Harpoon = partdefinition.addOrReplaceChild("Harpoon", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(-1.5F, -19.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 11).addBox(1.5F, -18.0F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		Harpoon.addOrReplaceChild("Tip", CubeListBuilder.create().texOffs(9, 21).addBox(-3.5F, -11.5F, 0.0F, 7.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(9, 0).addBox(0.0F, -11.5F, -3.5F, 0.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Harpoon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}