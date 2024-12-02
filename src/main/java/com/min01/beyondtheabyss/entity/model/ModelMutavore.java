package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.KinematicChain.ChainSegment;
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
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

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

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(94, 83).addBox(-7.5F, 0.0F, -31.0F, 15.0F, 5.0F, 31.0F, new CubeDeformation(0.0F))
		.texOffs(140, 253).addBox(-7.5F, -5.0F, -30.5F, 15.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));

		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 18.0F));

		PartDefinition tongue1 = tongue.addOrReplaceChild("tongue1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tongue2 = tongue1.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

		PartDefinition tongue3 = tongue2.addOrReplaceChild("tongue3", CubeListBuilder.create().texOffs(314, 0).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

		tongue3.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(314, 18).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(334, 30).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, -14.0F));

		tongue3.addOrReplaceChild("tongue_jaw", CubeListBuilder.create().texOffs(330, 18).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(311, 28).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

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

		PartDefinition tentaclePos = mutavore.addOrReplaceChild("tentaclePos", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		tentaclePos.addOrReplaceChild("tentacle16", CubeListBuilder.create(), PartPose.offset(7.0F, -30.0F, 23.0F));

		tentaclePos.addOrReplaceChild("tentacle17", CubeListBuilder.create(), PartPose.offset(-7.0F, -30.0F, 23.0F));

		tentaclePos.addOrReplaceChild("tentacle18", CubeListBuilder.create(), PartPose.offset(-7.0F, -4.0F, 23.0F));

		tentaclePos.addOrReplaceChild("tentacle19", CubeListBuilder.create(), PartPose.offset(7.0F, -4.0F, 23.0F));

		tentaclePos.addOrReplaceChild("tentacle20", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 23.0F));

		PartDefinition tentacles = mutavore.addOrReplaceChild("tentacles", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 46.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tentacle1 = tentacles.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -30.0F, 23.0F));

		PartDefinition tentacle6 = tentacle1.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		PartDefinition tentacle11 = tentacle6.addOrReplaceChild("tentacle11", CubeListBuilder.create().texOffs(274, 288).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(277, 295).addBox(0.0F, -10.0F, -40.0F, 0.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		tentacle11.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(338, 259).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, 2.0F, -17.908F, 0.0F, -0.5672F, 0.0F));

		tentacle11.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(338, 269).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -2.0F, -20.908F, 0.0F, 0.5672F, 0.0F));

		tentacle11.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(338, 249).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, 2.0F, -17.908F, 0.0F, 0.5672F, 0.0F));

		tentacle11.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(338, 239).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -2.0F, -20.908F, 0.0F, -0.5672F, 0.0F));

		PartDefinition tentacle2 = tentacles.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -30.0F, 23.0F));

		PartDefinition tentacle7 = tentacle2.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		PartDefinition tentacle12 = tentacle7.addOrReplaceChild("tentacle12", CubeListBuilder.create().texOffs(274, 288).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(277, 295).addBox(0.0F, -10.0F, -40.0F, 0.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		tentacle12.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(338, 259).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, 2.0F, -17.908F, 0.0F, -0.5672F, 0.0F));

		tentacle12.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(338, 269).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -2.0F, -20.908F, 0.0F, 0.5672F, 0.0F));

		tentacle12.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(338, 249).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, 2.0F, -17.908F, 0.0F, 0.5672F, 0.0F));

		tentacle12.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(338, 239).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -2.0F, -20.908F, 0.0F, -0.5672F, 0.0F));

		PartDefinition tentacle3 = tentacles.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -4.0F, 23.0F));

		PartDefinition tentacle8 = tentacle3.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		PartDefinition tentacle13 = tentacle8.addOrReplaceChild("tentacle13", CubeListBuilder.create().texOffs(274, 288).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(277, 295).addBox(0.0F, -10.0F, -40.0F, 0.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		tentacle13.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(338, 259).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, 2.0F, -17.908F, 0.0F, -0.5672F, 0.0F));

		tentacle13.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(338, 269).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -2.0F, -20.908F, 0.0F, 0.5672F, 0.0F));

		tentacle13.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(338, 249).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, 2.0F, -17.908F, 0.0F, 0.5672F, 0.0F));

		tentacle13.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(338, 239).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -2.0F, -20.908F, 0.0F, -0.5672F, 0.0F));

		PartDefinition tentacle4 = tentacles.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -4.0F, 23.0F));

		PartDefinition tentacle9 = tentacle4.addOrReplaceChild("tentacle9", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		PartDefinition tentacle14 = tentacle9.addOrReplaceChild("tentacle14", CubeListBuilder.create().texOffs(274, 288).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(277, 295).addBox(0.0F, -10.0F, -40.0F, 0.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		tentacle14.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(338, 259).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, 2.0F, -17.908F, 0.0F, -0.5672F, 0.0F));

		tentacle14.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(338, 269).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -2.0F, -20.908F, 0.0F, 0.5672F, 0.0F));

		tentacle14.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(338, 249).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, 2.0F, -17.908F, 0.0F, 0.5672F, 0.0F));

		tentacle14.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(338, 239).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -2.0F, -20.908F, 0.0F, -0.5672F, 0.0F));

		PartDefinition tentacle5 = tentacles.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 23.0F));

		PartDefinition tentacle10 = tentacle5.addOrReplaceChild("tentacle10", CubeListBuilder.create().texOffs(0, 314).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		PartDefinition tentacle15 = tentacle10.addOrReplaceChild("tentacle15", CubeListBuilder.create().texOffs(274, 288).addBox(-4.0F, -4.0F, -28.0F, 8.0F, 8.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(277, 295).addBox(0.0F, -10.0F, -40.0F, 0.0F, 23.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -28.0F));

		tentacle15.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(338, 259).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, 2.0F, -17.908F, 0.0F, -0.5672F, 0.0F));

		tentacle15.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(338, 269).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, -2.0F, -20.908F, 0.0F, 0.5672F, 0.0F));

		tentacle15.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(338, 249).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2312F, 2.0F, -17.908F, 0.0F, 0.5672F, 0.0F));

		tentacle15.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(338, 239).addBox(0.0F, -2.0F, -3.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.2312F, -2.0F, -20.908F, 0.0F, -0.5672F, 0.0F));

		return LayerDefinition.create(meshdefinition, 350, 350);
	}
	
	@Override
	public void setupAnim(EntityMutavore entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("mutavore"), netHeadYaw, headPitch);
		for(int i = 0; i < 5; i++)
		{
			int num0 = 16 + i;
			int num = i + 1;
			int num1 = num + 5;
			int num2 = num1 + 5;
			Vec3 tentaclePos = BTAClientUtil.getWorldPosition(entity, this.root, new Vec3(0.0F, entity.yBodyRot, 0.0F), new String[] {"mutavore", "tentaclePos", "tentacle" + num0});
			entity.posArray[i] = tentaclePos;
			BTANetwork.sendToServer(new UpdatePosArrayPacket(entity, tentaclePos, i));

			ChainSegment[] segments = entity.chains[i].getSegments();
			ModelPart tentacle = this.root.getChild("mutavore").getChild("tentacles");
			ModelPart firstTentacle = tentacle.getChild("tentacle" + num);
			ModelPart secondTentacle = firstTentacle.getChild("tentacle" + num1);
			ModelPart thirdTentacle = secondTentacle.getChild("tentacle" + num2);
			ModelPart[] tentacles = new ModelPart[] {firstTentacle, secondTentacle, thirdTentacle};
			for(int i2 = 0; i2 < segments.length; i2++)
			{
				Vec2 rot = segments[i2].getRot();
				float yRot = i2 > 0 ? tentacles[i2 - 1].yRot : 0.0F;
				float xRot = i2 > 0 ? tentacles[i2 - 1].xRot : 0.0F;
				//TODO edge tentacle yRot is inverted;
				if(i2 < 2)
				{
					tentacles[i2].yRot += Math.toRadians(rot.y - Math.toDegrees(yRot));
					tentacles[i2].xRot += Math.toRadians(rot.x - Math.toDegrees(xRot));
				}
			}
		}
		
		Vec3 tonguePos = BTAClientUtil.getWorldPosition(entity, this.root, new Vec3(0.0F, entity.yBodyRot, 0.0F), new String[] {"mutavore", "head", "jaw", "tongue", "tongue1"});
		entity.posArray[5] = tonguePos;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(entity, tonguePos, 5));
		ModelPart tongue = this.root.getChild("mutavore").getChild("head").getChild("jaw").getChild("tongue");
		ModelPart[] tongues = new ModelPart[] {tongue.getChild("tongue1"), tongue.getChild("tongue1").getChild("tongue2"), tongue.getChild("tongue1").getChild("tongue2").getChild("tongue3")};
		ChainSegment[] segments = entity.tongueChain.getSegments();
		
		for(int i = 0; i < segments.length; i++)
		{
			Vec2 rot = segments[i].getRot();
			float yRot = i > 0 ? tongues[i - 1].yRot : 0.0F;
			float xRot = i > 0 ? tongues[i - 1].xRot : 0.0F;
			tongues[i].yRot += Math.toRadians(rot.y - Math.toDegrees(yRot));
			tongues[i].xRot += Math.toRadians(rot.x - Math.toDegrees(xRot));
		}
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