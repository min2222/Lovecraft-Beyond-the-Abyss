package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.GnasherAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
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

public class GnasherModel extends HierarchicalModel<GnasherEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "gnasher"), "main");
	private final ModelPart root;

	public GnasherModel(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Gnasher = root.addOrReplaceChild("Gnasher", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body = Gnasher.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.3333F, -6.8333F, 10.0F, 12.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(35, 13).addBox(0.0F, 4.6667F, -4.8333F, 0.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(50, 0).addBox(0.0F, -12.3333F, -1.8333F, 0.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.6667F, -0.1667F));

		PartDefinition Fins = Body.addOrReplaceChild("Fins", CubeListBuilder.create(), PartPose.offset(0.0F, 1.6667F, -3.8333F));

		Fins.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(50, 50).addBox(0.0F, -6.5F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.5F, 0.0F, 0.0F, -1.2217F, 0.0F));

		Fins.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(35, 0).addBox(-10.0F, -6.5F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.5F, 0.0F, 0.0F, 1.2217F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, 1.6667F, -6.8333F));

		Head.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(0, 27).addBox(-4.0F, -6.0F, -11.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(29, 34).addBox(-4.0F, -0.5F, -11.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

		PartDefinition Tails = Body.addOrReplaceChild("Tails", CubeListBuilder.create().texOffs(23, 50).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.8333F, 7.1667F));

		Tails.addOrReplaceChild("TailEdge", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, -6.5F, -1.0F, 0.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(GnasherEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("Gnasher"), netHeadYaw, headPitch);
		entity.biteAnimationState.animate(this, GnasherAnimation.GNASHER_BITE, ageInTicks);
		this.animateWalk(GnasherAnimation.GNASHER_SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
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