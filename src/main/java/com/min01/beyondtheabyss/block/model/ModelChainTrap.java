package com.min01.beyondtheabyss.block.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ModelChainTrap extends Model
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "chain_trap"), "main");
	private final ModelPart ChainTrap;

	public ModelChainTrap(ModelPart root)
	{
		super(RenderType::entityCutoutNoCull);
		this.ChainTrap = root.getChild("ChainTrap");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ChainTrap = partdefinition.addOrReplaceChild("ChainTrap", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		ChainTrap.addOrReplaceChild("Base", CubeListBuilder.create().texOffs(0, 25).addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-8.0F, -4.0F, -8.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(34, 63).addBox(-5.0F, -4.0F, 5.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(68, 69).addBox(-1.5F, -5.0F, 6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(68, 76).addBox(-1.5F, -5.0F, -9.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(61, 63).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(39, 44).addBox(5.0F, -4.0F, -8.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-12.0F, -0.01F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Mandible = ChainTrap.addOrReplaceChild("Mandible", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition Right = Mandible.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(65, 25).addBox(-9.3333F, -1.6667F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.6667F, -0.0833F, 0.0F, 0.0F, 0.0F, 1.0472F));

		Right.addOrReplaceChild("Rightmandible", CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -18.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(34, 69).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.3333F, -0.1667F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition Left = Mandible.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(65, 32).addBox(0.3333F, -1.6667F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.6667F, -0.0833F, 0.0F, 0.0F, 0.0F, -1.0472F));

		Left.addOrReplaceChild("Leftmandible", CubeListBuilder.create().texOffs(17, 63).addBox(-6.5F, -18.0F, 0.0F, 8.0F, 18.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(51, 69).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.3333F, -0.1667F, 0.0F, 0.0F, 0.0F, 0.8727F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		ChainTrap.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}