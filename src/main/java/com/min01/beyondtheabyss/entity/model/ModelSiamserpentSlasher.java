package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
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

public class ModelSiamserpentSlasher extends HierarchicalModel<EntitySiamserpentHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "siamserpent_slasher"), "main");
	private final ModelPart root;

	public ModelSiamserpentSlasher(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		root.addOrReplaceChild("SiamserpentSlasher", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -8.0F, -11.5F, 14.0F, 10.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-11.0F, -1.0F, -11.5F, 22.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(52, 64).addBox(-7.0F, 2.0F, -11.5F, 14.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(73, 0).addBox(0.0F, -11.0F, -2.5F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(65, 29).addBox(-2.0F, -8.0F, -37.5F, 4.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -6.0F, -39.5F, 22.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySiamserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("SiamserpentSlasher"), entity.shouldInvertRotation() ? netHeadYaw + 180.0F : netHeadYaw, headPitch);
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