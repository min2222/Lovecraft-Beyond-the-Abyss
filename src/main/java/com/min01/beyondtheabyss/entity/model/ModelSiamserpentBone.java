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

public class ModelSiamserpentBone extends HierarchicalModel<EntitySiamserpentBone> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "siamserpent_bone"), "main");
	private final ModelPart root;

	public ModelSiamserpentBone(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition SiamserpentBone = root.addOrReplaceChild("SiamserpentBone", CubeListBuilder.create().texOffs(35, 47).addBox(-2.0F, -6.042F, -7.7143F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 60).addBox(0.0F, -13.042F, -7.7143F, 0.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-9.0F, -4.042F, -7.7143F, 18.0F, 15.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.958F, -0.2857F));

		SiamserpentBone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-16.0F, 0.0F, -8.0F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, 7.958F, 1.2857F, 0.0F, 0.0F, -0.6109F));

		SiamserpentBone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, 0.0F, -8.0F, 16.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 7.958F, 1.2857F, 0.0F, 0.0F, 0.6109F));

		SiamserpentBone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 46).addBox(-12.0F, 0.0F, -7.0F, 12.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -4.042F, 0.2857F, 0.0F, 0.0F, 0.6109F));

		SiamserpentBone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(46, 33).addBox(0.0F, 0.0F, -7.0F, 12.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -4.042F, 0.2857F, 0.0F, 0.0F, -0.6109F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySiamserpentBone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("SiamserpentBone"), entity.shouldInvertRotation() ? netHeadYaw + 180.0F : netHeadYaw, headPitch);
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