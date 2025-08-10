package com.min01.beyondtheabyss.block.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.animation.GlaringBarnacleAnimation;
import com.min01.beyondtheabyss.blockentity.AnimatableBlockEntity;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModelGlaringBarnacle extends HierarchicalBlockModel<AnimatableBlockEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "glaring_barnacle"), "main");
	private final ModelPart GlaringBarnacle;
	private final ModelPart Up;
	private final ModelPart Cirrifront;
	private final ModelPart Cirriback;
	private final ModelPart Cirrileft;
	private final ModelPart Cirriright;

	public ModelGlaringBarnacle(ModelPart root) 
	{
		this.GlaringBarnacle = root.getChild("GlaringBarnacle");
		this.Up = this.GlaringBarnacle.getChild("Up");
		this.Cirrifront = this.Up.getChild("Cirrifront");
		this.Cirriback = this.Up.getChild("Cirriback");
		this.Cirrileft = this.Up.getChild("Cirrileft");
		this.Cirriright = this.Up.getChild("Cirriright");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition GlaringBarnacle = partdefinition.addOrReplaceChild("GlaringBarnacle", CubeListBuilder.create().texOffs(0, 12).addBox(-3.5F, -3.0F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.5F, -0.01F, -5.5F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Up = GlaringBarnacle.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(0, 23).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition Cirrifront = Up.addOrReplaceChild("Cirrifront", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -1.0F, 0.6109F, 0.0F, 0.0F));

		Cirrifront.addOrReplaceChild("Cirri", CubeListBuilder.create().texOffs(21, 23).addBox(0.0F, -8.0F, -0.5F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Cirrifront.addOrReplaceChild("Cirri2", CubeListBuilder.create().texOffs(21, 23).addBox(0.0F, -8.0F, -0.5F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

		Cirrifront.addOrReplaceChild("Cirri3", CubeListBuilder.create().texOffs(21, 23).addBox(0.0F, -8.0F, -0.5F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition Cirriback = Up.addOrReplaceChild("Cirriback", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, -0.6109F, 0.0F, 0.0F));

		Cirriback.addOrReplaceChild("Cirri4", CubeListBuilder.create().texOffs(28, 23).addBox(0.0F, -8.0F, -2.5F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Cirriback.addOrReplaceChild("Cirri5", CubeListBuilder.create().texOffs(28, 23).addBox(0.0F, -8.0F, -2.5F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

		Cirriback.addOrReplaceChild("Cirri6", CubeListBuilder.create().texOffs(28, 23).addBox(0.0F, -8.0F, -2.5F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition Cirrileft = Up.addOrReplaceChild("Cirrileft", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

		Cirrileft.addOrReplaceChild("Cirri7", CubeListBuilder.create().texOffs(29, 12).addBox(-2.5F, -8.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Cirrileft.addOrReplaceChild("Cirri8", CubeListBuilder.create().texOffs(29, 12).addBox(-2.5F, -8.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		Cirrileft.addOrReplaceChild("Cirri9", CubeListBuilder.create().texOffs(29, 12).addBox(-2.5F, -8.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition Cirriright = Up.addOrReplaceChild("Cirriright", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

		Cirriright.addOrReplaceChild("Cirri10", CubeListBuilder.create().texOffs(29, 12).mirror().addBox(-0.5F, -8.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		Cirriright.addOrReplaceChild("Cirri11", CubeListBuilder.create().texOffs(29, 12).mirror().addBox(-0.5F, -8.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -1.0F));

		Cirriright.addOrReplaceChild("Cirri12", CubeListBuilder.create().texOffs(29, 12).mirror().addBox(-0.5F, -8.0F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(AnimatableBlockEntity block, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(block.idleAnimationState, GlaringBarnacleAnimation.BARNACLE_IDLE, ageInTicks);
		boolean isWater = block.getBlockState().getValue(BlockStateProperties.WATERLOGGED);
		this.Cirrifront.visible = isWater;
		this.Cirriback.visible = isWater;
		this.Cirrileft.visible = isWater;
		this.Cirriright.visible = isWater;
	}
	
	@Override
	public ModelPart root() 
	{
		return this.GlaringBarnacle;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		GlaringBarnacle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}