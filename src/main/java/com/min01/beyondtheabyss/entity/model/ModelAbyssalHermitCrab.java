package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalHermitCrab;
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

public class ModelAbyssalHermitCrab extends HierarchicalModel<EntityAbyssalHermitCrab> 
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "abyssal_hermit_crab"), "main");
	private final ModelPart AbyssHermit;

	public ModelAbyssalHermitCrab(ModelPart root)
	{
		this.AbyssHermit = root.getChild("AbyssHermit");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition AbyssHermit = partdefinition.addOrReplaceChild("AbyssHermit", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		AbyssHermit.addOrReplaceChild("Shell", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -10.5F, -8.75F, 17.0F, 11.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(69, 65).addBox(-1.5F, -12.5F, 0.25F, 3.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(59, 81).addBox(0.0F, -13.5F, 0.25F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(44, 28).addBox(-8.5F, 0.5F, -4.75F, 17.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(47, 43).addBox(-5.5F, -6.5F, 7.25F, 11.0F, 7.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 54).addBox(-5.5F, 0.5F, 7.25F, 11.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, 2.5F, -0.4363F, 0.0F, 0.0F));

		PartDefinition Crab = AbyssHermit.addOrReplaceChild("Crab", CubeListBuilder.create().texOffs(0, 28).addBox(-6.5F, -4.1072F, -8.6231F, 13.0F, 8.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.8928F, -0.3769F));

		Crab.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 54).mirror().addBox(0.0F, -2.25F, -3.5F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.25F, -0.1072F, -8.6231F, 0.3054F, 0.3491F, -1.5708F));

		Crab.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 54).addBox(0.0F, -2.25F, -3.5F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, -0.1072F, -8.6231F, 0.3054F, -0.3491F, 1.5708F));

		PartDefinition Legs = Crab.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(0.0F, 7.8928F, 4.3769F));

		PartDefinition Right = Legs.addOrReplaceChild("Right", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.5F, -6.0F, -1.0F, 0.0F, 0.0F, -0.7854F));

		Right.addOrReplaceChild("1", CubeListBuilder.create().texOffs(85, 65).addBox(-8.0F, -2.0F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 0.0F, -0.2618F, 0.0F));

		Right.addOrReplaceChild("2", CubeListBuilder.create().texOffs(85, 0).addBox(-8.0F, -2.0F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition Left = Legs.addOrReplaceChild("Left", CubeListBuilder.create(), PartPose.offsetAndRotation(6.5F, -6.0F, -1.0F, 0.0F, 0.0F, 0.7854F));

		Left.addOrReplaceChild("4", CubeListBuilder.create().texOffs(67, 17).addBox(-2.0F, -2.0F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 0.0F, 0.2618F, 0.0F));

		Left.addOrReplaceChild("5", CubeListBuilder.create().texOffs(51, 0).addBox(-2.0F, -2.0F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		Legs.addOrReplaceChild("Frontclawaleg", CubeListBuilder.create().texOffs(0, 85).addBox(0.0F, -2.6667F, -8.75F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(17, 75).addBox(-1.5F, -1.6667F, -8.75F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(51, 7).addBox(-1.5F, 1.3333F, -8.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -6.3333F, -10.25F, 0.4606F, -0.3152F, -0.1526F));

		Legs.addOrReplaceChild("Frontclawaleg2", CubeListBuilder.create().texOffs(34, 82).addBox(0.0F, -2.6667F, -8.75F, 0.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 71).addBox(-1.5F, -1.6667F, -8.75F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(-1.5F, 1.3333F, -8.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -6.3333F, -10.25F, 0.4606F, 0.3152F, 0.1526F));

		PartDefinition Arms = Crab.addOrReplaceChild("Arms", CubeListBuilder.create(), PartPose.offset(0.0F, -2.1072F, -7.6231F));

		PartDefinition Leftarm = Arms.addOrReplaceChild("Leftarm", CubeListBuilder.create().texOffs(67, 0).addBox(-1.5F, -3.25F, -10.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.01F))
		.texOffs(87, 15).addBox(0.0F, -5.25F, -7.0F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 0.25F, 0.0F, -0.2618F, 0.0F, 1.3963F));

		Leftarm.addOrReplaceChild("Leftarm2", CubeListBuilder.create().texOffs(84, 43).addBox(-1.5F, -0.5F, -8.25F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, 2.5F, -6.25F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.25F, 0.2618F, 0.0F, 0.0F));

		PartDefinition Rightarm = Arms.addOrReplaceChild("Rightarm", CubeListBuilder.create().texOffs(40, 65).addBox(-1.5F, -3.25F, -10.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.01F))
		.texOffs(12, 89).addBox(0.0F, -5.25F, -7.0F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, 0.25F, 0.0F, -0.2618F, 0.0F, -1.3963F));

		Rightarm.addOrReplaceChild("Rightarm2", CubeListBuilder.create().texOffs(80, 81).addBox(-1.5F, -0.5F, -8.25F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(0.0F, 2.5F, -6.25F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.25F, 0.2618F, 0.0F, 0.0F));

		PartDefinition Eyes = Crab.addOrReplaceChild("Eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 6.8928F, 0.3769F));

		Eyes.addOrReplaceChild("Lefteye", CubeListBuilder.create().texOffs(44, 28).addBox(-0.75F, -6.0F, 0.0F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -11.0F, -8.0F));

		Eyes.addOrReplaceChild("Righteye", CubeListBuilder.create().texOffs(8, 28).addBox(-3.25F, -6.0F, 0.0F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.25F, -11.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityAbyssalHermitCrab entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		AbyssHermit.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public ModelPart root() 
	{
		return this.AbyssHermit;
	}
}