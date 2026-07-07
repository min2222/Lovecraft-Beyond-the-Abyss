package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.animation.ItemAnimations;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ToothShotgunModel extends HierarchicalItemModel
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "tooth_shotgun"), "main");
	private final ModelPart root;

	public ToothShotgunModel(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, 0.0F, -15.0F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 59).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(34, 59).addBox(0.0F, 1.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(61, 51).addBox(0.0F, -8.0F, -3.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(48, 30).addBox(-1.5F, -4.0F, -3.0F, 3.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

		body.addOrReplaceChild("hammer", CubeListBuilder.create().texOffs(42, 59).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 2.0F));

		PartDefinition barrel = body.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(79, 0).addBox(-2.5F, -3.0F, -6.0F, 5.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(79, 21).addBox(-2.5F, -3.0F, -6.0F, 5.0F, 3.0F, 18.0F, new CubeDeformation(0.1F))
		.texOffs(18, 59).addBox(-2.5F, -4.0F, -1.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

		barrel.addOrReplaceChild("top_teeth", CubeListBuilder.create().texOffs(36, 42).addBox(-3.5F, -1.5F, -5.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.01F))
		.texOffs(48, 0).addBox(-3.5F, -1.5F, -5.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(36, 52).addBox(-2.5F, 0.5F, -4.5F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		barrel.addOrReplaceChild("bottom_teeth", CubeListBuilder.create().texOffs(48, 10).addBox(-3.5F, -0.5F, -5.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.01F))
		.texOffs(48, 20).addBox(-3.5F, -0.5F, -5.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(58, 52).addBox(-2.5F, -0.5F, -4.5F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.0F));

		barrel.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -0.5F, -5.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		barrel.addOrReplaceChild("ammo", CubeListBuilder.create().texOffs(120, 124).addBox(0.5F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(120, 124).addBox(-1.5F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(ItemStack stack, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		ItemAnimations.animate(stack, this, BTAClientUtil.MC.getPartialTick());
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}