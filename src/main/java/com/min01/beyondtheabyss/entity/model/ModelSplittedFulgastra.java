package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.SplittedFulgastraAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
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

public class ModelSplittedFulgastra extends HierarchicalModel<EntitySplittedFulgastra>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "splitted_fulgastra"), "main");
	private final ModelPart root;

	public ModelSplittedFulgastra(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition SplittedFulgastra = root.addOrReplaceChild("SplittedFulgastra", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

		SplittedFulgastra.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -6.0F, -13.0F, 26.0F, 7.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 64).addBox(-13.0F, 1.0F, -13.0F, 26.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(105, 34).addBox(-10.0F, -8.0F, -10.0F, 20.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Down = SplittedFulgastra.addOrReplaceChild("Down", CubeListBuilder.create().texOffs(0, 93).addBox(-13.0F, 1.0F, -13.0F, 26.0F, 2.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-13.0F, 3.0F, -13.0F, 26.0F, 3.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(105, 107).addBox(-6.0F, 6.0F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(105, 0).addBox(-12.0F, -6.0F, -12.0F, 24.0F, 9.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Flippers = Down.addOrReplaceChild("Flippers", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

		Flippers.addOrReplaceChild("Flipper", CubeListBuilder.create().texOffs(105, 95).addBox(-13.0F, 0.0F, 0.0F, 26.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -13.0F, -0.7854F, 0.0F, 0.0F));

		Flippers.addOrReplaceChild("Flipper2", CubeListBuilder.create().texOffs(105, 57).addBox(0.0F, 0.0F, -13.0F, 0.0F, 11.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		Flippers.addOrReplaceChild("Flipper3", CubeListBuilder.create().texOffs(105, 95).addBox(-13.0F, 0.0F, 0.0F, 26.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, 0.7854F, 0.0F, 0.0F));

		Flippers.addOrReplaceChild("Flipper4", CubeListBuilder.create().texOffs(105, 57).mirror().addBox(0.0F, 0.0F, -13.0F, 0.0F, 11.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition Tentacles = Down.addOrReplaceChild("Tentacles", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

		Tentacles.addOrReplaceChild("Tentacle", CubeListBuilder.create().texOffs(50, 122).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

		Tentacles.addOrReplaceChild("Tentacle2", CubeListBuilder.create().texOffs(0, 122).addBox(0.0F, 0.0F, -6.0F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, 0.0F));

		Tentacles.addOrReplaceChild("Tentacle3", CubeListBuilder.create().texOffs(50, 122).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		Tentacles.addOrReplaceChild("Tentacle4", CubeListBuilder.create().texOffs(0, 122).mirror().addBox(0.0F, 0.0F, -6.0F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 0.0F, 0.0F));

		SplittedFulgastra.addOrReplaceChild("Bulb", CubeListBuilder.create().texOffs(25, 122).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntitySplittedFulgastra entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(SplittedFulgastraAnimation.FULGASTRA_SWIM, limbSwing, limbSwingAmount, 2.5F, 2.5F);
		entity.chargingAnimationState.animate(this, SplittedFulgastraAnimation.FULGASTRA_CHARGING, ageInTicks);
		entity.shockingAnimationState.animate(this, SplittedFulgastraAnimation.FULGASTRA_SHOCKING, ageInTicks);
		entity.closedAnimationState.animate(this, SplittedFulgastraAnimation.FULGASTRA_CLOSED, ageInTicks);
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