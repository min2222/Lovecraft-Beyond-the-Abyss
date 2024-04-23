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
	private final ModelPart main;

	public ModelSubmarine(ModelPart root) 
	{
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-25.5F, 12.0F, -35.0F, 51.0F, 10.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(150, 203).addBox(-20.5F, -27.0F, 35.0F, 41.0F, 43.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(268, 236).addBox(10.5F, -8.0F, 27.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 353).addBox(0.0F, -42.0F, 47.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(300, 240).mirror().addBox(-16.5F, -10.0F, 29.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 246).mirror().addBox(-15.5F, -17.0F, 30.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 255).mirror().addBox(-15.5F, -17.0F, 32.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 255).mirror().addBox(13.5F, -17.0F, 32.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 246).addBox(13.5F, -17.0F, 30.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(268, 236).mirror().addBox(-18.5F, -8.0F, 27.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 240).addBox(12.5F, -10.0F, 29.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(250, 211).addBox(-3.5F, -9.0F, 51.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(232, 60).addBox(-23.5F, 10.0F, -55.0F, 47.0F, 9.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-6.5F, -8.0F, -30.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 45).addBox(-6.5F, 7.0F, -40.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(7.5F, -8.0F, 15.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 45).addBox(-19.5F, 7.0F, 5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-19.5F, -8.0F, -13.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 45).addBox(7.5F, 7.0F, -23.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(7.5F, -8.0F, -13.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 45).addBox(-19.5F, 7.0F, -23.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-19.5F, -8.0F, 15.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 45).addBox(7.5F, 7.0F, 5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 273).addBox(12.5F, 1.0F, -53.0F, 9.0F, 9.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 273).mirror().addBox(-21.5F, 1.0F, -53.0F, 9.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 300).addBox(-12.5F, 1.0F, -53.0F, 25.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(129, 264).addBox(-10.5F, -34.0F, -7.0F, 21.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 80).addBox(-20.5F, -34.0F, -35.0F, 41.0F, 7.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(0, 184).addBox(-8.5F, -34.0F, -7.0F, 17.0F, 41.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 157).addBox(20.5F, -34.0F, -35.0F, 5.0F, 46.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(48, 209).addBox(19.5F, -22.0F, 21.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 80).addBox(20.5F, -27.0F, -11.0F, 10.0F, 23.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-30.5F, -27.0F, -11.0F, 10.0F, 23.0F, 25.0F, new CubeDeformation(0.0F))
		.texOffs(152, 87).addBox(-25.5F, -34.0F, -35.0F, 5.0F, 46.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(232, 93).addBox(-20.5F, -9.0F, -25.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 88).addBox(-18.5F, -12.5F, -29.0F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, -1.0F));

		main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 56.0F, 0.0F, 0.0F, 1.5708F));

		main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 56.0F, 0.0F, 0.0F, -3.1416F));

		main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, 56.0F, 0.0F, 0.0F, -1.5708F));

		main.addOrReplaceChild("turbine", CubeListBuilder.create().texOffs(251, 203).addBox(-2.5F, -2.5F, 1.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(232, 136).addBox(-10.5F, -10.5F, 3.5F, 21.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 53.0F));

		main.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(6, 276).addBox(-1.5F, -7.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 282).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, 1.0F, -48.0F, -0.1309F, 0.0F, 0.0F));

		main.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(6, 276).addBox(-1.5F, -7.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 282).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 1.0F, -48.0F, -0.3491F, 0.0F, 0.0F));

		main.addOrReplaceChild("front", CubeListBuilder.create().texOffs(22, 130).addBox(21.5F, -15.5556F, 1.0556F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(152, 110).addBox(11.5F, -43.5556F, -0.9444F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 50).addBox(11.5F, -43.5556F, 1.0556F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-13.5F, -43.5556F, 1.0556F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(172, 0).addBox(-21.5F, -41.5556F, 1.0556F, 43.0F, 42.0F, 18.0F, new CubeDeformation(0.01F))
		.texOffs(152, 80).addBox(-13.5F, -43.5556F, -0.9444F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(101, 178).addBox(-5.5F, -45.5556F, 68.0556F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-13.5F, -13.5556F, -0.9444F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 89).addBox(-23.5F, -15.5556F, -0.9444F, 47.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(11.5F, -13.5556F, -0.9444F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 128).addBox(-23.5F, -15.5556F, 1.0556F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.5556F, -54.0556F));

		main.addOrReplaceChild("Hatch", CubeListBuilder.create().texOffs(34, 0).addBox(-5.5F, -10.0F, -16.0F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(13, 13).addBox(-1.5F, -10.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(80, 182).addBox(-10.5F, -7.0F, -21.0F, 21.0F, 8.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(80, 157).addBox(-4.5F, 1.0F, -11.5F, 9.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.0F, 14.0F));

		PartDefinition r_leg = main.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(0, 319).mirror().addBox(-42.5F, -51.25F, -39.5F, 17.0F, 17.0F, 76.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 73.0F, 1.0F));

		r_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(48, 370).mirror().addBox(-28.5F, -38.5F, -46.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 370).mirror().addBox(-28.5F, -38.5F, -7.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-34.0F, -15.5F, 22.0F, 0.0F, 0.0F, 0.829F));

		PartDefinition l_leg = main.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(0, 319).addBox(25.5F, -51.25F, -39.5F, 17.0F, 17.0F, 76.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 73.0F, 1.0F));

		l_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 370).addBox(21.5F, -38.5F, -46.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(48, 370).addBox(21.5F, -38.5F, -7.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(34.0F, -15.5F, 22.0F, 0.0F, 0.0F, -0.829F));

		main.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.8F, 40.0F, 1.0F));
		
		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntitySubmarine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.main, netHeadYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.main;
	}
}