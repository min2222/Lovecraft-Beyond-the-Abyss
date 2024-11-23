package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavoreTentacle;
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

public class ModelMutavoreTentacleEdge extends HierarchicalModel<EntityMutavoreTentacle>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "mutavore_tentacle_edge"), "main");
	private final ModelPart root;

	public ModelMutavoreTentacleEdge(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacle = root.addOrReplaceChild("tentacle", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0F, -8.0F, -20.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(3, 56).addBox(0.0F, -14.0F, -32.0F, 0.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -8.0F));

		tentacle.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 20).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -2.0F, -9.7759F, 0.0F, -0.5672F, 0.0F));

		tentacle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 30).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -6.0F, -12.7759F, 0.0F, 0.5672F, 0.0F));

		tentacle.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 10).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -2.0F, -9.7759F, 0.0F, 0.5672F, 0.0F));

		tentacle.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -6.0F, -12.7759F, 0.0F, -0.5672F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityMutavoreTentacle entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root, netHeadYaw, -headPitch);
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