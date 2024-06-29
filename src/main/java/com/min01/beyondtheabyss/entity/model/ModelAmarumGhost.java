package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAmarumGhost;
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

public class ModelAmarumGhost extends HierarchicalModel<EntityAmarumGhost>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "amarum_ghost"), "main");
	private final ModelPart root;

	public ModelAmarumGhost(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 27.0F, 0.0F));

		PartDefinition legs = main.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -30.0F, 0.0F));

		PartDefinition l_leg_p = legs.addOrReplaceChild("l_leg_p", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 1.0F, 6.0F, 0.4326F, 0.2149F, -0.4326F));

		l_leg_p.addOrReplaceChild("l_leg_p2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.4363F, 0.0F, -0.3054F));

		PartDefinition l_leg_o = legs.addOrReplaceChild("l_leg_o", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 1.0F, 2.0F, 0.1642F, 0.0594F, -0.5187F));

		l_leg_o.addOrReplaceChild("l_leg_o2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

		PartDefinition l_leg_n = legs.addOrReplaceChild("l_leg_n", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 1.0F, -2.0F, -0.0791F, 0.1188F, -0.3778F));

		l_leg_n.addOrReplaceChild("l_leg_n2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition l_leg_m = legs.addOrReplaceChild("l_leg_m", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 1.0F, -6.0F, -0.2075F, 0.5788F, -0.3674F));

		l_leg_m.addOrReplaceChild("l_leg_m2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition l_leg_l = legs.addOrReplaceChild("l_leg_l", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, 6.0F, 0.3979F, 0.2148F, -0.1787F));

		l_leg_l.addOrReplaceChild("l_leg_l2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition l_leg_k = legs.addOrReplaceChild("l_leg_k", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, 2.0F, 0.3491F, 0.0F, -0.0873F));

		l_leg_k.addOrReplaceChild("l_leg_k2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition l_leg_j = legs.addOrReplaceChild("l_leg_j", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, -2.0F, -0.0718F, 0.3864F, -0.4068F));

		l_leg_j.addOrReplaceChild("l_leg_j2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition l_leg_i = legs.addOrReplaceChild("l_leg_i", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.0F, -6.0F, -0.4034F, -0.2021F, -0.1755F));

		l_leg_i.addOrReplaceChild("l_leg_i2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition l_leg_h = legs.addOrReplaceChild("l_leg_h", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 6.0F, 0.4771F, -0.2577F, 0.1806F));

		l_leg_h.addOrReplaceChild("l_leg_h2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition l_leg_g = legs.addOrReplaceChild("l_leg_g", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 2.0F, 0.2182F, 0.0F, 0.2618F));

		l_leg_g.addOrReplaceChild("l_leg_g2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition l_leg_f = legs.addOrReplaceChild("l_leg_f", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, -2.0F, -0.1298F, 0.017F, 0.1298F));

		l_leg_f.addOrReplaceChild("l_leg_f2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition l_leg_e = legs.addOrReplaceChild("l_leg_e", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, -6.0F, -0.4363F, 0.0F, 0.2618F));

		l_leg_e.addOrReplaceChild("l_leg_e2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition l_leg_d = legs.addOrReplaceChild("l_leg_d", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.0F, 6.0F, 0.2182F, 0.0F, 0.3054F));

		l_leg_d.addOrReplaceChild("l_leg_d2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition l_leg_c = legs.addOrReplaceChild("l_leg_c", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.2618F));

		l_leg_c.addOrReplaceChild("l_leg_c2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition l_leg_b = legs.addOrReplaceChild("l_leg_b", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.0F, -2.0F, -0.1666F, 0.0522F, 0.4756F));

		l_leg_b.addOrReplaceChild("l_leg_b2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition l_leg_a = legs.addOrReplaceChild("l_leg_a", CubeListBuilder.create().texOffs(64, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.0F, -6.0F, 0.0F, 0.0F, 0.1311F));

		l_leg_a.addOrReplaceChild("l_leg_a2", CubeListBuilder.create().texOffs(72, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 14.0F, 0.0F, -0.4363F, 0.0F, 0.1745F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -18.6702F, -7.2164F, 16.0F, 14.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.3298F, -0.7836F));

		head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.6702F, -7.2164F, 0.1745F, 0.0F, 0.0F));

		head.addOrReplaceChild("jelly", CubeListBuilder.create().texOffs(0, 44).addBox(-9.0F, -13.0F, -9.0F, 18.0F, 20.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 82).addBox(-10.0F, -1.0F, -10.0F, 20.0F, 8.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.6702F, 0.7836F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityAmarumGhost entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
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