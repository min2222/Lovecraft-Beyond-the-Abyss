package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
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

public class ModelSiamserpentMiddleBone extends HierarchicalModel<EntitySiamserpentBone> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "siamserpent_middle_bone"), "main");
	private final ModelPart root;

	public ModelSiamserpentMiddleBone(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition SiamserpentMiddlebone = root.addOrReplaceChild("SiamserpentMiddlebone", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, -17.0F, -8.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(41, 32).addBox(0.0F, -25.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-9.0F, -15.0F, -8.0F, 18.0F, 15.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Heart = SiamserpentMiddlebone.addOrReplaceChild("Heart", CubeListBuilder.create().texOffs(0, 53).addBox(-3.5F, -0.7415F, -3.5F, 7.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(58, 57).addBox(-2.5F, -1.7415F, -4.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(29, 57).addBox(-3.5F, 7.2585F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(61, 68).addBox(-1.5F, -5.7415F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.2585F, -2.0F, 1.5708F, 0.0F, 0.0F));

		Heart.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 68).addBox(-1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 1.7585F, -0.75F, 0.829F, 0.0F, 0.0F));

		Heart.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(29, 67).addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.2415F, -2.25F, -1.2217F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySiamserpentBone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("SiamserpentMiddlebone"), entity.isInvert() ? netHeadYaw + 180.0F : netHeadYaw, entity.isInvert() ? -headPitch : headPitch);
		this.root.getChild("SiamserpentMiddlebone").getChild("Heart").visible = entity.getIndex() == 5;
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