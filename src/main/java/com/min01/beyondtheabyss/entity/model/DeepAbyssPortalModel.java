package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.DeepAbyssPortalAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.DeepAbyssPortalEntity;
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

public class DeepAbyssPortalModel extends HierarchicalModel<DeepAbyssPortalEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "deep_abyss_portal"), "main");
	private final ModelPart root;
	
	public DeepAbyssPortalModel(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition portal = root.addOrReplaceChild("portal", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, -72.0F, -1.5708F, 0.0F, 0.0F));

		portal.addOrReplaceChild("inner_portal", CubeListBuilder.create().texOffs(1, 189).addBox(-56.5F, -58.5F, -3.5F, 113.0F, 113.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -70.5F, -20.5F));

		PartDefinition door1 = portal.addOrReplaceChild("door1", CubeListBuilder.create(), PartPose.offset(0.0F, -72.0F, -75.0F));

		door1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(241, 213).addBox(-43.0F, -25.3597F, 46.9714F, 86.0F, 48.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 1.0F, 0.0349F, 0.0F, 0.0F));

		PartDefinition door2 = portal.addOrReplaceChild("door2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -72.0F, -75.0F, 0.0F, 0.0F, -0.48F));

		door2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(118, 309).addBox(-1.0F, -45.1878F, 56.0679F, 34.0F, 103.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0113F, 1.1202F, -9.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition door3 = portal.addOrReplaceChild("door3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -72.0F, -75.0F, 0.0F, 0.0F, 0.48F));

		door3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(376, 0).addBox(-33.0F, -45.1878F, 56.0679F, 34.0F, 103.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0113F, 1.1202F, -9.0F, 0.0175F, 0.0F, 0.0F));

		portal.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-88.0F, -87.5F, -6.5F, 175.0F, 175.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, -72.5F, -22.5F));

		PartDefinition gem = portal.addOrReplaceChild("gem", CubeListBuilder.create().texOffs(376, 103).mirror().addBox(40.5F, 91.0F, -8.5F, 20.0F, 20.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(376, 103).addBox(-60.5F, 91.0F, -8.5F, 20.0F, 20.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -130.0F, -28.5F));

		gem.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(376, 103).addBox(-10.0F, -10.0F, -8.5F, 20.0F, 20.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.3562F));

		portal.addOrReplaceChild("pipe1", CubeListBuilder.create().texOffs(118, 412).addBox(-35.0F, -7.0F, 75.0F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 368).addBox(-46.0F, -12.0F, 81.0F, 40.0F, 40.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(118, 424).addBox(-17.0F, -7.0F, 75.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(277, 412).addBox(-35.0F, 17.0F, 75.0F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(415, 403).addBox(-41.0F, -7.0F, 75.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(114.5F, 5.0F, -116.0F));

		PartDefinition pipe2 = portal.addOrReplaceChild("pipe2", CubeListBuilder.create(), PartPose.offset(88.5F, -157.0F, -31.75F));

		pipe2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(118, 412).addBox(-9.0F, -15.0F, -9.25F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(118, 424).addBox(9.0F, -15.0F, -9.25F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(277, 412).addBox(-9.0F, 9.0F, -9.25F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(415, 403).addBox(-15.0F, -15.0F, -9.25F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 368).addBox(-20.0F, -20.0F, -3.25F, 40.0F, 40.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition pipe3 = portal.addOrReplaceChild("pipe3", CubeListBuilder.create(), PartPose.offset(-88.5F, -157.0F, -31.75F));

		pipe3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(277, 412).addBox(-9.0F, 9.0F, -9.25F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(415, 403).addBox(-15.0F, -15.0F, -9.25F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(118, 424).addBox(9.0F, -15.0F, -9.25F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(118, 412).addBox(-9.0F, -15.0F, -9.25F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 368).addBox(-20.0F, -20.0F, -3.25F, 40.0F, 40.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition pipe4 = portal.addOrReplaceChild("pipe4", CubeListBuilder.create(), PartPose.offset(-88.5F, 13.0F, -31.75F));

		pipe4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(118, 412).addBox(-9.0F, -15.0F, -9.25F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(118, 424).addBox(9.0F, -15.0F, -9.25F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(277, 412).addBox(-9.0F, 9.0F, -9.25F, 18.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 368).addBox(-20.0F, -20.0F, -3.25F, 40.0F, 40.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(415, 403).addBox(-15.0F, -15.0F, -9.25F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition edge = portal.addOrReplaceChild("edge", CubeListBuilder.create().texOffs(241, 188).addBox(-32.0F, -12.0F, 50.0F, 114.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-25.0F, -24.0F, -85.0F));

		edge.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(291, 261).addBox(23.0F, -110.0F, 50.0F, 12.0F, 114.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(50.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.48F));

		edge.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(241, 261).addBox(-35.0F, -110.0F, 50.0F, 12.0F, 114.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(DeepAbyssPortalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.idleAnimationState, DeepAbyssPortalAnimation.PORTAL_IDLE, ageInTicks);
		this.animate(entity.openAnimationState, DeepAbyssPortalAnimation.PORTAL_OPEN, ageInTicks);
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