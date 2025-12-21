package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.projectile.EntityMutavoreCyst;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelMutavoreCyst extends EntityModel<EntityMutavoreCyst>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "mutavore_cyst"), "main");
	private final ModelPart cyst;
	private final ModelPart mine;
	private final ModelPart mine2;
	private final ModelPart mine3;
	private final ModelPart mine4;

	public ModelMutavoreCyst(ModelPart root) 
	{
		this.cyst = root.getChild("cyst");
		this.mine = this.cyst.getChild("mine");
		this.mine2 = this.cyst.getChild("mine2");
		this.mine3 = this.cyst.getChild("mine3");
		this.mine4 = this.cyst.getChild("mine4");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition cyst = partdefinition.addOrReplaceChild("cyst", CubeListBuilder.create(), PartPose.offset(0.0F, 19.75F, 0.0F));

		cyst.addOrReplaceChild("mine", CubeListBuilder.create().texOffs(224, 79).addBox(-3.5F, -5.0F, -3.5F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(115, 39).mirror().addBox(-6.5F, 2.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(115, 39).addBox(-6.5F, -2.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, 0.0F));

		cyst.addOrReplaceChild("mine2", CubeListBuilder.create().texOffs(224, 79).mirror().addBox(-3.5F, -5.0F, -3.5F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(115, 39).addBox(-6.5F, -2.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(115, 39).mirror().addBox(-6.5F, 2.0F, -6.5F, 13.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -0.75F, 0.0F));

		cyst.addOrReplaceChild("mine3", CubeListBuilder.create().texOffs(55, 64).addBox(-5.3333F, -3.5F, -3.5F, 10.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(-2.3333F, -6.5F, -6.5F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(2.6667F, -6.5F, -6.5F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3333F, 0.75F, 0.0F));

		cyst.addOrReplaceChild("mine4", CubeListBuilder.create().texOffs(56, 91).addBox(-4.6667F, -3.5F, -3.5F, 10.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(-2.6667F, -6.5F, -6.5F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(128, 26).addBox(2.3333F, -6.5F, -6.5F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3333F, 0.75F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(EntityMutavoreCyst entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.mine.visible = entity.getCystType() == 0;
		this.mine2.visible = entity.getCystType() == 1;
		this.mine3.visible = entity.getCystType() == 2;
		this.mine4.visible = entity.getCystType() == 3;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		cyst.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}