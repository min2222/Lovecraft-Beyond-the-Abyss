package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.GnasherLeaderAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
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

public class ModelGnasherLeader extends HierarchicalModel<EntityGnasher>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "gnasher_leader"), "main");
	private final ModelPart root;

	public ModelGnasherLeader(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition LeadGnasher = root.addOrReplaceChild("LeadGnasher", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body = LeadGnasher.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -8.2813F, -10.75F, 15.0F, 16.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 37).addBox(0.0F, -22.2813F, -12.75F, 0.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.7187F, 0.75F));

		Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 37).addBox(0.0F, -2.0F, -5.5F, 0.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 7.7187F, 8.75F, 0.0F, 0.0F, 0.4363F));

		Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(25, 37).addBox(0.0F, -2.0F, -5.5F, 0.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 7.7187F, 8.75F, 0.0F, 0.0F, -0.4363F));

		PartDefinition Fins = Body.addOrReplaceChild("Fins", CubeListBuilder.create(), PartPose.offset(0.0F, 1.7187F, -5.75F));

		Fins.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(72, 62).addBox(-1.0F, -6.5F, 0.0F, 25.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -0.5F, 0.0F, 0.0F, -1.2217F, 0.0F));

		Fins.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(71, 23).addBox(-24.0F, -6.5F, 0.0F, 25.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, -0.5F, 0.0F, 0.0F, 1.2217F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, 3.7187F, -10.75F));

		PartDefinition Up = Head.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(49, 37).addBox(-5.5F, -8.0F, -14.0F, 11.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(51, 0).addBox(0.0F, -9.0F, -14.0F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		Up.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 77).mirror().addBox(0.0F, -1.0F, -5.0F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.5F, -8.0F, -9.0F, 0.0F, 0.0F, -0.5236F));

		Up.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 77).addBox(0.0F, -1.0F, -5.0F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -8.0F, -9.0F, 0.0F, 0.0F, 0.5236F));

		Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(33, 62).addBox(-5.5F, 0.0F, -14.0F, 11.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(71, 0).addBox(-3.0F, -3.5F, -2.0F, 7.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(63, 2).addBox(0.5F, -4.5F, 0.0F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.7813F, 9.25F));

		Tail.addOrReplaceChild("Tailedge", CubeListBuilder.create().texOffs(76, 76).addBox(0.0F, -11.5F, 0.0F, 0.0F, 23.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 11.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityGnasher entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("LeadGnasher"), netHeadYaw, headPitch);
		BTAClientUtil.animateWalk(entity, this, GnasherLeaderAnimation.GNASHER_LEADER_SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		this.animate(entity.biteAnimationState, GnasherLeaderAnimation.GNASHER_LEADER_BITE, ageInTicks);
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