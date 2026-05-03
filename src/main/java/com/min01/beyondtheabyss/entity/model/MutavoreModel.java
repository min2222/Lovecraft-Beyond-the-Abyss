package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.MutavoreAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity.MutationType;
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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class MutavoreModel extends HierarchicalModel<MutavoreEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "mutavore"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart mound;
	private final ModelPart chunk2;
	private final ModelPart mine3;
	private final ModelPart mine2;
	private final ModelPart chunk3;
	private final ModelPart mound_head;
	private final ModelPart mine;
	private final ModelPart mine4;
	private final ModelPart tentacle;
	private final ModelPart tentacle_segment;
	private final ModelPart tentacle2;
	private final ModelPart tentacle_segment2;
	private final ModelPart tentacle3;
	private final ModelPart tentacle_segment3;
	private final ModelPart tentacle4;
	private final ModelPart tentacle_segment4;
	private final ModelPart tentacle5;
	private final ModelPart tentacle_segment5;

	public MutavoreModel(ModelPart root)
	{
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.mound = this.body.getChild("mound");
		this.chunk2 = this.mound.getChild("chunk2");
		this.mine3 = this.chunk2.getChild("mine3");
		this.mine2 = this.chunk2.getChild("mine2");
		this.chunk3 = this.mound.getChild("chunk3");
		this.mound_head = this.chunk3.getChild("mound_head");
		this.mine = this.chunk3.getChild("mine");
		this.mine4 = this.chunk3.getChild("mine4");
		this.tentacle = this.mound.getChild("tentacle");
		this.tentacle_segment = this.tentacle.getChild("tentacle_segment");
		this.tentacle2 = this.mound.getChild("tentacle2");
		this.tentacle_segment2 = this.tentacle2.getChild("tentacle_segment2");
		this.tentacle3 = this.mound.getChild("tentacle3");
		this.tentacle_segment3 = this.tentacle3.getChild("tentacle_segment3");
		this.tentacle4 = this.mound.getChild("tentacle4");
		this.tentacle_segment4 = this.tentacle4.getChild("tentacle_segment4");
		this.tentacle5 = this.mound.getChild("tentacle5");
		this.tentacle_segment5 = this.tentacle5.getChild("tentacle_segment5");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 111).addBox(0.0F, -22.0F, -22.0F, 0.0F, 14.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 123).addBox(-8.5F, -7.0F, -4.0F, 17.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -20.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 208).addBox(-8.0F, -2.0F, -5.0F, 16.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(182, 78).addBox(-6.0F, -2.0F, -23.0F, 12.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(186, 185).addBox(-6.0F, 5.0F, -23.0F, 12.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 112).addBox(-8.0F, 0.0F, -5.0F, 16.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(244, 0).addBox(-8.0F, 0.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(252, 254).addBox(-7.0F, 0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(244, 0).mirror().addBox(5.0F, 0.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(252, 254).mirror().addBox(6.0F, 0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -4.0F, -7.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(50, 212).addBox(-10.0F, -3.0F, -4.0F, 20.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 161).addBox(-8.0F, -2.0F, -23.0F, 16.0F, 6.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(182, 55).addBox(-8.0F, -6.0F, -23.0F, 16.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -3.0F));

		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -1.0F));

		tongue.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(4, 64).addBox(-1.5F, -2.0F, 3.0F, 3.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -16.0F));

		tongue.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 23).addBox(-2.5F, -1.5F, -6.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -11.0F));

		tongue.addOrReplaceChild("mouth2", CubeListBuilder.create().texOffs(0, 9).addBox(-2.5F, -0.5F, -6.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -11.0F));

		head.addOrReplaceChild("right_head_fin", CubeListBuilder.create().texOffs(72, 124).addBox(-8.0F, -2.0F, -1.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(180, 136).addBox(-20.0F, -2.0F, 0.0F, 20.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 0.0F));

		head.addOrReplaceChild("left_head_fin", CubeListBuilder.create().texOffs(180, 136).mirror().addBox(0.0F, -2.0F, 0.0F, 20.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(72, 124).mirror().addBox(0.0F, -2.0F, -1.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-18.0F, 16.0F, 1.0F));

		right_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-36.0F, -8.0F, -16.0F, 20.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(18.0F, 4.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-15.0F, 0.0F, 13.0F));

		right_hand.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(228, 28).addBox(-54.0F, -10.0F, -16.0F, 10.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(41.0F, 4.0F, -21.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_finger = right_hand.addOrReplaceChild("right_finger", CubeListBuilder.create(), PartPose.offset(-5.0F, -3.0F, 6.0F));

		right_finger.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-64.0F, -10.0F, -11.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 249).addBox(-64.0F, -10.0F, -15.0F, 13.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(46.0F, 8.0F, -27.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_finger2 = right_hand.addOrReplaceChild("right_finger2", CubeListBuilder.create(), PartPose.offset(-5.0F, 4.0F, 6.0F));

		right_finger2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-64.0F, -10.0F, -11.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 249).addBox(-64.0F, -10.0F, -15.0F, 13.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(46.0F, 8.0F, -27.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_finger_webs = right_hand.addOrReplaceChild("right_finger_webs", CubeListBuilder.create(), PartPose.offset(-7.0F, 0.0F, 7.0F));

		right_finger_webs.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(242, 174).addBox(-61.0F, -10.0F, -14.0F, 7.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(48.0F, 4.0F, -28.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(18.0F, 16.0F, 1.0F));

		left_arm.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(96, 0).addBox(16.0F, -8.0F, -16.0F, 20.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, 4.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(15.0F, 0.0F, 13.0F));

		left_hand.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(228, 28).mirror().addBox(44.0F, -10.0F, -16.0F, 10.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-41.0F, 4.0F, -21.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition left_finger = left_hand.addOrReplaceChild("left_finger", CubeListBuilder.create(), PartPose.offset(5.0F, -3.0F, 6.0F));

		left_finger.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(61.0F, -10.0F, -11.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 249).mirror().addBox(51.0F, -10.0F, -15.0F, 13.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-46.0F, 8.0F, -27.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition left_finger2 = left_hand.addOrReplaceChild("left_finger2", CubeListBuilder.create(), PartPose.offset(5.0F, 4.0F, 6.0F));

		left_finger2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(61.0F, -10.0F, -11.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 249).mirror().addBox(51.0F, -10.0F, -15.0F, 13.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-46.0F, 8.0F, -27.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition left_finger_webs = left_hand.addOrReplaceChild("left_finger_webs", CubeListBuilder.create(), PartPose.offset(7.0F, 0.0F, 7.0F));

		left_finger_webs.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(242, 174).mirror().addBox(54.0F, -10.0F, -14.0F, 7.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-48.0F, 4.0F, -28.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition mound = body.addOrReplaceChild("mound", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 18.0F));

		PartDefinition chunk = mound.addOrReplaceChild("chunk", CubeListBuilder.create().texOffs(204, 0).addBox(-10.0F, -12.0F, -3.0F, 16.0F, 21.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 0.0F, 5.0F));

		chunk.addOrReplaceChild("entrails", CubeListBuilder.create().texOffs(224, 136).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -6.0F, 4.0F));

		chunk.addOrReplaceChild("entrails2", CubeListBuilder.create().texOffs(104, 214).addBox(0.0F, -5.0F, 1.0F, 0.0F, 16.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 6.0F, 3.0F));

		PartDefinition chunk2 = mound.addOrReplaceChild("chunk2", CubeListBuilder.create().texOffs(128, 185).addBox(-9.0F, -8.0F, -7.0F, 17.0F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -9.0F, -4.0F));

		chunk2.addOrReplaceChild("mine3", CubeListBuilder.create().texOffs(55, 64).addBox(-5.0F, -5.0F, -3.0F, 10.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(-2.0F, -8.0F, -6.0F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(3.0F, -8.0F, -6.0F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -7.0F, 0.0F));

		chunk2.addOrReplaceChild("mine2", CubeListBuilder.create().texOffs(224, 79).mirror().addBox(-4.0F, -2.0F, -3.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(115, 39).addBox(-7.0F, 1.0F, -6.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(115, 39).mirror().addBox(-7.0F, 5.0F, -6.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -2.0F, 3.0F));

		PartDefinition chunk3 = mound.addOrReplaceChild("chunk3", CubeListBuilder.create().texOffs(0, 186).addBox(-5.0F, -15.0F, -7.0F, 16.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(72, 149).addBox(-13.0F, -5.0F, -5.0F, 27.0F, 20.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		chunk3.addOrReplaceChild("mound_head", CubeListBuilder.create().texOffs(224, 158).addBox(-3.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -3.0F, 0.0F));

		chunk3.addOrReplaceChild("mine", CubeListBuilder.create().texOffs(224, 79).addBox(0.0F, -3.0F, -6.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(115, 39).mirror().addBox(-3.0F, 4.0F, -9.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(115, 39).addBox(-3.0F, 0.0F, -9.0F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 7.0F, 0.0F));

		chunk3.addOrReplaceChild("mine4", CubeListBuilder.create().texOffs(56, 91).addBox(-4.0F, -1.0F, -3.0F, 10.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(-2.0F, -4.0F, -6.0F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(3.0F, -4.0F, -6.0F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 12.0F, 5.0F));

		PartDefinition tentacle = mound.addOrReplaceChild("tentacle", CubeListBuilder.create().texOffs(152, 149).mirror().addBox(-2.0F, -3.0F, 0.0F, 5.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, -7.0F, 9.0F));

		PartDefinition tentacle_segment = tentacle.addOrReplaceChild("tentacle_segment", CubeListBuilder.create().texOffs(96, 106).mirror().addBox(-2.0F, -2.0F, 0.0F, 3.0F, 4.0F, 39.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.0F, 31.0F));

		tentacle_segment.addOrReplaceChild("tentacle_ball", CubeListBuilder.create().texOffs(72, 111).mirror().addBox(-1.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 39.0F));

		PartDefinition tentacle2 = mound.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(152, 149).addBox(-2.0F, -3.0F, 0.0F, 5.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -5.0F, 1.0F));

		PartDefinition tentacle_segment2 = tentacle2.addOrReplaceChild("tentacle_segment2", CubeListBuilder.create().texOffs(96, 106).addBox(0.0F, -2.0F, 0.0F, 3.0F, 4.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 31.0F));

		tentacle_segment2.addOrReplaceChild("tentacle_ball2", CubeListBuilder.create().texOffs(72, 111).addBox(-4.0F, -2.0F, -4.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 39.0F));

		PartDefinition tentacle3 = mound.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 64).addBox(-2.0F, -3.0F, 0.0F, 7.0F, 6.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 4.0F, 8.0F));

		PartDefinition tentacle_segment3 = tentacle3.addOrReplaceChild("tentacle_segment3", CubeListBuilder.create().texOffs(96, 64).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.0F, 41.0F));

		tentacle_segment3.addOrReplaceChild("tentacle_arm_1", CubeListBuilder.create().texOffs(50, 226).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.0F, 30.0F));

		tentacle_segment3.addOrReplaceChild("tentacle_arm_2", CubeListBuilder.create().texOffs(50, 226).mirror().addBox(-2.0F, -3.0F, -1.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -1.0F, 27.0F));

		tentacle_segment3.addOrReplaceChild("tentacle_arm_3", CubeListBuilder.create().texOffs(50, 226).mirror().addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 2.0F, 23.0F));

		PartDefinition tentacle4 = mound.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(180, 106).addBox(-4.0F, -4.0F, -1.0F, 9.0F, 7.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 10.0F, 8.0F));

		PartDefinition tentacle_segment4 = tentacle4.addOrReplaceChild("tentacle_segment4", CubeListBuilder.create().texOffs(128, 0).mirror().addBox(-2.0F, -2.0F, 0.0F, 4.0F, 5.0F, 34.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -1.0F, 22.0F));

		tentacle_segment4.addOrReplaceChild("tentacle_end2", CubeListBuilder.create().texOffs(186, 205).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 34.0F));

		PartDefinition tentacle5 = mound.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(128, 0).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 5.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 10.0F, 8.0F));

		tentacle5.addOrReplaceChild("tentacle_segment5", CubeListBuilder.create().texOffs(70, 182).addBox(0.0F, -2.0F, 0.0F, 2.0F, 3.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(128, 56).addBox(0.0F, -2.0F, 27.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(134, 214).addBox(1.0F, -2.0F, 29.0F, 0.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 34.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(MutavoreEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.body, netHeadYaw, headPitch);
		float partialTicks = ageInTicks - entity.tickCount;
        float yBodyRot = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
		Vec2 rot1 = entity.worm1.getRot(partialTicks);
		Vec2 rot2 = entity.worm2.getRot(partialTicks);
		Vec2 rot3 = entity.worm3.getRot(partialTicks);
		Vec2 rot4 = entity.worm4.getRot(partialTicks);
		Vec2 rot5 = entity.worm5.getRot(partialTicks);
		Vec2 rot6 = entity.worm6.getRot(partialTicks);
		Vec2 rot7 = entity.worm7.getRot(partialTicks);
		Vec2 rot8 = entity.worm8.getRot(partialTicks);
		Vec2 rot9 = entity.worm9.getRot(partialTicks);
		Vec2 rot10 = entity.worm10.getRot(partialTicks);
		BTAClientUtil.animateHead(this.tentacle, rot1.y - netHeadYaw - yBodyRot, rot1.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle_segment, rot2.y - netHeadYaw - yBodyRot, rot2.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle2, rot3.y - netHeadYaw - yBodyRot, rot3.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle_segment2, rot4.y - netHeadYaw - yBodyRot, rot4.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle3, rot5.y - netHeadYaw - yBodyRot, rot5.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle_segment3, rot6.y - netHeadYaw - yBodyRot, rot6.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle4, rot7.y - netHeadYaw - yBodyRot, rot7.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle_segment4, rot8.y - netHeadYaw - yBodyRot, rot8.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle5, rot9.y - netHeadYaw - yBodyRot, rot9.x - headPitch);
		BTAClientUtil.animateHead(this.tentacle_segment5, rot10.y - netHeadYaw - yBodyRot, rot10.x - headPitch);
		
		this.mound_head.visible = entity.isMutated(MutationType.MUTATE4);
		this.mine.visible =  entity.hasCyst(0) && entity.isMutated(MutationType.MUTATE4);
		this.mine2.visible = entity.hasCyst(1) && (entity.isMutated(MutationType.MUTATE3) || entity.isMutated(MutationType.MUTATE4));
		this.mine3.visible = entity.hasCyst(2) && (entity.isMutated(MutationType.MUTATE3) || entity.isMutated(MutationType.MUTATE4));
		this.mine4.visible = entity.hasCyst(3) && entity.isMutated(MutationType.MUTATE4);
		
		entity.idleAnimationState.animateIdle(this, MutavoreAnimation.MUTAVORE_IDLE, ageInTicks, limbSwingAmount, 1.5F);
		entity.bubbleStartAnimationState.animate(this, MutavoreAnimation.MUTAVORE_BUBBLE_START, ageInTicks);
		entity.bubbleStopAnimationState.animate(this, MutavoreAnimation.MUTAVORE_BUBBLE_STOP, ageInTicks);
		entity.tongueStartAnimationState.animate(this, MutavoreAnimation.TongueAnimation.MUTAVORE_TONGUE_START, ageInTicks);
		entity.tongueLoopAnimationState.animate(this, MutavoreAnimation.TongueAnimation.MUTAVORE_TONGUE_LOOP, ageInTicks);
		entity.tongueStopAnimationState.animate(this, MutavoreAnimation.TongueAnimation.MUTAVORE_TONGUE_STOP, ageInTicks);
		entity.mutateLArmAnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE_L_ARM, ageInTicks);
		entity.mutateRArmAnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE_R_ARM, ageInTicks);
		entity.mutate1AnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE1, ageInTicks);
		entity.mutate2AnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE2, ageInTicks);
		entity.mutate3AnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE3, ageInTicks);
		entity.mutate4AnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE4, ageInTicks);
		entity.mutateHeadAnimationState.animate(this, MutavoreAnimation.MutationAnimation.MUTATE_HEAD, ageInTicks);
		this.animateWalk(MutavoreAnimation.MUTAVORE_SWIM, limbSwing, limbSwingAmount, 2.5F, 1.5F);
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