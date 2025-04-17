package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.GloomfishAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGloomfish;
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

public class ModelGloomfish extends HierarchicalModel<EntityGloomfish>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "gloomfish"), "main");
	private final ModelPart root;

	public ModelGloomfish(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition gloomfish = root.addOrReplaceChild("gloomfish", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.5F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-1.0F, -3.5F, -5.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -0.5F));

		gloomfish.addOrReplaceChild("rightfin", CubeListBuilder.create().texOffs(18, 8).addBox(-3.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, -1.5F));

		gloomfish.addOrReplaceChild("topfin", CubeListBuilder.create().texOffs(8, 14).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -0.5F));

		gloomfish.addOrReplaceChild("bottomfin", CubeListBuilder.create().texOffs(18, 5).addBox(0.0F, 0.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 0.5F));

		gloomfish.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.5F));

		gloomfish.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.5F, -5.5F));

		gloomfish.addOrReplaceChild("leftfin", CubeListBuilder.create().texOffs(18, 8).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, -1.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(EntityGloomfish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("gloomfish"), netHeadYaw, headPitch);
		this.animateWalk(GloomfishAnimation.GLOOMFISH_SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
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