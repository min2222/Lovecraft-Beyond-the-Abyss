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

public class ModelRiftwellingAltar extends Model 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "riftwelling_altar"), "main");
	private final ModelPart bone;

	public ModelRiftwellingAltar(ModelPart root)
	{
		super(RenderType::entityCutoutNoCull);
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -11.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(69, 4).addBox(-6.0F, -10.0F, -11.0F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(69, 7).addBox(-6.0F, -10.0F, 8.0F, 12.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(66, 10).addBox(8.0F, -10.0F, -6.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(60, 10).addBox(-11.0F, -10.0F, -6.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 55).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(10, 16).addBox(0.0F, -9.0F, -8.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-6.0F, -9.0F, -6.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(4, 27).addBox(-8.0F, -9.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 73).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(10, 21).addBox(0.0F, -9.0F, 6.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(6.0F, -9.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(36, 36).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0).addBox(-3.0F, -13.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(48, 18).addBox(-3.0F, -13.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 7).addBox(0.0F, -9.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -11.0F, 8.0F, 0.0F, 0.7854F, 0.0F));

		bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 31).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -11.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

		bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(60, 46).addBox(0.0F, 0.0F, -3.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.2929F, -11.0F, -7.2929F, 0.0F, 0.7854F, 0.0F));

		bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(56, 46).addBox(0.0F, 0.0F, 23.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(0.0F, -9.0F, 18.0F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.2635F, -11.0F, -8.2635F, 0.0F, 0.7854F, 0.0F));

		bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(52, 48).addBox(-2.4F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7172F, -11.0F, 7.7172F, 0.0F, 0.7854F, 0.0F));

		bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(48, 48).addBox(22.6F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(36, 36).addBox(17.6F, -9.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.9806F, -11.0F, 7.9806F, 0.0F, 0.7854F, 0.0F));

		bone.addOrReplaceChild("teeth2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 4.0F, -0.0873F, 0.0F, 0.0F));

		bone.addOrReplaceChild("teeth", CubeListBuilder.create().texOffs(0, 2).addBox(-4.0F, -2.0F, 0.0F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -4.0F, 0.0873F, 0.0F, 0.0F));

		bone.addOrReplaceChild("teeth3", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		bone.addOrReplaceChild("teeth4", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -12.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}