package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.GhidruthAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.ModelDataSyncPacket;
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

public class ModelGhidruth extends HierarchicalModel<EntityGhidruth>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth"), "main");
	private final ModelPart root;

	public ModelGhidruth(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition root2 = root.addOrReplaceChild("root2", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, -14.0F));

		PartDefinition Head = root2.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(178, 0).addBox(-18.5F, -26.6071F, -85.65F, 37.0F, 18.0F, 53.0F, new CubeDeformation(0.0F))
		.texOffs(261, 72).addBox(-13.5F, -8.6071F, -74.65F, 27.0F, 6.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.3929F, 15.04F));

		Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(104, 377).addBox(0.0F, 0.0F, -20.0F, 0.0F, 7.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.5F, -8.6071F, -54.65F, 0.0F, 0.0F, 0.384F));

		Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(104, 377).mirror().addBox(0.0F, 0.0F, -20.0F, 0.0F, 7.0F, 40.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(15.5F, -8.6071F, -54.95F, 0.0F, 0.0F, -0.384F));

		Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 73).addBox(-13.5F, 0.0F, 0.0F, 27.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.6071F, -76.65F, -0.384F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(356, 46).addBox(-13.0F, -9.009F, -13.0F, 26.0F, 18.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.6081F, -85.65F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Body = Head.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-23.5F, -27.8851F, -0.2541F, 47.0F, 51.0F, 83.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.722F, -41.5759F));

		Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(387, 188).addBox(-22.5F, -2.5F, -7.0F, 26.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.3055F, -4.3857F, 2.4712F, 0.2618F, 0.5585F, 0.4682F));

		Body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(374, 257).mirror().addBox(-32.0F, -0.75F, -3.5F, 34.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-24.4365F, -1.5047F, 3.9712F, 0.1797F, 0.6784F, 0.2817F));

		Body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(374, 257).mirror().addBox(-34.0F, 1.0F, -2.5F, 34.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-22.4365F, -10.2547F, 0.9712F, 0.1003F, 0.4167F, 0.5536F));

		Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(374, 257).addBox(0.0F, 1.0F, -2.5F, 34.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.4365F, -10.2547F, 0.9712F, 0.1003F, -0.4167F, -0.5536F));

		Body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(374, 257).addBox(-2.0F, -0.75F, -3.5F, 34.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.4365F, -1.5047F, 3.9712F, 0.1797F, -0.6784F, -0.2817F));

		Body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(387, 208).addBox(-3.5F, -2.5F, -7.0F, 26.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.3055F, -4.3857F, 2.4712F, 0.2618F, -0.5585F, -0.4682F));

		Body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(360, 165).addBox(-37.0125F, -2.18F, -9.673F, 45.0F, 5.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.0021F, 2.2943F, 6.476F, 0.0F, 0.6981F, 0.0F));

		Body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(366, 119).addBox(-7.9875F, -2.18F, -9.673F, 45.0F, 5.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0021F, 2.2943F, 6.476F, 0.0F, -0.6981F, 0.0F));

		Body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(295, 289).addBox(-47.06F, -4.535F, -7.52F, 56.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0021F, -11.9307F, 44.256F, 0.2805F, 0.1976F, 0.3837F));

		Body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(148, 289).addBox(-8.94F, -4.535F, -7.52F, 56.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0021F, -11.9307F, 44.256F, 0.2805F, -0.1976F, -0.3837F));

		Body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(366, 142).mirror().addBox(-35.5F, 0.0F, -10.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-24.0F, -20.8841F, 52.7459F, 0.2182F, 0.5236F, 0.6109F));

		Body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(356, 91).mirror().addBox(-39.5F, -1.0F, -8.0F, 43.0F, 0.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-24.4444F, -18.54F, 43.7232F, 0.2657F, 0.1685F, 0.5692F));

		Body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(356, 91).addBox(-3.5F, -1.0F, -8.0F, 43.0F, 0.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.4444F, -18.54F, 43.7232F, 0.2657F, -0.1685F, -0.5692F));

		Body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(366, 142).addBox(-3.5F, 1.0F, -9.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.5621F, -7.4657F, 44.736F, 0.2636F, -0.2319F, 0.1059F));

		Body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(366, 142).addBox(-3.5F, 0.0F, -10.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.0F, -20.8841F, 52.7459F, 0.2182F, -0.5236F, -0.6109F));

		Body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(366, 142).mirror().addBox(-35.5F, 1.0F, -9.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-23.5621F, -7.4657F, 44.736F, 0.2636F, 0.2319F, -0.1059F));

		Body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(306, 29).mirror().addBox(-32.5F, -1.0F, -5.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -27.8841F, 73.7459F, 0.0F, 0.8727F, 1.5708F));

		Body.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(306, 29).addBox(-6.5F, -1.0F, -5.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -27.8841F, 73.7459F, 0.0F, -0.8727F, -1.5708F));

		Body.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(306, 29).addBox(-1.5F, 0.0F, -2.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.5621F, -7.4657F, 44.736F, 0.3564F, -0.5389F, -0.097F));

		Body.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(306, 29).mirror().addBox(-37.5F, 0.0F, -2.0F, 39.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-23.5621F, -7.4657F, 44.736F, 0.3564F, 0.5389F, 0.097F));

		Body.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(148, 316).addBox(-31.6428F, 1.05F, -13.4768F, 44.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0621F, 2.8743F, 83.031F, -0.4626F, 0.8227F, -0.5975F));

		Body.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(285, 316).addBox(-12.3572F, 1.05F, -13.4768F, 44.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0621F, 2.8743F, 83.031F, -0.4626F, -0.8227F, 0.5975F));

		Body.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(185, 377).mirror().addBox(0.0F, -14.0F, -15.5F, 0.0F, 14.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.5F, -27.8851F, 62.2459F, 0.0F, 0.0F, -0.3927F));

		Body.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 135).mirror().addBox(0.0304F, -16.8473F, -15.5F, 0.0F, 17.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-24.5F, 0.6149F, 49.2459F, 0.0F, 0.0F, -1.8326F));

		Body.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 135).addBox(-0.0304F, -16.8473F, -15.5F, 0.0F, 17.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.5F, 0.6149F, 49.2459F, 0.0F, 0.0F, 1.8326F));

		Body.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(185, 377).addBox(0.0F, -14.0F, -15.5F, 0.0F, 14.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -27.8851F, 62.2459F, 0.0F, 0.0F, 0.3927F));

		Body.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(149, 135).addBox(-1.0F, -22.0F, -8.5F, 0.0F, 26.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -25.9586F, 23.9847F, -0.7341F, -0.1176F, -0.1293F));

		Body.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(149, 135).mirror().addBox(0.0F, -23.0F, -8.5F, 0.0F, 26.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(149, 135).addBox(8.0F, -23.0F, -8.5F, 0.0F, 26.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -27.9586F, 23.9847F, -0.3054F, 0.0F, 0.0F));

		Body.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(149, 135).addBox(1.0F, -22.0F, -8.5F, 0.0F, 26.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -25.9586F, 23.9847F, -0.7341F, 0.1176F, 0.1293F));

		Body.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -23.5F, -13.5F, 5.0F, 45.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.3724F, 21.8883F, -0.3054F, 0.0F, 0.0F));

		PartDefinition Fin = Body.addOrReplaceChild("Fin", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0452F, 57.7228F));

		Fin.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(314, 174).addBox(-4.5F, -33.0F, -13.5F, 9.0F, 68.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.8614F, 17.3908F, -0.6981F, 0.0F, 0.0F));

		Fin.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(0, 387).addBox(-4.5F, -11.0F, -9.5F, 9.0F, 24.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -58.6706F, 43.8948F, -0.6981F, 0.0F, 0.0F));

		PartDefinition RearBody = Body.addOrReplaceChild("RearBody", CubeListBuilder.create().texOffs(0, 135).addBox(-19.5F, -17.1341F, -3.0242F, 39.0F, 41.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(246, 210).addBox(0.0F, -28.1341F, -0.0242F, 0.0F, 11.0F, 67.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.751F, 82.9001F));

		RearBody.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(306, 0).mirror().addBox(-55.9111F, -3.5421F, -23.4224F, 50.0F, 7.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 12.1923F, 57.7432F, 0.2943F, 0.6534F, 0.3822F));

		RearBody.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(306, 0).addBox(5.9111F, -3.5421F, -23.4224F, 50.0F, 7.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.1923F, 57.7432F, 0.2943F, -0.6534F, -0.3822F));

		PartDefinition Tail = RearBody.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(172, 201).addBox(-11.8133F, -13.2595F, -10.299F, 23.0F, 28.0F, 47.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3133F, -0.8746F, 67.1947F));

		Tail.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(0, 247).addBox(-3.5F, -16.8201F, -11.2447F, 7.0F, 114.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3133F, 0.1052F, 24.69F, 1.4399F, 0.0F, 0.0F));

		Tail.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(65, 247).addBox(-4.5F, -21.2245F, -14.98F, 9.0F, 72.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3133F, -36.035F, 40.2033F, -0.7854F, 0.0F, 0.0F));

		Body.addOrReplaceChild("LeftFin", CubeListBuilder.create().texOffs(283, 386).addBox(39.5F, -4.0F, -9.0F, 25.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(264, 347).addBox(-7.5F, -4.0F, -9.0F, 47.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.5947F, 16.5084F, 16.5359F, 0.0F, 0.0F, 0.6981F));

		Body.addOrReplaceChild("RightFin", CubeListBuilder.create().texOffs(217, 377).addBox(-64.5F, -4.0F, -9.0F, 25.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(127, 347).addBox(-39.5F, -4.0F, -9.0F, 47.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.5947F, 16.5084F, 16.5359F, 0.0F, 0.0F, -0.6981F));

		PartDefinition Mouth = Head.addOrReplaceChild("Mouth", CubeListBuilder.create().texOffs(149, 135).addBox(-18.5F, -7.0658F, -39.01F, 37.0F, 13.0F, 52.0F, new CubeDeformation(0.0F))
		.texOffs(276, 119).addBox(-12.5F, -13.0658F, -31.01F, 25.0F, 6.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.4587F, -39.83F));

		Mouth.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(39, 361).addBox(-13.31F, -6.6003F, -12.69F, 26.0F, 13.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4155F, -39.4484F, 0.0F, 0.7854F, 0.0F));

		Mouth.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(361, 347).addBox(0.0F, -8.0F, -19.5F, 0.0F, 8.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.5F, -7.0658F, -11.51F, 0.0F, 0.0F, -0.3491F));

		Mouth.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(361, 347).mirror().addBox(0.0F, -8.0F, -19.5F, 0.0F, 8.0F, 40.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(14.5F, -7.0658F, -11.51F, 0.0F, 0.0F, 0.3491F));

		Mouth.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(178, 0).addBox(-12.5F, -8.0F, 0.0F, 25.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0658F, -33.01F, 0.3491F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntityGhidruth entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.swimAnimationState, GhidruthAnimation.GHIDRUTH_SWIM, ageInTicks);
		this.animate(entity.biteAnimationState, GhidruthAnimation.GHIDRUTH_BITE, ageInTicks);
		this.animate(entity.tailSwingAnimationState, GhidruthAnimation.GHIDRUTH_TAIL_SWING, ageInTicks);
		this.root.getChild("root2").xRot += (headPitch * ((float)Math.PI / 180F)) * 0.7F;
		float pi = ((float)Math.PI / 180F);
	    ModelPart rearBody = this.root.getChild("root2").getChild("Head").getChild("Body").getChild("RearBody");
	    ModelPart tail = this.root.getChild("root2").getChild("Head").getChild("Body").getChild("RearBody").getChild("Tail");
	    ModelPart head = this.root.getChild("root2").getChild("Head");
	    ModelPart root2 = this.root.getChild("root2");
	    BTANetwork.CHANNEL.sendToServer(new ModelDataSyncPacket(entity, 0.0F, (rearBody.yRot / pi) + (tail.yRot / pi), 0.0F, ModelDataSyncPacket.ModelType.TAIL_ROT));
	    BTANetwork.CHANNEL.sendToServer(new ModelDataSyncPacket(entity, 0.0F, head.xRot / pi, 0.0F, ModelDataSyncPacket.ModelType.HEAD_ROT));
	    BTANetwork.CHANNEL.sendToServer(new ModelDataSyncPacket(entity, 0.0F, root2.xRot / pi, 0.0F, ModelDataSyncPacket.ModelType.RENDER_ROT));
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