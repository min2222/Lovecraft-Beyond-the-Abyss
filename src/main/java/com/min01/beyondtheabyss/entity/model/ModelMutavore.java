package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
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

public class ModelMutavore extends HierarchicalModel<EntityMutavore>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "mutavore"), "main");
	private final ModelPart root;

	public ModelMutavore(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition mutavore = root.addOrReplaceChild("mutavore", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -34.0F, -24.0F, 22.0F, 34.0F, 47.0F, new CubeDeformation(0.0F))
		.texOffs(12, 195).addBox(0.0F, -46.0F, -22.0F, 0.0F, 12.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		mutavore.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 222).mirror().addBox(0.0F, -11.0F, 1.0F, 0.0F, 12.0F, 43.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -35.0F, -23.0F, 0.0F, 0.0F, 0.4363F));

		mutavore.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 222).addBox(0.0F, -11.0F, 1.0F, 0.0F, 12.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -35.0F, -23.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition head = mutavore.addOrReplaceChild("head", CubeListBuilder.create().texOffs(3, 138).addBox(-8.0F, 1.0F, -30.0F, 16.0F, 6.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 83).addBox(-8.0F, -12.0F, -30.0F, 16.0F, 13.0F, 31.0F, new CubeDeformation(0.0F))
		.texOffs(94, 119).addBox(-8.0F, 1.0F, -9.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -25.0F));

		head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(94, 83).addBox(-7.5F, 0.0F, -31.0F, 15.0F, 5.0F, 31.0F, new CubeDeformation(0.0F))
		.texOffs(140, 253).addBox(-7.5F, -5.0F, -30.5F, 15.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));

		PartDefinition left_head_fin = head.addOrReplaceChild("left_head_fin", CubeListBuilder.create(), PartPose.offset(7.0F, -11.0F, -4.0F));

		left_head_fin.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(160, 33).addBox(-1.0F, -4.0F, 0.0F, 40.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4621F, 2.1942F, 1.5363F, 0.2618F, -0.3491F, -0.4363F));

		left_head_fin.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(157, 14).addBox(-1.0F, -2.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.2618F, -0.3491F, -0.4363F));

		PartDefinition right_head_fin = head.addOrReplaceChild("right_head_fin", CubeListBuilder.create(), PartPose.offset(-7.0F, -11.0F, -4.0F));

		right_head_fin.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(157, 14).mirror().addBox(-23.0F, -2.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.2618F, 0.3491F, 0.4363F));

		right_head_fin.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(160, 33).mirror().addBox(-39.0F, -4.0F, 0.0F, 40.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.4621F, 2.1942F, 1.5363F, 0.2618F, 0.3491F, 0.4363F));

		mutavore.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(90, 157).addBox(0.0F, -2.0F, -5.0F, 19.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(140, 130).addBox(6.0F, 0.0F, -5.0F, 42.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -4.0F, -16.0F));

		mutavore.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(140, 130).mirror().addBox(-48.0F, 0.0F, -5.0F, 42.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(90, 157).mirror().addBox(-19.0F, -2.0F, -5.0F, 19.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.0F, -4.0F, -16.0F));

		mutavore.addOrReplaceChild("left_small_fin", CubeListBuilder.create().texOffs(156, 218).addBox(0.0F, -2.0F, -3.0F, 9.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(195, 217).addBox(5.0F, 0.0F, -3.0F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -4.0F, 18.0F));

		mutavore.addOrReplaceChild("right_small_fin", CubeListBuilder.create().texOffs(195, 217).mirror().addBox(-21.0F, 0.0F, -3.0F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(156, 218).mirror().addBox(-9.0F, -2.0F, -3.0F, 9.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.0F, -4.0F, 18.0F));

		PartDefinition mutate1 = mutavore.addOrReplaceChild("mutate1", CubeListBuilder.create(), PartPose.offset(-11.0F, -24.0F, 12.0F));

		mutate1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(88, 273).addBox(-5.0F, -10.0F, -5.0F, 5.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0873F, 0.0F, 0.1309F));

		PartDefinition mutate2 = mutavore.addOrReplaceChild("mutate2", CubeListBuilder.create(), PartPose.offset(11.0F, -28.0F, -11.0F));

		mutate2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(244, 59).addBox(-4.0F, -7.0F, -9.0F, 8.0F, 15.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tentacles = mutavore.addOrReplaceChild("tentacles", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		tentacles.addOrReplaceChild("tentacle1", CubeListBuilder.create(), PartPose.offset(7.0F, -26.0F, 23.0F));

		tentacles.addOrReplaceChild("tentacle2", CubeListBuilder.create(), PartPose.offset(-7.0F, -26.0F, 23.0F));

		tentacles.addOrReplaceChild("tentacle3", CubeListBuilder.create(), PartPose.offset(-7.0F, 0.0F, 23.0F));

		tentacles.addOrReplaceChild("tentacle4", CubeListBuilder.create(), PartPose.offset(7.0F, 0.0F, 23.0F));

		tentacles.addOrReplaceChild("tentacle5", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 23.0F));

		return LayerDefinition.create(meshdefinition, 350, 350);
	}
	
	@Override
	public void setupAnim(EntityMutavore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("mutavore"), netHeadYaw, headPitch);
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