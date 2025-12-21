package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusHead;
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

public class ModelForneusHead extends HierarchicalModel<EntityForneusHead>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "forneus_head"), "main");
	private final ModelPart root;
	private final ModelPart head;

	public ModelForneusHead(ModelPart root)
	{
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-3.0F, -74.0F, 50.0F));

		head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1880, 916).addBox(-88.0F, -220.0F, -32.0F, 56.0F, 60.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, 0.0F, -234.0F, -1.5708F, 0.0F, 1.5708F));

		head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1776, 1452).addBox(-30.0F, -278.0F, 30.0F, 36.0F, 8.0F, 52.0F, new CubeDeformation(0.0F))
		.texOffs(2877, 1068).addBox(-30.0F, -270.0F, 30.0F, 36.0F, 32.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(528, 1871).addBox(-34.0F, -238.0F, 14.0F, 44.0F, 128.0F, 44.0F, new CubeDeformation(0.0F))
		.texOffs(1120, 1532).addBox(-40.0F, -110.0F, -96.0F, 56.0F, 56.0F, 212.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(147.1178F, -1.0F, -136.0F, -1.5708F, -0.3491F, 1.5708F));

		head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(1880, 444).addBox(-6.0F, -278.0F, 30.0F, 36.0F, 8.0F, 52.0F, new CubeDeformation(0.0F))
		.texOffs(2664, 486).addBox(-6.0F, -270.0F, 30.0F, 36.0F, 32.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(1856, 744).addBox(-10.0F, -238.0F, 14.0F, 44.0F, 128.0F, 44.0F, new CubeDeformation(0.0F))
		.texOffs(1344, 252).addBox(-16.0F, -110.0F, -96.0F, 56.0F, 56.0F, 212.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-141.1178F, -1.0F, -136.0F, -1.5708F, 0.3491F, -1.5708F));

		head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(1880, 236).addBox(0.0F, -94.0F, -176.0F, 24.0F, 24.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(2636, 2716).addBox(0.0F, -142.0F, -192.0F, 24.0F, 72.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-169.1178F, -1.0F, -161.0F, -1.9199F, 0.3491F, -1.5708F));

		head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(2865, 2961).addBox(-24.0F, -142.0F, -192.0F, 24.0F, 72.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 340).addBox(-24.0F, -94.0F, -176.0F, 24.0F, 24.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(175.1178F, -1.0F, -161.0F, -1.9199F, -0.3491F, 1.5708F));

		head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(3115, 595).mirror().addBox(-40.0F, -110.0F, 51.0F, 37.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(163.1178F, -12.0F, -173.0F, -1.8326F, -0.1309F, 1.5708F));

		head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(2984, 254).mirror().addBox(-40.0F, -110.0F, 51.0F, 37.0F, 56.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(115.1178F, -12.0F, -192.0F, -1.6581F, -0.1309F, 1.5708F));

		head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(2789, 718).mirror().addBox(-33.0F, -54.0F, 57.0F, 13.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(163.1178F, -22.0F, -173.0F, -1.8065F, -0.174F, 1.3997F));

		head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(3070, 867).mirror().addBox(-33.0F, -54.0F, 59.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(121.1178F, -25.0F, -192.0F, -1.6275F, -0.1467F, 1.3511F));

		head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(3070, 867).addBox(19.0F, -54.0F, 59.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-115.1178F, -25.0F, -192.0F, -1.6275F, 0.1467F, -1.3511F));

		head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(2984, 254).addBox(3.0F, -110.0F, 51.0F, 37.0F, 56.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-109.1178F, -12.0F, -192.0F, -1.6581F, 0.1309F, -1.5708F));

		head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(2789, 718).addBox(20.0F, -54.0F, 57.0F, 13.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-157.1178F, -22.0F, -173.0F, -1.8065F, 0.174F, -1.3997F));

		head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(3115, 595).addBox(3.0F, -110.0F, 51.0F, 37.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-157.1178F, -12.0F, -173.0F, -1.8326F, 0.1309F, -1.5708F));

		head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(1856, 520).addBox(-88.0F, -160.0F, -48.0F, 56.0F, 160.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, 39.0F, -191.0F, -1.5708F, -0.3054F, 1.5708F));

		head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(656, 688).addBox(-68.0F, -160.0F, -96.0F, 132.0F, 160.0F, 160.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-64.8822F, 10.0F, -99.0F, -0.7854F, 0.0F, 1.5708F));

		PartDefinition left_antenna = head.addOrReplaceChild("left_antenna", CubeListBuilder.create(), PartPose.offset(136.1178F, -7.0F, -67.0F));

		left_antenna.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(704, 1871).addBox(11.0F, -322.0F, 4.0F, 16.0F, 262.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 6.0F, -69.0F, -1.5708F, -0.3491F, 1.5708F));

		PartDefinition left_antenna2 = left_antenna.addOrReplaceChild("left_antenna2", CubeListBuilder.create(), PartPose.offset(-1.0F, -87.0F, 241.0F));

		left_antenna2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(496, 1795).addBox(15.0F, -722.0F, 8.0F, 8.0F, 400.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 93.0F, -310.0F, -1.5708F, -0.3491F, 1.5708F));

		PartDefinition left_antenna3 = left_antenna2.addOrReplaceChild("left_antenna3", CubeListBuilder.create(), PartPose.offset(0.0F, -137.0F, 375.0F));

		left_antenna3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(217, 184).addBox(15.0F, -770.0F, 8.0F, 32.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 230.0F, -685.0F, -1.5708F, -0.3491F, 1.5708F));

		PartDefinition right_antenna = head.addOrReplaceChild("right_antenna", CubeListBuilder.create(), PartPose.offset(-130.1178F, -7.0F, -67.0F));

		right_antenna.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(1568, 1800).addBox(-27.0F, -322.0F, 4.0F, 16.0F, 262.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 6.0F, -69.0F, -1.5708F, 0.3491F, -1.5708F));

		PartDefinition right_antenna2 = right_antenna.addOrReplaceChild("right_antenna2", CubeListBuilder.create(), PartPose.offset(1.0F, -87.0F, 241.0F));

		right_antenna2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(464, 1795).addBox(-23.0F, -722.0F, 8.0F, 8.0F, 400.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 93.0F, -310.0F, -1.5708F, 0.3491F, -1.5708F));

		PartDefinition right_antenna3 = right_antenna2.addOrReplaceChild("right_antenna3", CubeListBuilder.create(), PartPose.offset(0.0F, -137.0F, 375.0F));

		right_antenna3.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(1120, 1475).addBox(-47.0F, -770.0F, 8.0F, 32.0F, 48.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 230.0F, -685.0F, -1.5708F, 0.3491F, -1.5708F));

		PartDefinition left_jaw = head.addOrReplaceChild("left_jaw", CubeListBuilder.create(), PartPose.offset(75.8822F, 10.0F, -93.0F));

		left_jaw.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(1344, 592).addBox(-40.0F, 32.0F, 48.0F, 80.0F, 24.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(704, 296).addBox(-40.0F, 32.0F, 96.0F, 80.0F, 24.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(672, 352).addBox(-40.0F, -24.0F, -208.0F, 80.0F, 80.0F, 256.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, -6.0F, -0.7854F, 0.0F, -1.5708F));

		PartDefinition left_jaw2 = left_jaw.addOrReplaceChild("left_jaw2", CubeListBuilder.create(), PartPose.offset(-144.0F, 0.0F, -167.0F));

		left_jaw2.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(1120, 1283).addBox(-24.0F, -32.0F, -460.0F, 48.0F, 72.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1543).addBox(-24.0F, -8.0F, -436.0F, 48.0F, 24.0F, 228.0F, new CubeDeformation(0.0F))
		.texOffs(1296, 0).addBox(-24.0F, 16.0F, -436.0F, 48.0F, 24.0F, 228.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(139.0F, 0.0F, 161.0F, -0.7854F, 0.0F, -1.5708F));

		PartDefinition left_inner_jaw = head.addOrReplaceChild("left_inner_jaw", CubeListBuilder.create(), PartPose.offset(52.8822F, 10.0F, -115.0F));

		left_inner_jaw.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(2721, 1210).addBox(-16.0F, 136.0F, -68.0F, 24.0F, 48.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(960, 1991).addBox(-16.0F, 88.0F, -44.0F, 24.0F, 96.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(768, 1871).addBox(-28.0F, 0.0F, -56.0F, 48.0F, 88.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 0.0F, 16.0F, -0.7854F, 0.0F, -1.5708F));

		PartDefinition right_inner_jaw = head.addOrReplaceChild("right_inner_jaw", CubeListBuilder.create(), PartPose.offset(-46.8822F, 10.0F, -115.0F));

		right_inner_jaw.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(3306, 1484).addBox(-8.0F, 136.0F, -68.0F, 24.0F, 48.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(960, 1871).addBox(-8.0F, 88.0F, -44.0F, 24.0F, 96.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(1600, 520).addBox(-20.0F, 0.0F, -56.0F, 48.0F, 88.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, 0.0F, 16.0F, -0.7854F, 0.0F, 1.5708F));

		PartDefinition right_jaw = head.addOrReplaceChild("right_jaw", CubeListBuilder.create(), PartPose.offset(-69.8822F, 10.0F, -93.0F));

		right_jaw.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(896, 296).addBox(-40.0F, 32.0F, 96.0F, 80.0F, 24.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(1344, 520).addBox(-40.0F, 32.0F, 48.0F, 80.0F, 24.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(0, 352).addBox(-40.0F, -24.0F, -208.0F, 80.0F, 80.0F, 256.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, -6.0F, -0.7854F, 0.0F, 1.5708F));

		PartDefinition right_jaw2 = right_jaw.addOrReplaceChild("right_jaw2", CubeListBuilder.create(), PartPose.offset(144.0F, 0.0F, -167.0F));

		right_jaw2.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 1291).addBox(-24.0F, -8.0F, -436.0F, 48.0F, 24.0F, 228.0F, new CubeDeformation(0.0F))
		.texOffs(1120, 1379).addBox(-24.0F, -32.0F, -460.0F, 48.0F, 72.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(552, 1619).addBox(-24.0F, 16.0F, -436.0F, 48.0F, 24.0F, 228.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-139.0F, 0.0F, 161.0F, -0.7854F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 4096, 4096);
	}

	@Override
	public void setupAnim(EntityForneusHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.head, netHeadYaw, headPitch);
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