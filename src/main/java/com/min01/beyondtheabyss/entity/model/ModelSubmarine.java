package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.animation.SubmarineAnimation;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
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

		PartDefinition submarine = root.addOrReplaceChild("submarine", CubeListBuilder.create().texOffs(268, 236).addBox(10.5F, 0.0F, 27.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 353).addBox(0.0F, -34.0F, 47.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(300, 240).mirror().addBox(-16.5F, -2.0F, 29.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 246).mirror().addBox(-15.5F, -9.0F, 30.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 255).mirror().addBox(-15.5F, -9.0F, 32.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 255).mirror().addBox(13.5F, -9.0F, 32.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 246).addBox(13.5F, -9.0F, 30.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(268, 236).mirror().addBox(-18.5F, 0.0F, 27.0F, 8.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(300, 240).addBox(12.5F, -2.0F, 29.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(232, 60).addBox(-23.5F, 18.0F, -55.0F, 47.0F, 9.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-6.5F, 0.0F, -30.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(7.5F, 0.0F, 15.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-19.5F, 0.0F, -13.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(7.5F, 0.0F, -13.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(302, 22).addBox(-19.5F, 0.0F, 15.0F, 12.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 273).addBox(12.5F, 9.0F, -53.0F, 9.0F, 9.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 273).mirror().addBox(-21.5F, 9.0F, -53.0F, 9.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 300).addBox(-12.5F, 9.0F, -53.0F, 25.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(129, 264).addBox(-10.5F, -26.0F, -7.0F, 21.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 184).addBox(-8.5F, -26.0F, -7.0F, 17.0F, 41.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(48, 209).addBox(19.5F, -14.0F, 21.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(232, 93).addBox(-20.5F, -1.0F, -25.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 88).addBox(-18.5F, -4.5F, -29.0F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -47.0F, -1.0F));

		submarine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, 56.0F, 0.0F, 0.0F, 1.5708F));

		submarine.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, 56.0F, 0.0F, 0.0F, -3.1416F));

		submarine.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 353).addBox(0.0F, -36.5F, -9.0F, 0.0F, 23.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, 56.0F, 0.0F, 0.0F, -1.5708F));

		submarine.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-25.5F, -5.0F, -35.0F, 51.0F, 10.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

		PartDefinition back = submarine.addOrReplaceChild("back", CubeListBuilder.create().texOffs(150, 203).addBox(-20.5F, -21.5F, -9.0F, 41.0F, 43.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 44.0F));

		back.addOrReplaceChild("turbine", CubeListBuilder.create().texOffs(251, 203).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(232, 136).addBox(-10.5F, -10.5F, 0.5F, 21.0F, 21.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(250, 211).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		submarine.addOrReplaceChild("controller", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 17.5F, -35.0F));

		submarine.addOrReplaceChild("seat1", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(13.5F, 17.5F, -18.0F));

		submarine.addOrReplaceChild("seat2", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.5F, 17.5F, -18.0F));

		submarine.addOrReplaceChild("seat3", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(13.5F, 17.5F, 10.0F));

		submarine.addOrReplaceChild("seat4", CubeListBuilder.create().texOffs(302, 45).addBox(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.5F, 17.5F, 10.0F));

		submarine.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(6, 276).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 282).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, 6.0257F, -47.6084F, -0.1309F, 0.0F, 0.0F));

		submarine.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(6, 276).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 282).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 6.1809F, -46.9739F, -0.3491F, 0.0F, 0.0F));

		submarine.addOrReplaceChild("front", CubeListBuilder.create().texOffs(22, 130).addBox(21.5F, 4.6364F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(152, 110).addBox(11.5F, -23.3636F, -10.8182F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 50).addBox(11.5F, -23.3636F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-13.5F, -23.3636F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(172, 0).addBox(-21.5F, -21.3636F, -8.8182F, 43.0F, 42.0F, 18.0F, new CubeDeformation(0.01F))
		.texOffs(152, 80).addBox(-13.5F, -23.3636F, -10.8182F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-13.5F, 6.6364F, -10.8182F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(232, 89).addBox(-23.5F, 4.6364F, -10.8182F, 47.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(11.5F, 6.6364F, -10.8182F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 128).addBox(-23.5F, 4.6364F, -8.8182F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.6364F, -44.1818F));

		PartDefinition hatch = submarine.addOrReplaceChild("hatch", CubeListBuilder.create().texOffs(13, 13).addBox(-1.5F, -10.875F, -12.375F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(80, 182).addBox(-10.5F, -7.875F, -21.375F, 21.0F, 8.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(80, 157).addBox(-4.5F, 0.125F, -11.875F, 9.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(101, 178).addBox(-5.5F, -1.875F, -0.375F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.125F, 14.375F));

		hatch.addOrReplaceChild("valve", CubeListBuilder.create().texOffs(34, 0).addBox(-5.5F, 0.0F, -5.5F, 11.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.875F, -10.875F));

		submarine.addOrReplaceChild("l_wall", CubeListBuilder.create().texOffs(0, 157).addBox(-2.55F, -22.95F, -34.95F, 5.0F, 46.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(0, 80).addBox(-2.55F, -15.95F, -10.95F, 10.0F, 23.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(23.05F, -3.05F, -0.05F));

		submarine.addOrReplaceChild("r_wall", CubeListBuilder.create().texOffs(152, 87).addBox(-2.45F, -23.05F, -34.85F, 5.0F, 46.0F, 70.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.45F, -16.05F, -10.85F, 10.0F, 23.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(-23.05F, -2.95F, -0.15F));

		PartDefinition l_leg = submarine.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(0, 319).addBox(-5.26F, -1.0921F, -38.0F, 17.0F, 17.0F, 76.0F, new CubeDeformation(0.0F)), PartPose.offset(30.76F, 30.8421F, -0.5F));

		l_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(48, 370).addBox(21.5F, -38.5F, -46.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(48, 370).addBox(21.5F, -38.5F, -7.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.24F, 34.6579F, 23.5F, 0.0F, 0.0F, -0.829F));

		PartDefinition r_leg = submarine.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(0, 319).mirror().addBox(-11.74F, -1.0921F, -38.0F, 17.0F, 17.0F, 76.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-30.76F, 30.8421F, -0.5F));

		r_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 370).mirror().addBox(-28.5F, -38.5F, -46.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 370).mirror().addBox(-28.5F, -38.5F, -7.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.24F, 34.6579F, 23.5F, 0.0F, 0.0F, 0.829F));

		submarine.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 80).addBox(-20.5F, -3.5F, -35.0F, 41.0F, 7.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(EntitySubmarine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("submarine"), netHeadYaw, headPitch);
		this.animateWalk(SubmarineAnimation.SUBMARINE_TURBINE_SPIN, limbSwing, limbSwingAmount, 2.5F, 2.5F);
		entity.openHatchAnimationState.animate(this, SubmarineAnimation.SUBMARINE_OPEN_HATCH, ageInTicks);
		entity.closeHatchAnimationState.animate(this, SubmarineAnimation.SUBMARINE_CLOSE_HATCH, ageInTicks);
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