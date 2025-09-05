package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.TwinserpentAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead;
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

public class ModelTwinserpentSlasher extends HierarchicalModel<EntityTwinserpentHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "twinserpent_slasher"), "main");
	private final ModelPart root;

	public ModelTwinserpentSlasher(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("TwinserpentSlasher", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -15.0F, -14.5F, 14.0F, 10.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-11.0F, -8.0F, -14.5F, 22.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(52, 64).addBox(-7.0F, -5.0F, -14.5F, 14.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(73, 0).addBox(0.0F, -18.0F, -5.5F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(65, 29).addBox(-2.0F, -15.0F, -40.5F, 4.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -13.0F, -42.5F, 22.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityTwinserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("TwinserpentSlasher"), netHeadYaw, headPitch);
		entity.slashRightAnimationState.animate(this, TwinserpentAnimation.SlasherAnimation.SLASHER_SLASH_RIGHT, ageInTicks);
		entity.slashLeftAnimationState.animate(this, TwinserpentAnimation.SlasherAnimation.SLASHER_SLASH_LEFT, ageInTicks);
		entity.slasherChargeStartAnimationState.animate(this, TwinserpentAnimation.SlasherAnimation.SLASHER_CHARGE_START, ageInTicks);
		entity.slasherChargingAnimationState.animate(this, TwinserpentAnimation.SlasherAnimation.SLASHER_CHARGING, ageInTicks);
		entity.slasherDisabledAnimationState.animate(this, TwinserpentAnimation.SlasherAnimation.SLASHER_DISABLED, ageInTicks);
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