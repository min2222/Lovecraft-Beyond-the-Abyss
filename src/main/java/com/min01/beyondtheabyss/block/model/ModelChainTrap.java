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
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "chain_trap"), "main");
	private final ModelPart root;

	public ModelChainTrap(ModelPart root)
	{
		super(RenderType::entityCutoutNoCull);
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition ChainTrap = root.addOrReplaceChild("ChainTrap", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		ChainTrap.addOrReplaceChild("Base", CubeListBuilder.create().texOffs(0, 25).addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-8.0F, -4.0F, -8.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(65, 25).addBox(-5.0F, -4.0F, 5.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(59, 70).addBox(-1.5F, -5.0F, 6.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(72, 70).addBox(-1.5F, -5.0F, -9.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(65, 31).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(39, 44).addBox(5.0F, -4.0F, -8.0F, 3.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-12.0F, -0.1F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Mandible = ChainTrap.addOrReplaceChild("Mandible", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition Right = Mandible.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(65, 37).addBox(-9.3333F, -1.6667F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.6667F, -0.0833F, 0.0F, 0.0F, 0.0F, 1.0472F));

		PartDefinition Rightmandible = Right.addOrReplaceChild("Rightmandible", CubeListBuilder.create().texOffs(25, 70).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.3333F, -0.1667F, 0.0F, 0.0F, 0.0F, -0.8727F));

		Rightmandible.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(59, 77).addBox(-12.2998F, -4.2438F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(0, 63).addBox(-12.2998F, -0.2438F, -1.5F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(43, 63).addBox(-12.2998F, -0.2438F, -1.5F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(0, 77).addBox(-12.2998F, -4.2438F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(72, 77).addBox(-12.2998F, -6.2438F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
		.texOffs(78, 44).addBox(-0.2998F, -2.2438F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(13, 77).addBox(-4.2998F, -3.2438F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3565F, -4.6701F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition Left = Mandible.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(0, 70).addBox(0.3333F, -1.6667F, -1.5F, 9.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.6667F, -0.0833F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition Leftmandible = Left.addOrReplaceChild("Leftmandible", CubeListBuilder.create().texOffs(42, 70).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.3333F, -0.1667F, 0.0F, 0.0F, 0.0F, 0.8727F));

		Leftmandible.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(0, 63).mirror().addBox(-7.8812F, -19.7244F, -1.9F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(43, 63).mirror().addBox(-7.8812F, -19.7244F, -1.9F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 77).mirror().addBox(6.1188F, -23.7244F, -1.4F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(59, 77).mirror().addBox(6.1188F, -23.7244F, -1.4F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(72, 77).mirror().addBox(6.1188F, -25.7244F, -1.4F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(78, 44).mirror().addBox(-2.8812F, -21.7244F, -1.9F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(13, 77).mirror().addBox(1.1188F, -22.7244F, -1.9F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(18.1244F, -6.9812F, 0.4F, 0.0F, 0.0F, -1.5708F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}