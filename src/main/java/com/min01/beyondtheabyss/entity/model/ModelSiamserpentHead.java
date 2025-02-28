package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.SiamserpentAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
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

public class ModelSiamserpentHead extends HierarchicalModel<EntitySiamserpentHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "iamserpent_blaster"), "main");
	private final ModelPart root;

	public ModelSiamserpentHead(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("SiamserpentSlasher", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -15.0F, -14.5F, 14.0F, 10.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-11.0F, -8.0F, -14.5F, 22.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(52, 64).addBox(-7.0F, -5.0F, -14.5F, 14.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(73, 0).addBox(0.0F, -18.0F, -5.5F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(65, 29).addBox(-2.0F, -15.0F, -40.5F, 4.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -13.0F, -42.5F, 22.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition SiamserpentBlaster = root.addOrReplaceChild("SiamserpentBlaster", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Up = SiamserpentBlaster.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(0, 66).addBox(-6.0F, -8.9532F, -15.7F, 12.0F, 9.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(58, 9).addBox(-6.0F, 0.0468F, -23.7F, 12.0F, 2.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(84, 37).addBox(-6.0F, -10.9532F, -23.7F, 12.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0468F, 7.2F));

		Up.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(59, 74).mirror().addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -8.9532F, -6.2F, 0.0F, 0.0F, 0.6109F));

		Up.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(59, 74).addBox(0.0F, -7.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -8.9532F, -6.2F, 0.0F, 0.0F, -0.6109F));

		PartDefinition Jaw = SiamserpentBlaster.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -25.75F, 14.0F, 6.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-7.0F, -4.0F, -25.75F, 14.0F, 4.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 7.25F));

		Jaw.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(65, 48).addBox(0.0F, 0.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 3.0F, -13.25F, 0.0F, 0.0F, 0.6109F));

		Jaw.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(65, 48).mirror().addBox(0.0F, 0.0F, -7.5F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 3.0F, -13.25F, 0.0F, 0.0F, -0.6109F));

		SiamserpentBlaster.addOrReplaceChild("InnerRay", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 2.5F));

		SiamserpentBlaster.addOrReplaceChild("RayofEnergy", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(-6.0F)), PartPose.offset(0.0F, -5.0F, 3.5F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntitySiamserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("SiamserpentSlasher"), entity.shouldInvertRotation() ? netHeadYaw + 180.0F : netHeadYaw, entity.shouldInvertRotation() ? -headPitch : headPitch);
		BTAClientUtil.animateHead(this.root.getChild("SiamserpentBlaster"), entity.shouldInvertRotation() ? netHeadYaw + 180.0F : netHeadYaw, entity.shouldInvertRotation() ? -headPitch : headPitch);
		this.root.getChild("SiamserpentSlasher").visible = entity.getHeadType() == HeadType.SLASHER;
		this.root.getChild("SiamserpentBlaster").visible = entity.getHeadType() == HeadType.BLASTER;
		this.root.getChild("SiamserpentBlaster").getChild("RayofEnergy").visible = false;
		this.root.getChild("SiamserpentBlaster").getChild("InnerRay").visible = false;
		this.root.getChild("SiamserpentBlaster").getChild("RayofEnergy").zScale += entity.getBeamLength();
		this.root.getChild("SiamserpentBlaster").getChild("InnerRay").zScale += entity.getBeamLength() + (entity.getBeamLength() / 2.0F);
		this.animate(entity.chargeAnimationState, SiamserpentAnimation.BlasterAnimation.BLASTER_CHARGE, ageInTicks);
		this.animate(entity.shootStartAnimationState, SiamserpentAnimation.BlasterAnimation.BLASTER_SHOOT_START, ageInTicks);
		this.animate(entity.shootLoopAnimationState, SiamserpentAnimation.BlasterAnimation.BLASTER_SHOOT_LOOP, ageInTicks);
		this.animate(entity.shootEndAnimationState, SiamserpentAnimation.BlasterAnimation.BLASTER_SHOOT_END, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}
}