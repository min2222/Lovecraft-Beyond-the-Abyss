package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerTail;
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

public class ModelDuneDevourerTail extends HierarchicalModel<EntityDuneDevourerTail>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "dune_devourer_tail"), "main");
	private final ModelPart root;
	private final ModelPart dune_devourer;

	public ModelDuneDevourerTail(ModelPart root)
	{
		this.root = root.getChild("root");
		this.dune_devourer = this.root.getChild("dune_devourer");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition dune_devourer = root.addOrReplaceChild("dune_devourer", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		dune_devourer.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(544, 0).addBox(-21.0F, -17.0F, -199.0F, 42.0F, 32.0F, 200.0F, new CubeDeformation(0.0F))
		.texOffs(716, 289).addBox(21.0F, -17.0F, -199.0F, 27.0F, 32.0F, 63.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-45.0F, -40.0F, -46.1429F, 0.0F, 0.2182F, 0.0F));

		dune_devourer.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(716, 289).mirror().addBox(-48.0F, -17.0F, -199.0F, 27.0F, 32.0F, 63.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(544, 0).mirror().addBox(-21.0F, -17.0F, -199.0F, 42.0F, 32.0F, 200.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(45.0F, -40.0F, -46.1429F, 0.0F, -0.2182F, 0.0F));

		dune_devourer.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(560, 120).addBox(-117.0F, 33.0F, 0.0F, 44.0F, 30.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(560, 0).addBox(-117.0F, 48.0F, -15.0F, 44.0F, 0.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(320, 518).mirror().addBox(-163.0F, 33.0F, -15.0F, 46.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(532, 420).addBox(-445.0F, 48.0F, -15.0F, 44.0F, 0.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(560, 90).addBox(-445.0F, 33.0F, 0.0F, 44.0F, 30.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(320, 518).addBox(-401.0F, 33.0F, -15.0F, 46.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(259.0F, -40.0F, -77.0F, 1.5708F, 0.0F, 0.0F));

		dune_devourer.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 180).addBox(-100.0F, -2.0F, -40.0F, 200.0F, 100.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, -50.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 1024, 1024);
	}

	@Override
	public void setupAnim(EntityDuneDevourerTail entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.dune_devourer, netHeadYaw, -headPitch);
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