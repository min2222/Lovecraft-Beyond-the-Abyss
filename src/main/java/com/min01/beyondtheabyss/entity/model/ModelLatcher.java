package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.LatcherAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.PartPositionUpdatePacket;
import com.min01.beyondtheabyss.network.PartPositionUpdatePacket.PartPosType;
import com.min01.beyondtheabyss.network.PartRotationUpdatePacket;
import com.min01.beyondtheabyss.network.PartRotationUpdatePacket.PartRotationType;
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
import net.minecraft.world.phys.Vec3;

public class ModelLatcher extends HierarchicalModel<EntityLatcher> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "latcher"), "main");
	private final ModelPart LatchingIsopod;

	public ModelLatcher(ModelPart root)
	{
		this.LatchingIsopod = root.getChild("LatchingIsopod");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition LatchingIsopod = partdefinition.addOrReplaceChild("LatchingIsopod", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition Body = LatchingIsopod.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 30).addBox(-4.5F, -3.0F, 0.0F, 9.0F, 6.0F, 14.0F, new CubeDeformation(0.02F))
		.texOffs(0, 51).addBox(0.0F, -5.0F, 1.0F, 0.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-8.5F, 3.0F, 0.02F, 17.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-10.5F, 0.021F, 0.02F, 21.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition Arms = Body.addOrReplaceChild("Arms", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, 1.75F));

		PartDefinition left_up_arm = Arms.addOrReplaceChild("left_up_arm", CubeListBuilder.create().texOffs(68, 49).addBox(-1.1F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.6F, -1.5F, -0.25F, 0.0F, 0.0F, -0.3491F));

		PartDefinition bone = left_up_arm.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(49, 60).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, 0.25F, -0.25F));

		bone.addOrReplaceChild("Finger1", CubeListBuilder.create().texOffs(5, 21).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.25F, -6.0F, 0.0F, 0.0F, -0.7854F));

		bone.addOrReplaceChild("Finger2", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.25F, -6.0F, 0.0F, 0.0F, 0.7854F));

		bone.addOrReplaceChild("Finger3", CubeListBuilder.create().texOffs(8, 18).addBox(0.0F, -0.25F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -6.0F));

		PartDefinition right_up_arm = Arms.addOrReplaceChild("right_up_arm", CubeListBuilder.create().texOffs(68, 40).addBox(-3.9F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6F, -1.5F, -0.25F, 0.0F, 0.0F, 0.3491F));

		PartDefinition bone2 = right_up_arm.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(32, 60).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, 0.25F, -0.25F));

		bone2.addOrReplaceChild("Finger4", CubeListBuilder.create().texOffs(5, 15).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.25F, -6.0F, 0.0F, 0.0F, 0.7854F));

		bone2.addOrReplaceChild("Finger5", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.25F, -6.0F, 0.0F, 0.0F, -0.7854F));

		bone2.addOrReplaceChild("Finger6", CubeListBuilder.create().texOffs(9, 9).addBox(0.0F, -0.25F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -6.0F));

		PartDefinition left_down_arm = Arms.addOrReplaceChild("left_down_arm", CubeListBuilder.create().texOffs(15, 67).addBox(-1.1F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.85F, 1.25F, -0.25F, 0.0F, 0.3054F, 0.4363F));

		PartDefinition bone3 = left_down_arm.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(59, 55).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, 0.25F, -0.25F));

		bone3.addOrReplaceChild("Finger7", CubeListBuilder.create().texOffs(9, 3).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.25F, -6.0F, 0.0F, 0.0F, -0.7854F));

		bone3.addOrReplaceChild("Finger8", CubeListBuilder.create().texOffs(3, 9).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.25F, -6.0F, 0.0F, 0.0F, 0.7854F));

		bone3.addOrReplaceChild("Finger9", CubeListBuilder.create().texOffs(6, 6).addBox(0.0F, -0.25F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -6.0F));

		PartDefinition right_down_arm = Arms.addOrReplaceChild("right_down_arm", CubeListBuilder.create().texOffs(0, 67).addBox(-3.9F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.85F, 1.25F, -0.25F, 0.0F, -0.3054F, -0.4363F));

		PartDefinition bone4 = right_down_arm.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(58, 40).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, 0.25F, -0.25F));

		bone4.addOrReplaceChild("Finger10", CubeListBuilder.create().texOffs(6, 0).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.25F, -6.0F, 0.0F, 0.0F, 0.7854F));

		bone4.addOrReplaceChild("Finger11", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -2.0F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -0.25F, -6.0F, 0.0F, 0.0F, -0.7854F));

		bone4.addOrReplaceChild("Finger12", CubeListBuilder.create().texOffs(3, 3).addBox(0.0F, -0.25F, -1.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -6.0F));

		PartDefinition Tails = Body.addOrReplaceChild("Tails", CubeListBuilder.create().texOffs(14, 51).addBox(-2.5F, -1.5F, -1.0F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.02F))
		.texOffs(49, 15).addBox(-5.5F, 1.521F, 0.02F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.3F, 14.0F));

		PartDefinition Tails2 = Tails.addOrReplaceChild("Tails2", CubeListBuilder.create().texOffs(37, 41).addBox(-2.5F, -1.5F, -1.0F, 5.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(33, 30).addBox(-5.5F, 1.501F, 0.0F, 11.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		Tails2.addOrReplaceChild("TailEdge", CubeListBuilder.create().texOffs(49, 22).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 9.0F));

		PartDefinition Head = LatchingIsopod.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -6.75F));

		PartDefinition Up = Head.addOrReplaceChild("Up", CubeListBuilder.create().texOffs(65, 29).addBox(-2.5F, 1.0F, -3.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(57, 7).addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.25F));

		PartDefinition AntennaRight = Up.addOrReplaceChild("AntennaRight", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.0F, -1.75F, -0.6398F, -0.3013F, -0.3795F));

		PartDefinition Antenna1 = AntennaRight.addOrReplaceChild("Antenna1", CubeListBuilder.create().texOffs(10, 23).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		Antenna1.addOrReplaceChild("Antenna2", CubeListBuilder.create().texOffs(11, 15).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition AntennaLeft = Up.addOrReplaceChild("AntennaLeft", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.5F, -1.0F, -1.75F, -0.6398F, 0.3013F, 0.3795F));

		PartDefinition Antenna3 = AntennaLeft.addOrReplaceChild("Antenna3", CubeListBuilder.create().texOffs(10, 23).mirror().addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		Antenna3.addOrReplaceChild("Antenna4", CubeListBuilder.create().texOffs(11, 15).mirror().addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		Head.addOrReplaceChild("Down", CubeListBuilder.create().texOffs(62, 65).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
		.texOffs(57, 0).addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.01F))
		.texOffs(39, 55).addBox(-4.5F, 0.0F, -3.0F, 9.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.25F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityLatcher entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.LatchingIsopod, netHeadYaw, headPitch);
		this.animate(entity.propelAnimationState, LatcherAnimation.LATCHER_PROPEL, ageInTicks);
		this.animate(entity.startLatchAnimationState, LatcherAnimation.LATCHER_START_LATCH, ageInTicks);
		this.animate(entity.latchAnimationState, LatcherAnimation.LATCHER_LATCH, ageInTicks);
		this.animate(entity.unlatchAnimationState, LatcherAnimation.LATCHER_UNLATCH, ageInTicks);
		float pi = Mth.PI / 180;
	    ModelPart body = this.LatchingIsopod.getChild("Body");
	    ModelPart tail = body.getChild("Tails");
	    ModelPart tail2 = tail.getChild("Tails2");
	    float bodyXRot = body.xRot / pi;
	    float tailXRot = tail.xRot / pi;
	    float tail2XRot = tail2.xRot / pi;
	    BTANetwork.sendToAll(new PartPositionUpdatePacket(entity, Vec3.ZERO, Vec3.ZERO, new Vec3(0, tail.y, 0), PartPosType.TAIL));
	    BTANetwork.sendToAll(new PartRotationUpdatePacket(entity, Vec3.ZERO, new Vec3(bodyXRot + tailXRot, 0, 0), Vec3.ZERO, PartRotationType.BODY));
	    BTANetwork.sendToAll(new PartRotationUpdatePacket(entity, Vec3.ZERO, Vec3.ZERO, new Vec3(bodyXRot + tail2XRot, 0, 0), PartRotationType.TAIL));
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		LatchingIsopod.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.LatchingIsopod;
	}
}