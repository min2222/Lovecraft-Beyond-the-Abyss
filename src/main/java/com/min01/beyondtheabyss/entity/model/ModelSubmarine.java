package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
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

public class ModelSubmarine extends HierarchicalModel<EntitySubmarine> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "submarine"), "main");
	private final ModelPart root;

	public ModelSubmarine(ModelPart root) 
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition submarine = root.addOrReplaceChild("submarine", CubeListBuilder.create().texOffs(268, 236).addBox(11.2115F, -2.9231F, 17.25F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 353).addBox(0.7115F, -36.9231F, 37.25F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(300, 240).mirror().addBox(-15.7885F, -4.9231F, 19.25F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 246).mirror().addBox(-14.7885F, -11.9231F, 20.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 255).mirror().addBox(-14.7885F, -11.9231F, 22.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 255).mirror().addBox(14.2115F, -11.9231F, 22.25F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 246).addBox(14.2115F, -11.9231F, 20.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(268, 236).mirror().addBox(-17.7885F, -2.9231F, 17.25F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 240).addBox(13.2115F, -4.9231F, 19.25F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(232, 60).addBox(-22.7885F, 15.0769F, -64.75F, 47.0F, 9.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-5.7885F, -2.9231F, -39.75F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(8.2115F, -2.9231F, 5.25F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-18.7885F, -2.9231F, -22.75F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(8.2115F, -2.9231F, -22.75F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-18.7885F, -2.9231F, 5.25F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 273).addBox(13.2115F, 6.0769F, -62.75F, 9.0F, 9.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 273).mirror().addBox(-20.7885F, 6.0769F, -62.75F, 9.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 300).addBox(-11.7885F, 6.0769F, -62.75F, 25.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(129, 264).addBox(-9.7885F, -28.9231F, -16.75F, 21.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 184).addBox(-7.7885F, -28.9231F, -16.75F, 17.0F, 41.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 209).addBox(20.2115F, -16.9231F, 11.25F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(232, 93).addBox(-19.7885F, -3.9231F, -35.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 88).addBox(-17.7885F, -7.4231F, -38.75F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.7115F, -44.0769F, 8.75F));

		submarine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7115F, -0.4231F, 46.25F, 0.0F, 0.0F, 1.5708F));

		submarine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7115F, -0.4231F, 46.25F, 0.0F, 0.0F, -3.1416F));

		submarine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7115F, -0.4231F, 46.25F, 0.0F, 0.0F, -1.5708F));

		submarine.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-25.5F, -5.0F, -35.0F, 51.0F, 10.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7115F, 22.0769F, -9.75F));

		PartDefinition back = submarine.addOrReplaceChild("back", CubeListBuilder.create().texOffs(150, 203).addBox(-20.5F, -21.5F, -9.0F, 41.0F, 43.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7115F, -0.4231F, 34.25F));

		back.addOrReplaceChild("turbine", CubeListBuilder.create().texOffs(251, 203).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(232, 136).addBox(-10.5F, -10.5F, 0.5F, 21.0F, 21.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(250, 211).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		submarine.addOrReplaceChild("controller", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2115F, 14.5769F, -44.75F));

		submarine.addOrReplaceChild("seat1", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(14.2115F, 14.5769F, -27.75F));

		submarine.addOrReplaceChild("seat2", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.7885F, 14.5769F, -27.75F));

		submarine.addOrReplaceChild("seat3", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(14.2115F, 14.5769F, 0.25F));

		submarine.addOrReplaceChild("seat4", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.7885F, 14.5769F, 0.25F));

		submarine.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(6, 276).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 282).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.2115F, 3.1026F, -57.3584F, -0.1309F, 0.0F, 0.0F));

		submarine.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(6, 276).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 282).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.7885F, 3.2578F, -56.7239F, -0.3491F, 0.0F, 0.0F));

		submarine.addOrReplaceChild("front", CubeListBuilder.create().texOffs(22, 130).addBox(21.5F, 4.6364F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(152, 110).addBox(11.5F, -23.3636F, -10.8182F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 50).addBox(11.5F, -23.3636F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-13.5F, -23.3636F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(172, 0).addBox(-21.5F, -21.3636F, -8.8182F, 43.0F, 42.0F, 18.0F, new CubeDeformation(0.01F))
		.texOffs(152, 80).addBox(-13.5F, -23.3636F, -10.8182F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(101, 178).addBox(-5.5F, -25.3636F, 58.1818F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-13.5F, 6.6364F, -10.8182F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 89).addBox(-23.5F, 4.6364F, -10.8182F, 47.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(11.5F, 6.6364F, -10.8182F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 128).addBox(-23.5F, 4.6364F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7115F, -5.5594F, -53.9318F));
		
		submarine.addOrReplaceChild("hatch", CubeListBuilder.create().texOffs(34, 0).addBox(-5.5F, -6.875F, -5.5F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(13, 13).addBox(-1.5F, -6.875F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(80, 182).addBox(-10.5F, -3.875F, -10.5F, 21.0F, 8.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(80, 157).addBox(-4.5F, 4.125F, -1.0F, 9.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7115F, -33.0481F, -6.25F));

		submarine.addOrReplaceChild("l_wall", CubeListBuilder.create().texOffs(0, 157).addBox(-2.55F, -22.95F, -34.95F, 5.0F, 46.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(0, 80).addBox(-2.55F, -15.95F, -10.95F, 10.0F, 23.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(23.7615F, -5.9731F, -9.8F));

		submarine.addOrReplaceChild("r_wall", CubeListBuilder.create().texOffs(152, 87).addBox(-2.45F, -23.05F, -34.85F, 5.0F, 46.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.45F, -16.05F, -10.85F, 10.0F, 23.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(-22.3385F, -5.8731F, -9.9F));

		PartDefinition l_leg = submarine.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(0, 319).addBox(-5.26F, -1.0921F, -38.0F, 17.0F, 17.0F, 76.0F, new CubeDeformation(0.0F)), PartPose.offset(31.4716F, 27.919F, -10.25F));

		l_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(48, 370).addBox(21.5F, -38.5F, -46.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(48, 370).addBox(21.5F, -38.5F, -7.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.24F, 34.6579F, 23.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition r_leg = submarine.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(0, 319).mirror().addBox(-11.74F, -1.0921F, -38.0F, 17.0F, 17.0F, 76.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-30.0485F, 27.919F, -10.25F));

		r_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 370).mirror().addBox(-28.5F, -38.5F, -46.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 370).mirror().addBox(-28.5F, -38.5F, -7.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.24F, 34.6579F, 23.5F, 0.0F, 0.0F, 0.829F));

		submarine.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 80).addBox(-20.5F, -3.5F, -35.0F, 41.0F, 7.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7115F, -25.4231F, -9.75F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntitySubmarine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root, netHeadYaw, headPitch);
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