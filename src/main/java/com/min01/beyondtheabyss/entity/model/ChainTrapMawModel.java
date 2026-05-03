package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ChainTrapMawEntity;
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

public class ChainTrapMawModel extends HierarchicalModel<ChainTrapMawEntity> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "chain_trap_maw"), "main");
	private final ModelPart ChainMaw;

	public ChainTrapMawModel(ModelPart root)
	{
		this.ChainMaw = root.getChild("ChainMaw");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ChainMaw = partdefinition.addOrReplaceChild("ChainMaw", CubeListBuilder.create(), PartPose.offset(0.0F, 21.5F, 0.0F));

		PartDefinition Maw = ChainMaw.addOrReplaceChild("Maw", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -4.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

		Maw.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(11, 10).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3536F, -1.0F, -0.3536F, 0.0F, 0.7854F, 0.0F));

		Maw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(11, 10).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3536F, -1.0F, 0.3536F, 0.0F, -0.7854F, 0.0F));

		Maw.addOrReplaceChild("Maw1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.25F, -3.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -2.5F, 0.0F, 0.0F, 0.0F, -0.6545F));

		Maw.addOrReplaceChild("Maw2", CubeListBuilder.create().texOffs(0, 5).addBox(-6.75F, -3.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -2.5F, 0.0F, 0.0F, 0.0F, 0.6109F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(ChainTrapMawEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.ChainMaw;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		ChainMaw.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}