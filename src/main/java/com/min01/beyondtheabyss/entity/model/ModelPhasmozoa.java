package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
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

public class ModelPhasmozoa extends HierarchicalModel<EntityPhasmozoa> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "phasmozoa"), "main");
	private final ModelPart phasmozoa;

	public ModelPhasmozoa(ModelPart root)
	{
		this.phasmozoa = root.getChild("phasmozoa");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition phasmozoa = partdefinition.addOrReplaceChild("phasmozoa", CubeListBuilder.create(), PartPose.offset(0.0F, 54.0F, 0.0F));

		PartDefinition Outer = phasmozoa.addOrReplaceChild("Outer", CubeListBuilder.create().texOffs(0, 36).addBox(-8.0F, -32.0F, -8.0F, 16.0F, 26.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-11.0F, -6.0F, -11.0F, 22.0F, 13.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 0.0F));

		PartDefinition Leftfin = Outer.addOrReplaceChild("Leftfin", CubeListBuilder.create().texOffs(99, 36).addBox(0.0F, 0.0F, -2.5F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -1.999F, 0.0F, 0.0F, 0.0F, 1.309F));

		PartDefinition Leftfin2 = Leftfin.addOrReplaceChild("Leftfin2", CubeListBuilder.create().texOffs(106, 106).addBox(0.0F, 0.0F, -2.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, 0.0F));

		Leftfin2.addOrReplaceChild("Leftfin3", CubeListBuilder.create().texOffs(87, 99).addBox(0.0F, 0.0F, -2.5F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 0.0F));

		PartDefinition Rightfin = Outer.addOrReplaceChild("Rightfin", CubeListBuilder.create().texOffs(67, 14).addBox(-10.0F, 0.0F, -2.5F, 10.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -1.999F, 0.0F, 0.0F, 0.0F, -1.309F));

		PartDefinition Rightfin2 = Rightfin.addOrReplaceChild("Rightfin2", CubeListBuilder.create().texOffs(86, 105).addBox(-7.0F, 0.0F, -2.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

		Rightfin2.addOrReplaceChild("Rightfin3", CubeListBuilder.create().texOffs(0, 103).addBox(-9.0F, 0.0F, -2.5F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 0.0F, 0.0F));

		PartDefinition FrontFin = Outer.addOrReplaceChild("FrontFin", CubeListBuilder.create().texOffs(89, 14).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.999F, -11.0F, 1.309F, 0.0F, 0.0F));

		PartDefinition FrontFin2 = FrontFin.addOrReplaceChild("FrontFin2", CubeListBuilder.create().texOffs(24, 98).addBox(-2.5F, 0.0F, -7.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

		FrontFin2.addOrReplaceChild("FrontFin3", CubeListBuilder.create().texOffs(92, 71).addBox(-2.5F, 0.0F, -9.0F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition BackFin = Outer.addOrReplaceChild("BackFin", CubeListBuilder.create().texOffs(89, 25).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.999F, 11.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition BackFin2 = BackFin.addOrReplaceChild("BackFin2", CubeListBuilder.create().texOffs(49, 36).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

		BackFin2.addOrReplaceChild("BackFin3", CubeListBuilder.create().texOffs(0, 93).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

		PartDefinition Body = phasmozoa.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(65, 36).addBox(-5.5F, -19.0F, -5.5F, 11.0F, 23.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -46.0F, 0.0F));

		PartDefinition Body2 = Body.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(87, 85).addBox(-3.5F, -7.5F, -3.5F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.5F, 0.0F));

		Body2.addOrReplaceChild("PosionSac", CubeListBuilder.create().texOffs(42, 85).addBox(-5.5F, -5.0F, -5.5F, 11.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 79).addBox(-6.5F, -3.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(52, 71).addBox(-6.5F, -1.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(67, 0).addBox(-6.5F, 1.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(49, 44).addBox(-1.5F, 2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 0.0F));

		PartDefinition Tentacle = Body.addOrReplaceChild("Tentacle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Right = Tentacle.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(107, 6).addBox(-3.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 5.5F, 0.0F));

		PartDefinition t2 = Right.addOrReplaceChild("t2", CubeListBuilder.create().texOffs(92, 111).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

		PartDefinition t3 = t2.addOrReplaceChild("t3", CubeListBuilder.create().texOffs(81, 111).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t4 = t3.addOrReplaceChild("t4", CubeListBuilder.create().texOffs(35, 111).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t5 = t4.addOrReplaceChild("t5", CubeListBuilder.create().texOffs(110, 42).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t5.addOrReplaceChild("t6", CubeListBuilder.create().texOffs(65, 105).addBox(-5.0F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition Left = Tentacle.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(107, 0).addBox(-1.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 5.5F, 0.0F));

		PartDefinition t7 = Left.addOrReplaceChild("t7", CubeListBuilder.create().texOffs(11, 109).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition t8 = t7.addOrReplaceChild("t8", CubeListBuilder.create().texOffs(0, 109).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t9 = t8.addOrReplaceChild("t9", CubeListBuilder.create().texOffs(24, 106).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t10 = t9.addOrReplaceChild("t10", CubeListBuilder.create().texOffs(0, 36).addBox(0.0F, 0.0F, -2.5F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t10.addOrReplaceChild("t11", CubeListBuilder.create().texOffs(44, 105).addBox(0.0F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition Front = Tentacle.addOrReplaceChild("Front", CubeListBuilder.create().texOffs(40, 79).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, -4.5F));

		PartDefinition t12 = Front.addOrReplaceChild("t12", CubeListBuilder.create().texOffs(109, 81).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition t13 = t12.addOrReplaceChild("t13", CubeListBuilder.create().texOffs(76, 85).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t14 = t13.addOrReplaceChild("t14", CubeListBuilder.create().texOffs(0, 79).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t15 = t14.addOrReplaceChild("t15", CubeListBuilder.create().texOffs(67, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t15.addOrReplaceChild("t16", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition Back = Tentacle.addOrReplaceChild("Back", CubeListBuilder.create().texOffs(20, 93).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 4.5F));

		PartDefinition t17 = Back.addOrReplaceChild("t17", CubeListBuilder.create().texOffs(114, 112).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition t18 = t17.addOrReplaceChild("t18", CubeListBuilder.create().texOffs(103, 112).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t19 = t18.addOrReplaceChild("t19", CubeListBuilder.create().texOffs(112, 69).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition t20 = t19.addOrReplaceChild("t20", CubeListBuilder.create().texOffs(110, 58).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		t20.addOrReplaceChild("t21", CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityPhasmozoa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		phasmozoa.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.phasmozoa;
	}
}