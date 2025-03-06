package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosHead;
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

public class ModelKormosHead extends HierarchicalModel<EntityKormosHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "kormos_head"), "main");
	private final ModelPart root;

	public ModelKormosHead(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-36.0F, -35.0F, -112.0F, 72.0F, 38.0F, 112.0F, new CubeDeformation(0.0F))
		.texOffs(11, 160).addBox(-25.0F, -34.0F, -90.0F, 50.0F, 59.0F, 90.0F, new CubeDeformation(0.0F))
		.texOffs(456, 421).addBox(-36.0F, 3.0F, -112.0F, 72.0F, 10.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(456, 483).addBox(26.0F, 3.0F, -92.0F, 10.0F, 10.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(0, 521).addBox(-36.0F, 3.0F, -92.0F, 10.0F, 10.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -29.0F, 47.0F));

		head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 365).addBox(-22.0F, -33.0F, -88.0F, 44.0F, 28.0F, 88.0F, new CubeDeformation(0.0F))
		.texOffs(304, 303).addBox(-26.0F, -5.0F, -92.0F, 52.0F, 26.0F, 92.0F, new CubeDeformation(0.0F))
		.texOffs(84, 521).addBox(16.0F, -13.0F, -68.0F, 10.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(456, 451).addBox(-26.0F, -13.0F, -92.0F, 52.0F, 8.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(136, 521).addBox(-26.0F, -13.0F, -68.0F, 10.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, 47.0F));

		return LayerDefinition.create(meshdefinition, 1024, 1024);
	}

	@Override
	public void setupAnim(EntityKormosHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("head"), netHeadYaw, headPitch);
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