package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelSlasherSkull extends EntityModel<EntitySiamserpentHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "slasher_skull"), "main");
	private final ModelPart SlasherSkull;

	public ModelSlasherSkull(ModelPart root) 
	{
		this.SlasherSkull = root.getChild("SlasherSkull");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("SlasherSkull", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -4.3333F, -3.9167F, 14.0F, 10.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-11.0F, 2.6667F, -3.9167F, 22.0F, 0.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(75, 53).addBox(-7.0F, 5.6667F, -3.9167F, 14.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 87).addBox(0.0F, -7.3333F, 5.0833F, 0.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(75, 80).addBox(-2.0F, -4.3333F, -29.9167F, 4.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -2.3333F, -31.9167F, 22.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.3333F, -11.0833F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntitySiamserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		SlasherSkull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}