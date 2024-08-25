package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalBulbray;
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

public class ModelAbyssalBulbray extends HierarchicalModel<EntityAbyssalBulbray> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "abyssal_bulbray"), "main");
	private final ModelPart root;

	public ModelAbyssalBulbray(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Mantangler = root.addOrReplaceChild("Mantangler", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body = Mantangler.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.5F, -4.0F, -17.0F, 29.0F, 4.0F, 34.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -4.0F, -4.0F));

		PartDefinition Fin = Body.addOrReplaceChild("Fin", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.0F));

		Fin.addOrReplaceChild("Left", CubeListBuilder.create().texOffs(77, 39).addBox(-1.0F, -1.0F, -12.0F, 23.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(14.5F, -1.0F, -7.0F));

		Fin.addOrReplaceChild("Right", CubeListBuilder.create().texOffs(0, 74).addBox(-22.0F, -1.0F, -12.0F, 23.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.5F, -1.0F, -7.0F));

		Fin.addOrReplaceChild("Left2", CubeListBuilder.create().texOffs(0, 120).addBox(-6.0F, -1.0F, -5.5F, 15.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(14.5F, 0.0F, 11.5F));

		Fin.addOrReplaceChild("Right2", CubeListBuilder.create().texOffs(118, 83).addBox(-9.0F, -1.0F, -5.5F, 15.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.5F, 0.0F, 11.5F));

		PartDefinition Antenna = Body.addOrReplaceChild("Antenna", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.1342F, -8.4052F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -12.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition Antenna2 = Antenna.addOrReplaceChild("Antenna2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.6342F, -11.1552F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -8.25F));

		Antenna2.addOrReplaceChild("Antenna3", CubeListBuilder.create().texOffs(139, 101).addBox(-1.5021F, -0.6342F, -14.9385F, 3.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(93, 0).addBox(-2.5021F, -1.6342F, -21.0385F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 74).addBox(2.4979F, 0.8658F, -21.0385F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-3.5021F, 0.8658F, -21.0385F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(19, 0).addBox(-0.0021F, -2.6342F, -21.0385F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(18, 16).addBox(-0.0021F, 3.3658F, -21.0385F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -11.0167F, 0.0F, 0.0F, -0.0025F));

		PartDefinition Body2 = Body.addOrReplaceChild("Body2", CubeListBuilder.create().texOffs(0, 39).addBox(-11.5F, -2.0F, -15.0F, 23.0F, 4.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 2.0F));

		Body2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(144, 0).mirror().addBox(-3.0F, 0.0F, -14.0F, 6.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(144, 0).addBox(-12.0F, 0.0F, -14.0F, 6.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 1.25F, -15.0F, 0.3491F, 0.0F, 0.0F));

		Body2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 39).addBox(-2.5F, 0.0F, -10.0F, 4.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 50).addBox(-19.5F, 0.0F, -10.0F, 4.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 0.5F, -15.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition Fin2 = Body2.addOrReplaceChild("Fin2", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.0F));

		Fin2.addOrReplaceChild("Bigfinleft", CubeListBuilder.create().texOffs(55, 103).addBox(0.0F, 0.0F, -8.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(11.5F, 2.0F, -7.0F));

		Fin2.addOrReplaceChild("Bigfinright", CubeListBuilder.create().texOffs(0, 101).addBox(-18.0F, 0.0F, -8.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.5F, 2.0F, -7.0F));

		Fin2.addOrReplaceChild("Smallfinleft", CubeListBuilder.create().texOffs(93, 0).addBox(0.0F, 0.0F, -7.0F, 11.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(11.5F, 2.0F, 11.0F));

		Fin2.addOrReplaceChild("Smallfinright", CubeListBuilder.create().texOffs(67, 74).addBox(-11.0F, 0.0F, -7.0F, 11.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.5F, 2.0F, 11.0F));

		PartDefinition Tails = Mantangler.addOrReplaceChild("Tails", CubeListBuilder.create().texOffs(105, 103).addBox(-2.5F, -2.0F, -1.0F, 5.0F, 5.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(97, 132).addBox(0.0F, -7.0F, 0.0F, 0.0F, 5.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 13.0F));

		PartDefinition Tails2 = Tails.addOrReplaceChild("Tails2", CubeListBuilder.create().texOffs(68, 126).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 22.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.75F, 22.0F));

		Tails2.addOrReplaceChild("Tails3", CubeListBuilder.create().texOffs(39, 122).addBox(-1.5F, -1.5F, 0.5F, 3.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(118, 66).addBox(-7.5F, 0.25F, 12.5F, 15.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 20.5F));

		PartDefinition Tailfinleft = Tails.addOrReplaceChild("Tailfinleft", CubeListBuilder.create().texOffs(148, 29).addBox(0.0F, 0.0F, -5.5F, 8.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 1.001F, 19.5F));

		Tailfinleft.addOrReplaceChild("Tailfinleft2", CubeListBuilder.create().texOffs(0, 138).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 5.5F));

		PartDefinition Tailfinright = Tails.addOrReplaceChild("Tailfinright", CubeListBuilder.create().texOffs(144, 15).addBox(-8.0F, 0.0F, -5.5F, 8.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 1.001F, 19.5F));

		Tailfinright.addOrReplaceChild("Tailfinright2", CubeListBuilder.create().texOffs(120, 132).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 5.5F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityAbyssalBulbray entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("Mantangler"), netHeadYaw, headPitch);
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