package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.DeepVampireAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
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

public class ModelDeepVampire extends HierarchicalModel<EntityDeepVampire> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "deep_vampire"), "main");
	private final ModelPart root;

	public ModelDeepVampire(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition DeepVamp = root.addOrReplaceChild("DeepVamp", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.4241F));

		PartDefinition Head = DeepVamp.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, -8.8F, 3.4241F));

		Head.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(0, 65).addBox(-3.5F, -5.5F, -11.0F, 7.0F, 5.0F, 11.0F, new CubeDeformation(0.02F))
		.texOffs(62, 0).addBox(-3.5F, -0.5F, -11.0F, 7.0F, 6.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition Down = Head.addOrReplaceChild("Down", CubeListBuilder.create().texOffs(68, 52).addBox(-3.5F, 1.0F, -11.0F, 7.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(53, 25).addBox(-3.5F, -1.0F, -11.0F, 7.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 0.0F));

		PartDefinition ChinBarbelRight = Down.addOrReplaceChild("ChinBarbelRight", CubeListBuilder.create().texOffs(19, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -7.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube = ChinBarbelRight.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition cube2 = cube.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(19, 6).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

		cube2.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition ChinBarbelLeft = Down.addOrReplaceChild("ChinBarbelLeft", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 3.0F, -7.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition cube4 = ChinBarbelLeft.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(10, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition cube5 = cube4.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(16, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

		cube5.addOrReplaceChild("cube6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition Body = DeepVamp.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(32, 0).addBox(-2.5F, -3.0F, 0.25F, 5.0F, 5.0F, 19.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -8.0F, 3.1741F));

		Body.addOrReplaceChild("BodyPos", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition Body2 = Body.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(50, 57).addBox(0.0F, -5.0F, 0.0F, 0.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 19.15F, 0.0F, 0.0F, 0.0F));

		Body2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -2.0F, -3.5F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 17.5F, 0.0F, 0.0F, -0.3491F));

		Body2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -2.0F, -3.5F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 17.5F, 0.0F, 0.0F, 0.3491F));

		Body2.addOrReplaceChild("TailPos", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 9.0F));

		PartDefinition Tails2 = Body2.addOrReplaceChild("Tails2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.5F, -1.0F, 3.0F, 4.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(28, 52).addBox(0.0F, -4.5F, 0.0F, 0.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -6.5F, 19.0F, 0.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.4F, 18.0F));

		Tails2.addOrReplaceChild("TailEdgePos", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 13.0F));

		Tails2.addOrReplaceChild("TailEdge", CubeListBuilder.create().texOffs(28, 74).addBox(0.0F, -6.5F, 0.0F, 0.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

		PartDefinition BigFinLeft = Body.addOrReplaceChild("BigFinLeft", CubeListBuilder.create().texOffs(29, 41).addBox(-0.99F, -0.01F, -2.01F, 15.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -2.0F, 12.25F, -0.3316F, -1.309F, -1.2741F));

		BigFinLeft.addOrReplaceChild("BigFinLeft2", CubeListBuilder.create().texOffs(0, 30).addBox(0.01F, -0.01F, -2.01F, 21.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, 0.0F, 0.0F));

		PartDefinition BigFinRight = Body.addOrReplaceChild("BigFinRight", CubeListBuilder.create().texOffs(29, 41).mirror().addBox(-14.01F, -0.01F, -2.01F, 15.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -2.0F, 12.25F, -0.3316F, 1.309F, 1.2741F));

		BigFinRight.addOrReplaceChild("BigFinRight2", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-21.01F, -0.01F, -2.01F, 21.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.0F, 0.0F, 0.0F));

		Body.addOrReplaceChild("GillLeft", CubeListBuilder.create().texOffs(47, 77).addBox(0.0F, -4.5F, 0.0F, 0.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.51F, -0.51F, 3.24F, 0.0F, 0.7418F, 0.0F));

		Body.addOrReplaceChild("GillRight", CubeListBuilder.create().texOffs(64, 77).addBox(0.0F, -4.5F, 0.0F, 0.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.51F, -0.51F, 3.24F, 0.0F, -0.7418F, 0.0F));

		PartDefinition Frills = Body.addOrReplaceChild("Frills", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 1.25F));

		PartDefinition Frillleft = Frills.addOrReplaceChild("Frillleft", CubeListBuilder.create().texOffs(0, 41).addBox(0.0F, -3.5F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.51F, 2.49F, -0.01F, 0.0F, -0.3491F, 0.0F));

		Frillleft.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(48, 52).addBox(-3.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.5F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition Frillright = Frills.addOrReplaceChild("Frillright", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-8.0F, -3.5F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.51F, 2.49F, -0.01F, 0.0F, 0.3491F, 0.0F));

		Frillright.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(48, 52).mirror().addBox(-5.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -3.5F, 0.0F, -0.2182F, 0.0F, 0.0F));

		Frills.addOrReplaceChild("FrillCenter", CubeListBuilder.create().texOffs(48, 61).addBox(-2.5F, -9.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.01F, -0.01F, -0.2182F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityDeepVampire entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("DeepVamp"), netHeadYaw, headPitch);
		this.animateWalk(DeepVampireAnimation.DEEP_VAMPIRE_SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		this.animate(entity.biteRightAnimationState, DeepVampireAnimation.DEEP_VAMPIRE_BITE_RIGHT, ageInTicks);
		this.animate(entity.biteLeftAnimationState, DeepVampireAnimation.DEEP_VAMPIRE_BITE_LEFT, ageInTicks);
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