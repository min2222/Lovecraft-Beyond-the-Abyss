package com.min01.beyondtheabyss.item.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModelAdvancedDiverSet<T extends LivingEntity> extends HumanoidModel<T> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "advanced_diver_set"), "main");
	public final ModelPart RightLeg;
	public final ModelPart Head;
	public final ModelPart RightArm;
	public final ModelPart LeftArm;
	public final ModelPart Body;
	public final ModelPart LeftLeg;
	public final ModelPart Left_boots;
	public final ModelPart Right_boots;

	public ModelAdvancedDiverSet(ModelPart root) 
	{
		super(root);
		this.RightLeg = root.getChild("RightLeg");
		this.Head = root.getChild("Head");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		this.Body = root.getChild("Body");
		this.LeftLeg = root.getChild("LeftLeg");
		this.Left_boots = root.getChild("Left_boots");
		this.Right_boots = root.getChild("Right_boots");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 65).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(27, 79).addBox(-2.1F, 7.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		RightLeg.addOrReplaceChild("RightLegLayer_r1", CubeListBuilder.create().texOffs(0, 83).addBox(1.0F, -1.5F, 5.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offsetAndRotation(-4.0F, 2.5F, -7.0F, 0.0F, 0.0F, 0.1309F));

		RightLeg.addOrReplaceChild("RightLegLayer_r2", CubeListBuilder.create().texOffs(0, 92).addBox(-0.3F, -2.1F, 5.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(-2.0F, 2.5F, -7.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
		.texOffs(64, 9).addBox(-4.0F, -7.5F, -6.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 9).addBox(-4.0F, -7.5F, -6.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Head.addOrReplaceChild("HatLayer_r1", CubeListBuilder.create().texOffs(73, 47).addBox(-2.5F, -6.0694F, 7.4543F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(73, 47).mirror().addBox(9.5F, -6.0694F, 7.4543F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(73, 47).addBox(-2.5F, -6.0694F, 4.4543F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F))
		.texOffs(73, 47).mirror().addBox(9.5F, -6.0694F, 4.4543F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false)
		.texOffs(60, 41).mirror().addBox(9.5F, -6.0694F, 0.9543F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(60, 41).addBox(-2.5F, -6.0694F, 0.9543F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(68, 32).mirror().addBox(9.5F, -6.0694F, -1.0457F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(68, 32).addBox(-2.5F, -6.0694F, -1.0457F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(68, 24).mirror().addBox(5.5F, -1.0694F, -1.0457F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(80, 24).mirror().addBox(6.5F, -6.0694F, 9.9543F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(94, 20).addBox(2.0F, -6.0694F, 9.9543F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(94, 20).addBox(6.0F, -6.0694F, 9.9543F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
		.texOffs(82, 20).addBox(2.5F, -6.0694F, 9.9543F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(80, 24).addBox(-2.5F, -6.0694F, 9.9543F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(68, 24).addBox(-0.5F, -1.0694F, -1.0457F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -1.2713F, -5.066F, -0.3054F, 0.0F, 0.0F));

		Head.addOrReplaceChild("HatLayer_r2", CubeListBuilder.create().texOffs(68, 19).addBox(-1.0F, 4.6143F, -6.7253F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, -8.2489F, -0.5291F, 0.1745F, 0.0F, 0.0F));

		Head.addOrReplaceChild("HatLayer_r3", CubeListBuilder.create().texOffs(90, 31).addBox(-1.0F, -0.6075F, -4.314F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(87, 25).addBox(-1.0F, -0.6075F, -3.314F, 2.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.2489F, -0.5291F, 0.0436F, 0.0F, 0.0F));

		PartDefinition bone = Head.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(5.587F, -20.0993F, -0.347F));

		bone.addOrReplaceChild("HatLayer_r4", CubeListBuilder.create().texOffs(82, 7).addBox(-1.0F, 0.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7043F, 11.3231F, 0.347F, 0.0F, 0.0F, -0.1745F));

		bone.addOrReplaceChild("HatLayer_r5", CubeListBuilder.create().texOffs(56, 2).addBox(5.0792F, -10.7511F, -2.2626F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.587F, 21.9504F, -0.1821F, 0.0F, -0.1309F, 0.0F));

		bone.addOrReplaceChild("HatLayer_r6", CubeListBuilder.create().texOffs(68, 3).addBox(5.0792F, -11.0547F, -3.8849F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-5.587F, 21.9504F, -0.1821F, -0.1047F, -0.1309F, 0.0F));

		partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(27, 79).addBox(-3.0F, 5.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(27, 79).mirror().addBox(-1.0F, 5.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
		.texOffs(4, 36).addBox(-1.5F, 0.0F, -2.4F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(10, 34).addBox(-5.0F, 9.0F, -3.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(10, 34).mirror().addBox(4.0F, 9.0F, -3.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(10, 34).mirror().addBox(4.0F, 9.0F, 1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(10, 34).addBox(-5.0F, 9.0F, 1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(23, 85).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.45F))
		.texOffs(77, 75).addBox(-6.0F, 4.8F, -2.6F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(77, 82).mirror().addBox(3.0F, 3.2F, 2.6F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(77, 82).addBox(-6.0F, 3.2F, 2.6F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(77, 75).mirror().addBox(3.0F, 4.8F, -2.6F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(27, 64).addBox(-4.5F, -1.0F, 2.5F, 9.0F, 12.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(26, 65).addBox(2.0F, 8.5F, 5.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(23, 67).addBox(4.5F, 0.0F, 4.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(51, 65).addBox(-4.5F, -1.0F, 5.7F, 9.0F, 8.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		Body.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(73, 63).mirror().addBox(-1.5F, -1.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(4.5F, 1.5F, 0.0F, 0.0F, 0.0F, 0.1309F));

		Body.addOrReplaceChild("BodyLayer_r2", CubeListBuilder.create().texOffs(73, 63).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-4.5F, 1.5F, 0.0F, 0.0F, 0.0F, -0.1309F));

		Body.addOrReplaceChild("BodyLayer_r3", CubeListBuilder.create().texOffs(23, 90).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.2F, -2.6F, 0.0F, 0.0F, 0.1309F));

		PartDefinition bone2 = Body.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -2.6452F));

		bone2.addOrReplaceChild("BodyLayer_r4", CubeListBuilder.create().texOffs(10, 34).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6874F, 0.3F, 0.0F, 0.0F, -1.4835F, 0.0F));

		bone2.addOrReplaceChild("BodyLayer_r5", CubeListBuilder.create().texOffs(10, 34).mirror().addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.6874F, 0.3F, 0.0F, 0.0F, 1.4835F, 0.0F));

		PartDefinition bone3 = Body.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -2.6452F));

		bone3.addOrReplaceChild("BodyLayer_r6", CubeListBuilder.create().texOffs(10, 34).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6874F, 0.3F, 5.2905F, 0.0F, 1.4835F, 0.0F));

		bone3.addOrReplaceChild("BodyLayer_r7", CubeListBuilder.create().texOffs(10, 34).mirror().addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.6874F, 0.3F, 5.2905F, 0.0F, -1.4835F, 0.0F));

		partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(27, 79).mirror().addBox(-1.9F, 7.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

		partdefinition.addOrReplaceChild("Left_boots", CubeListBuilder.create().texOffs(17, 23).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.4F)), PartPose.offset(1.9F, 12.0F, -1.0F));

		partdefinition.addOrReplaceChild("Right_boots", CubeListBuilder.create().texOffs(17, 23).mirror().addBox(-2.0F, 9.0F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(-1.9F, 12.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		this.Head.copyFrom(this.head);
		this.Body.copyFrom(this.body);
		this.LeftArm.copyFrom(this.leftArm);
		this.RightArm.copyFrom(this.rightArm);
		this.LeftLeg.copyFrom(this.leftLeg);
		this.RightLeg.copyFrom(this.rightLeg);
		this.Left_boots.copyFrom(this.leftLeg);
		this.Right_boots.copyFrom(this.rightLeg);
		RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Left_boots.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Right_boots.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}