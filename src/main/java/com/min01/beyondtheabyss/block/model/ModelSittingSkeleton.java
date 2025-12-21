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

public class ModelSittingSkeleton extends Model
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "sitting_skeleton"), "main");
	private final ModelPart Skeleton;

	public ModelSittingSkeleton(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.Skeleton = root.getChild("Skeleton");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Skeleton = partdefinition.addOrReplaceChild("Skeleton", CubeListBuilder.create(), PartPose.offset(0.0F, 34.0F, 4.0F));

		Skeleton.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.5F, -1.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition body = Skeleton.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, -1.0F, 0.1745F, 0.0F, -0.0873F));

		body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 30).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		Skeleton.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(23, 32).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -12.0F, -0.9F, -1.4286F, -0.2457F, -0.0914F));

		Skeleton.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(25, 17).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -12.0F, 0.1F, -1.4377F, 0.1264F, 0.0341F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		Skeleton.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}