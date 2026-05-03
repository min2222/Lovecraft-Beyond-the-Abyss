package com.min01.beyondtheabyss.block.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.animation.BiocrafterAnimation;
import com.min01.beyondtheabyss.blockentity.deepabyss.BiocrafterBlockEntity;
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

public class BiocrafterModel extends HierarchicalBlockModel<BiocrafterBlockEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "biocrafter"), "main");
	private final ModelPart root;

	public BiocrafterModel(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Biocrafter = root.addOrReplaceChild("Biocrafter", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Top = Biocrafter.addOrReplaceChild("Top", CubeListBuilder.create().texOffs(0, 118).addBox(-7.4999F, -0.5857F, -7.4999F, 15.0F, 2.0F, 15.0F, new CubeDeformation(0.01F))
		.texOffs(0, 90).addBox(-7.4999F, -12.5857F, -7.4999F, 15.0F, 12.0F, 15.0F, new CubeDeformation(0.01F))
		.texOffs(61, 119).addBox(-5.4999F, -14.5857F, -5.4999F, 11.0F, 3.0F, 11.0F, new CubeDeformation(0.01F))
		.texOffs(0, 39).addBox(-7.4999F, -15.5857F, -7.4999F, 15.0F, 15.0F, 15.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.0001F, -16.4143F, -0.0001F));

		Top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(106, 119).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.6006F, -8.6857F, 7.5994F, 0.0F, 0.7854F, 0.0F));

		Top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(106, 119).mirror().addBox(0.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.6001F, -8.6857F, 7.6001F, 0.0F, -0.7854F, 0.0F));

		Top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(106, 119).mirror().addBox(0.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.6001F, -8.6857F, -7.5999F, 0.0F, 0.7854F, 0.0F));

		Top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(106, 119).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5999F, -8.6857F, -7.5999F, 0.0F, -0.7854F, 0.0F));

		PartDefinition Bottom = Biocrafter.addOrReplaceChild("Bottom", CubeListBuilder.create().texOffs(0, 70).addBox(-8.5F, -17.0F, -8.5F, 17.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(118, 109).addBox(-6.5F, -25.0F, -6.5F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(69, 70).addBox(-8.5F, -15.0F, -8.5F, 17.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-19.0F, 0.0F, -19.0F, 38.0F, 0.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Bottom.addOrReplaceChild("Gut", CubeListBuilder.create().texOffs(61, 90).addBox(-6.5F, -6.7F, -7.5F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(61, 39).addBox(-7.0F, -7.19F, -8.0F, 15.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -7.8F, 0.5F));

		PartDefinition Teeth = Bottom.addOrReplaceChild("Teeth", CubeListBuilder.create().texOffs(118, 90).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		Teeth.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(122, 60).addBox(-8.5F, -3.0F, 0.0F, 17.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9996F, 8.4991F, -0.3927F, 0.0F, 0.0F));

		Teeth.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(122, 39).addBox(0.0F, -3.0F, -8.5F, 0.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4991F, 0.9996F, 0.0F, 0.0F, 0.0F, -0.3927F));

		Teeth.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(122, 39).mirror().addBox(0.0F, -3.0F, -8.5F, 0.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		Teeth.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(122, 60).addBox(-8.5F, -3.0F, 0.0F, 17.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -8.5F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(BiocrafterBlockEntity blockEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		blockEntity.processingAnimationState.animateBlock(this, BiocrafterAnimation.BIOCRAFTER_PROCESSING, ageInTicks);
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