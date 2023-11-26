package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.GhidruthAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.network.AbyssNetwork;
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
	private final ModelPart MainRootThing;

	public ModelGhidruth(ModelPart root)
	{
		this.MainRootThing = root.getChild("MainRootThing");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition MainRootThing = partdefinition.addOrReplaceChild("MainRootThing", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition root2 = MainRootThing.addOrReplaceChild("root2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Head = root2.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(73, 307).addBox(1.4481F, -52.7175F, -46.8065F, 0.0F, 35.0F, 38.0F, new CubeDeformation(0.0F))
		.texOffs(340, 384).addBox(-17.5519F, -17.7175F, -61.8065F, 35.0F, 13.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(283, 98).addBox(-17.5519F, -17.7175F, -43.8065F, 35.0F, 36.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0519F, -8.2825F, 26.8065F));

		Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(291, 297).addBox(-13.5F, -6.5F, -14.5F, 27.0F, 13.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0519F, -5.6357F, -58.2179F, -0.3927F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(327, 415).addBox(-11.0F, -7.5F, -7.5F, 22.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0519F, -1.4968F, -66.1459F, -1.3963F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(323, 47).addBox(-10.0F, -6.5F, -26.5F, 20.0F, 13.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5133F, -11.2175F, -70.0261F, 0.0F, -0.2269F, 0.0F));

		Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 27).addBox(-7.0F, -6.5F, -7.5F, 13.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4519F, -11.2175F, -97.2065F, 0.0F, -0.7854F, 0.0F));

		Head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 263).addBox(-2.0F, -4.5F, -8.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.9481F, 4.5849F, -33.0904F, 0.5299F, 0.7119F, 0.3655F));

		Head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(62, 421).addBox(-7.0F, -3.0F, -10.0F, 11.0F, 6.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.9194F, -2.4817F, -45.3859F, -0.4215F, -0.3614F, 0.1572F));

		Head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(223, 416).addBox(-4.0F, -3.0F, -10.0F, 11.0F, 6.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0233F, -2.4817F, -45.3859F, -0.4215F, 0.3614F, -0.1572F));

		Head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 97).addBox(-10.2937F, -1.8186F, -10.1521F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 114).addBox(0.7063F, -1.8186F, 0.5979F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.5376F, 0.3634F, -29.9056F, 0.5299F, -0.7119F, -0.3655F));

		Head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 215).addBox(-5.5143F, -1.8186F, -8.5578F, 8.0F, 9.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.5376F, 0.3634F, -29.9056F, 0.3927F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(304, 229).addBox(-10.0F, -6.5F, -18.5F, 20.0F, 13.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6098F, -11.2175F, -77.8211F, 0.0F, 0.2269F, 0.0F));

		Head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 345).addBox(-9.5F, -6.5F, -20.5F, 18.0F, 13.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2981F, -7.105F, -74.5223F, -0.2182F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(382, 309).addBox(-13.5F, -6.5F, -5.5F, 27.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0519F, 11.0793F, -44.8658F, 0.0F, 0.0F, -3.1416F));

		Head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 239).addBox(-5.5F, -4.5F, -5.5F, 8.0F, 9.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.4481F, 4.0109F, -31.7045F, 0.3927F, 0.0F, 0.0F));

		Head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(201, 20).addBox(-15.0F, -4.5F, 6.75F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.5339F, 6.2085F, -37.0101F, 0.5299F, 0.7119F, 0.3655F));

		PartDefinition Body = Head.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(160, 284).addBox(1.2857F, -60.3527F, 20.6398F, 0.0F, 55.0F, 50.0F, new CubeDeformation(0.0F))
		.texOffs(227, 49).addBox(-52.7143F, 11.6473F, -5.3602F, 35.0F, 0.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(0, 97).addBox(17.2857F, 11.6473F, -5.3602F, 35.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1624F, -12.3648F, -18.4463F));

		Body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(213, 0).addBox(-17.5F, 0.0F, -17.5F, 49.0F, 0.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(32.7857F, -5.3527F, 21.1398F, 0.0F, 0.0F, -0.3927F));

		Body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 215).addBox(-35.0736F, -11.1411F, -52.4439F, 49.0F, 0.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-34.1762F, 6.3079F, 56.0837F, 0.0F, 0.0F, 0.3927F));

		Body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 0).addBox(-17.5F, -18.0F, -30.5F, 35.0F, 36.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2143F, 16.6473F, 16.1398F, -0.1309F, 0.0F, 0.0F));

		Body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(136, 41).addBox(-17.5F, -18.0F, -30.5F, 35.0F, 36.0F, 56.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2143F, 8.6473F, 21.1398F, 0.1309F, 0.0F, 0.0F));

		PartDefinition LeftFin = Body.addOrReplaceChild("LeftFin", CubeListBuilder.create(), PartPose.offsetAndRotation(10.4711F, 23.8376F, 11.3421F, 0.0F, 0.0F, 0.5672F));

		LeftFin.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(397, 415).addBox(-19.6129F, -0.5F, -3.4544F, 17.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.2683F, -1.176F, -0.192F, -3.1416F, 0.6981F, 3.1416F));

		LeftFin.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(357, 354).addBox(-11.139F, -0.5F, -15.7903F, 37.0F, 7.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.2683F, -1.176F, -0.192F, -3.1416F, -0.0873F, 3.1416F));

		LeftFin.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(94, 389).addBox(-16.5F, -8.0F, -8.0F, 33.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3643F, 0.324F, -5.2023F, 0.0F, -1.5708F, -0.7854F));

		LeftFin.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(381, 226).addBox(-28.1245F, -0.5F, 6.5602F, 32.0F, 7.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.2683F, -1.176F, -0.192F, 0.0F, -1.0908F, 0.0F));

		PartDefinition RightFin = Body.addOrReplaceChild("RightFin", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.8997F, 23.8376F, 11.3421F, 0.0F, 0.0F, -0.5672F));

		RightFin.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(397, 415).mirror().addBox(2.6129F, -0.5F, -3.4544F, 17.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.2683F, -1.176F, -0.192F, -3.1416F, -0.6981F, -3.1416F));

		RightFin.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(357, 354).mirror().addBox(-25.861F, -0.5F, -15.7903F, 37.0F, 7.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.2683F, -1.176F, -0.192F, -3.1416F, 0.0873F, -3.1416F));

		RightFin.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(94, 389).mirror().addBox(-16.5F, -8.0F, -8.0F, 33.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.3643F, 0.324F, -5.2023F, 0.0F, 1.5708F, 0.7854F));

		RightFin.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(381, 226).mirror().addBox(-3.8755F, -0.5F, 6.5602F, 32.0F, 7.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.2683F, -1.176F, -0.192F, 0.0F, 1.0908F, 0.0F));

		PartDefinition RearBody = Body.addOrReplaceChild("RearBody", CubeListBuilder.create(), PartPose.offset(-0.2143F, 11.0273F, 56.7972F));

		RearBody.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(96, 0).addBox(-17.5F, 0.0F, -17.5F, 35.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(34.0F, 9.62F, 2.3426F, 0.0F, 0.0F, 0.3927F));

		RearBody.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(80, 266).addBox(-23.0226F, 13.2333F, -14.4439F, 35.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.9619F, -4.7194F, -0.7136F, 0.0F, 0.0F, -0.3927F));

		RearBody.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(161, 175).addBox(-17.5F, -33.0F, -32.5F, 35.0F, 36.0F, 55.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.3467F, 15.1229F, -0.1309F, 0.0F, 0.0F));

		RearBody.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 264).addBox(-17.5F, -14.0F, -27.5F, 35.0F, 36.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.3192F, 11.6264F, 0.1309F, 0.0F, 0.0F));

		PartDefinition Tail = RearBody.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 0).addBox(-0.3333F, -19.6844F, -1.2055F, 0.0F, 40.0F, 135.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1667F, 1.3044F, 29.0481F));

		Tail.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(111, 35).addBox(-17.5F, 0.0F, -13.5F, 23.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(34.1667F, 8.3156F, 18.2945F, 0.3655F, -0.147F, 0.3655F));

		Tail.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(181, 0).addBox(-17.6426F, 29.2153F, 25.4106F, 23.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.7953F, -6.0238F, -29.7616F, 0.3655F, 0.147F, -0.3655F));

		Tail.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 67).addBox(0.0F, -20.0F, -54.0F, 0.0F, 40.0F, 108.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3333F, -7.4625F, 30.2884F, 1.1781F, 0.0F, 0.0F));

		Tail.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(160, 266).addBox(-17.5F, -1.5F, -28.0F, 35.0F, 19.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1667F, -8.2993F, 18.0418F, 0.5236F, 0.0F, 0.0F));

		Tail.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(286, 169).addBox(-17.5F, -14.0F, 33.5F, 35.0F, 19.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1667F, 3.0148F, -33.4216F, 0.1309F, 0.0F, 0.0F));

		PartDefinition Mouth = Head.addOrReplaceChild("Mouth", CubeListBuilder.create(), PartPose.offset(-0.0461F, 16.5873F, -36.7275F));

		Mouth.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(124, 421).addBox(23.229F, -6.6175F, -1.7771F, 12.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-24.0612F, -6.6175F, -23.9955F, 14.0F, 13.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, 6.2095F, -23.3535F, 0.0F, -0.7854F, -3.1416F));

		Mouth.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(423, 129).addBox(-35.229F, -6.6175F, -1.7771F, 12.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, 6.2095F, -23.3535F, 0.0F, 0.7854F, 3.1416F));

		Mouth.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(234, 377).addBox(-13.5F, -2.1882F, -10.6307F, 27.0F, 13.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, 6.2095F, -23.3535F, -0.3927F, 0.0F, 3.1416F));

		Mouth.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(400, 34).addBox(-11.0F, -8.5F, -10.5F, 22.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, -3.3938F, -29.3569F, -1.3963F, 0.0F, -3.1416F));

		Mouth.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(360, 0).addBox(-17.5F, -6.6175F, -4.6639F, 35.0F, 13.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, 6.2095F, -23.3535F, 0.0F, 0.0F, -3.1416F));

		Mouth.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(394, 169).addBox(-17.9903F, -6.6175F, -19.1319F, 20.0F, 13.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, 6.2095F, -23.3535F, 0.0F, -0.3578F, 3.1416F));

		Mouth.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 395).addBox(-2.0097F, -6.6175F, -19.1319F, 20.0F, 13.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0058F, 6.2095F, -23.3535F, 0.0F, 0.3578F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntityGhidruth entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.swimAnimationState, GhidruthAnimation.GHIDRUTH_SWIM, ageInTicks);
		this.animate(entity.biteAnimationState, GhidruthAnimation.GHIDRUTH_BITE, ageInTicks);
		this.animate(entity.tailSwingAnimationState, GhidruthAnimation.GHIDRUTH_TAIL_SWING, ageInTicks);
		float pi = ((float)Math.PI / 180F);
	    ModelPart rearBody = this.MainRootThing.getChild("root2").getChild("Head").getChild("Body").getChild("RearBody");
	    ModelPart tail = this.MainRootThing.getChild("root2").getChild("Head").getChild("Body").getChild("RearBody").getChild("Tail");
	    ModelPart head = this.MainRootThing.getChild("root2").getChild("Head");
	    AbyssNetwork.CHANNEL.sendToServer(new ModelDataSyncPacket(entity, 0.0F, (rearBody.yRot / pi) + (tail.yRot / pi) + entity.yBodyRot, 0.0F, ModelDataSyncPacket.ModelType.TAIL_ROT));
	    AbyssNetwork.CHANNEL.sendToServer(new ModelDataSyncPacket(entity, 0.0F, head.xRot / pi, 0.0F, ModelDataSyncPacket.ModelType.HEAD_ROT));
	    AbyssNetwork.CHANNEL.sendToServer(new ModelDataSyncPacket(entity, head.x, -head.y, head.z, ModelDataSyncPacket.ModelType.HEAD_POS));
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		MainRootThing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() 
	{
		return this.MainRootThing;
	}
}