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

public class LargeSkullModel extends Model
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "large_skull"), "main");
	private final ModelPart largeskull;

	public LargeSkullModel(ModelPart root) 
	{
		super(RenderType::entityCutoutNoCull);
		this.largeskull = root.getChild("largeskull");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition largeskull = partdefinition.addOrReplaceChild("largeskull", CubeListBuilder.create().texOffs(0, 22).addBox(-3.5F, -0.25F, -6.5F, 7.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(28, 26).addBox(-3.5F, -2.25F, -6.5F, 7.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.25F, -0.5F));

		largeskull.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -15.0F, 7.0F, 6.0F, 15.0F, new CubeDeformation(0.01F))
		.texOffs(32, 9).addBox(-3.5F, 0.0F, -13.0F, 7.0F, 2.0F, 13.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 2.75F, 6.5F, -0.7418F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		largeskull.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}