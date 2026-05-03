package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.FulgastraAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.FulgastraEntity;
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

public class FulgastraModel extends HierarchicalModel<FulgastraEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "fulgastra"), "main");
	private final ModelPart root;

	public FulgastraModel(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Fulgastra = root.addOrReplaceChild("Fulgastra", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 0.0F));

		PartDefinition Center = Fulgastra.addOrReplaceChild("Center", CubeListBuilder.create().texOffs(105, 187).addBox(-13.0F, 0.25F, -13.0F, 26.0F, 3.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(105, 156).addBox(-13.0F, 15.25F, -13.0F, 26.0F, 4.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-13.0F, 3.25F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.25F, 0.0F));

		PartDefinition Tentacles = Center.addOrReplaceChild("Tentacles", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		Tentacles.addOrReplaceChild("Tentacle", CubeListBuilder.create().texOffs(53, 253).addBox(-13.0F, 0.0F, 0.0F, 26.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.25F, -11.0F));

		Tentacles.addOrReplaceChild("Tentacle2", CubeListBuilder.create().texOffs(210, 44).addBox(1.0F, -2.0F, -1.0F, 0.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 21.25F, -11.999F));

		Tentacles.addOrReplaceChild("Tentacle3", CubeListBuilder.create().texOffs(53, 253).addBox(-13.0F, 0.0F, 0.0F, 26.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.25F, 11.0F));

		Tentacles.addOrReplaceChild("Tentacle4", CubeListBuilder.create().texOffs(210, 44).mirror().addBox(-1.0F, -2.0F, -1.0F, 0.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, 21.25F, -11.999F));

		PartDefinition Bulb = Center.addOrReplaceChild("Bulb", CubeListBuilder.create().texOffs(261, 94).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		Bulb.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(263, 42).addBox(0.0F, 0.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		Bulb.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(263, 34).addBox(0.0F, -1.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		Bulb.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(263, 26).addBox(0.0F, 0.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		Bulb.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(263, 18).addBox(0.0F, -1.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		Bulb.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(263, 50).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, -3.0F, 0.0F, 0.7854F, 0.0F));

		Bulb.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(206, 231).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, -3.0F, 0.0F, -0.7854F, 0.0F));

		Bulb.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(206, 224).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 3.0F, 0.0F, -0.7854F, 0.0F));

		Bulb.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(206, 217).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 3.0F, 0.0F, 0.7854F, 0.0F));

		Bulb.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(157, 253).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -3.0F, -0.7854F, 0.0F, 0.0F));

		Bulb.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(79, 250).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.7854F, 0.0F, 0.0F));

		Bulb.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(66, 250).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

		Bulb.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(53, 250).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 3.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition Front = Fulgastra.addOrReplaceChild("Front", CubeListBuilder.create().texOffs(0, 39).addBox(-13.0F, -2.5F, -26.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -13.0F));

		Front.addOrReplaceChild("FrontEye", CubeListBuilder.create().texOffs(261, 209).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, -25.0F));

		Front.addOrReplaceChild("FrontFlipper", CubeListBuilder.create().texOffs(0, 239).addBox(-13.0F, 0.0F, 0.0F, 26.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.5F, -24.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition Back = Fulgastra.addOrReplaceChild("Back", CubeListBuilder.create().texOffs(105, 78).addBox(-13.0F, -2.5F, 0.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 13.0F));

		Back.addOrReplaceChild("BackEye", CubeListBuilder.create().texOffs(261, 218).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 25.0F));

		Back.addOrReplaceChild("BackFlipper", CubeListBuilder.create().texOffs(206, 239).addBox(-13.0F, 0.0F, 0.0F, 26.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.5F, 24.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition Left = Fulgastra.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(105, 39).addBox(-1.0F, -2.5F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, -2.5F, 0.0F));

		Left.addOrReplaceChild("LeftEye", CubeListBuilder.create().texOffs(261, 107).addBox(0.0F, -4.0F, -4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, 3.5F, 0.0F));

		Left.addOrReplaceChild("LeftFlipper", CubeListBuilder.create().texOffs(0, 195).addBox(0.0F, 0.0F, -13.0F, 0.0F, 17.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, 9.5F, 0.0F, 0.0F, 0.0F, -0.6109F));

		PartDefinition Right = Fulgastra.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(0, 156).addBox(-25.0F, -2.5F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, -2.5F, 0.0F));

		Right.addOrReplaceChild("Eye", CubeListBuilder.create().texOffs(261, 124).addBox(0.0F, -4.0F, -4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 3.5F, 0.0F));

		Right.addOrReplaceChild("RightFlipper", CubeListBuilder.create().texOffs(210, 0).addBox(0.0F, 0.0F, -13.0F, 0.0F, 17.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.0F, 9.5F, 0.0F, 0.0F, 0.0F, 0.6109F));

		PartDefinition Frontleft = Fulgastra.addOrReplaceChild("Frontleft", CubeListBuilder.create().texOffs(0, 78).addBox(0.0F, -2.5F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -2.5F, -26.0F));

		Frontleft.addOrReplaceChild("FrontleftEye", CubeListBuilder.create().texOffs(259, 261).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 3.5F, -12.0F));

		PartDefinition FronteftFlipper = Frontleft.addOrReplaceChild("FronteftFlipper", CubeListBuilder.create(), PartPose.offset(-13.0F, 22.5F, 26.0F));

		PartDefinition FlipperFrontleft = FronteftFlipper.addOrReplaceChild("FlipperFrontleft", CubeListBuilder.create().texOffs(259, 239).addBox(37.502F, 0.0F, 0.0F, 25.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.501F, -13.0F, -38.0F, -0.5236F, 0.0F, 0.0F));

		FlipperFrontleft.addOrReplaceChild("TentacleFrontleftdown", CubeListBuilder.create().texOffs(106, 253).addBox(37.502F, 0.0F, 0.0F, 25.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition FlipperFrontleft2 = FronteftFlipper.addOrReplaceChild("FlipperFrontleft2", CubeListBuilder.create().texOffs(210, 203).mirror().addBox(0.0F, 0.0F, -12.5F, 0.0F, 10.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(38.0F, -13.0F, -25.5F, 0.0F, 0.0F, -0.5236F));

		FlipperFrontleft2.addOrReplaceChild("TentacleFrontleftdown2", CubeListBuilder.create().texOffs(210, 83).addBox(0.0F, 0.0F, -12.5F, 0.0F, 14.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		Frontleft.addOrReplaceChild("FrontleftEye2", CubeListBuilder.create().texOffs(261, 158).addBox(0.0F, -4.0F, -4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(25.0F, 3.5F, 0.0F));

		PartDefinition Backleft = Fulgastra.addOrReplaceChild("Backleft", CubeListBuilder.create().texOffs(0, 117).addBox(0.0F, -2.5F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -2.5F, 26.0F));

		Backleft.addOrReplaceChild("BackleftEye", CubeListBuilder.create().texOffs(263, 9).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 3.5F, 12.0F));

		Backleft.addOrReplaceChild("BackleftEye2", CubeListBuilder.create().texOffs(261, 192).addBox(0.0F, -4.0F, -4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(25.0F, 3.5F, 0.0F));

		PartDefinition BackeftFlipper = Backleft.addOrReplaceChild("BackeftFlipper", CubeListBuilder.create(), PartPose.offset(-13.0F, 22.5F, -26.0F));

		PartDefinition FlipperBackleft = BackeftFlipper.addOrReplaceChild("FlipperBackleft", CubeListBuilder.create().texOffs(261, 83).addBox(-12.5F, 0.0F, 0.0F, 25.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.501F, -13.0F, 38.0F, 0.5236F, 0.0F, 0.0F));

		FlipperBackleft.addOrReplaceChild("TentacleBackleftdown", CubeListBuilder.create().texOffs(157, 257).addBox(-12.5F, 0.0F, 0.0F, 25.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition FlipperBackleft2 = BackeftFlipper.addOrReplaceChild("FlipperBackleft2", CubeListBuilder.create().texOffs(155, 217).addBox(0.0F, 0.0F, -12.5F, 0.0F, 10.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(38.0F, -13.0F, 25.5F, 0.0F, 0.0F, -0.5236F));

		FlipperBackleft2.addOrReplaceChild("TentacleBackleftdown2", CubeListBuilder.create().texOffs(210, 163).addBox(0.0F, 0.0F, -12.5F, 0.0F, 14.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition Frontright = Fulgastra.addOrReplaceChild("Frontright", CubeListBuilder.create().texOffs(105, 0).addBox(-26.0F, -2.5F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -2.5F, -26.0F));

		Frontright.addOrReplaceChild("FrontrightEye", CubeListBuilder.create().texOffs(261, 227).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 3.5F, -12.0F));

		PartDefinition FrontrightFlipper = Frontright.addOrReplaceChild("FrontrightFlipper", CubeListBuilder.create(), PartPose.offset(13.0F, 22.5F, 26.0F));

		PartDefinition FlipperFrontright = FrontrightFlipper.addOrReplaceChild("FlipperFrontright", CubeListBuilder.create().texOffs(208, 257).addBox(-13.5F, 0.0F, 0.0F, 25.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.501F, -13.0F, -38.0F, -0.5236F, 0.0F, 0.0F));

		FlipperFrontright.addOrReplaceChild("TentacleFrontrightdown", CubeListBuilder.create().texOffs(53, 235).addBox(-13.5F, 0.0F, 0.0F, 25.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition FlipperFrontright2 = FrontrightFlipper.addOrReplaceChild("FlipperFrontright2", CubeListBuilder.create().texOffs(210, 203).addBox(-1.0F, 0.0F, -12.5F, 0.0F, 10.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-37.0F, -13.0F, -25.5F, 0.0F, 0.0F, 0.5236F));

		FlipperFrontright2.addOrReplaceChild("TentacleFrontrightdown2", CubeListBuilder.create().texOffs(53, 195).addBox(-1.0F, 0.0F, -12.5F, 0.0F, 14.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		Frontright.addOrReplaceChild("FrontrightEye2", CubeListBuilder.create().texOffs(261, 141).addBox(-28.0F, -7.0F, -28.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 6.5F, 24.0F));

		PartDefinition Backright = Fulgastra.addOrReplaceChild("Backright", CubeListBuilder.create().texOffs(105, 117).addBox(-26.0F, -2.5F, -13.0F, 26.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -2.5F, 26.0F));

		Backright.addOrReplaceChild("BackrightEye", CubeListBuilder.create().texOffs(263, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 3.5F, 12.0F));

		Backright.addOrReplaceChild("BackrightEye2", CubeListBuilder.create().texOffs(261, 175).addBox(0.0F, -4.0F, -4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-25.0F, 3.5F, 0.0F));

		PartDefinition BackrightFlipper = Backright.addOrReplaceChild("BackrightFlipper", CubeListBuilder.create(), PartPose.offset(13.0F, 22.5F, -26.0F));

		PartDefinition FlipperBackright = BackrightFlipper.addOrReplaceChild("FlipperBackright", CubeListBuilder.create().texOffs(259, 250).addBox(-13.5F, 0.0F, 0.0F, 25.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.501F, -13.0F, 38.0F, 0.5236F, 0.0F, 0.0F));

		FlipperBackright.addOrReplaceChild("TentacleBackrightdown", CubeListBuilder.create().texOffs(0, 257).addBox(-13.5F, 0.0F, 0.0F, 25.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition FlipperBackright2 = BackrightFlipper.addOrReplaceChild("FlipperBackright2", CubeListBuilder.create().texOffs(104, 217).addBox(-1.0F, 0.0F, -12.5F, 0.0F, 10.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-37.0F, -13.0F, 25.5F, 0.0F, 0.0F, 0.5236F));

		FlipperBackright2.addOrReplaceChild("TentacleBackrightdown2", CubeListBuilder.create().texOffs(210, 123).addBox(-1.0F, 0.0F, -12.5F, 0.0F, 14.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(FulgastraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		entity.splittingAnimationState.animate(this, FulgastraAnimation.FULGASTRA_SPLITTING, ageInTicks);
		entity.reformingAnimationState.animate(this, FulgastraAnimation.FULGASTRA_REFORMING, ageInTicks);
		this.animateWalk(FulgastraAnimation.FULGASTRA_SWIM, limbSwing, limbSwingAmount, 2.5F, 2.5F);
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