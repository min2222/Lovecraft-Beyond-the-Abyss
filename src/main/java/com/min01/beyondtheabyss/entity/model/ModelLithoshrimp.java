package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.LithoshrimpAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLithoshrimp;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelLithoshrimp extends HierarchicalModel<EntityLithoshrimp>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "lithoshrimp"), "main");
	private final ModelPart root;

	public ModelLithoshrimp(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition lithoshrimp = root.addOrReplaceChild("lithoshrimp", CubeListBuilder.create().texOffs(26, 8).addBox(-3.0F, -1.5F, 0.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(26, 19).addBox(-3.0F, -1.5F, -5.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 0.0F));

		lithoshrimp.addOrReplaceChild("mandibles", CubeListBuilder.create().texOffs(32, 0).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -4.0F));

		lithoshrimp.addOrReplaceChild("antennae1", CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, 0.0F, -12.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.5F, -5.0F, 0.1745F, 0.6109F, 0.0F));

		lithoshrimp.addOrReplaceChild("antennae2", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, 0.0F, -12.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.5F, -5.0F, 0.1745F, -0.6109F, 0.0F));

		lithoshrimp.addOrReplaceChild("legset1", CubeListBuilder.create().texOffs(26, 27).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.5F, -2.5F));

		lithoshrimp.addOrReplaceChild("legset2", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.5F, -2.5F));

		PartDefinition tailbase = lithoshrimp.addOrReplaceChild("tailbase", CubeListBuilder.create().texOffs(14, 32).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 4.0F));

		tailbase.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		lithoshrimp.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(14, 36).addBox(-2.5F, -4.0F, -2.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(EntityLithoshrimp entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		entity.idleAnimationState.animate(this, LithoshrimpAnimation.LITHOSHRIMP_IDLE, ageInTicks, limbSwingAmount, 3.5F);
		this.animateWalk(LithoshrimpAnimation.LITHOSHRIMP_SWIM, limbSwing, limbSwingAmount, 3.5F, 3.5F);
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