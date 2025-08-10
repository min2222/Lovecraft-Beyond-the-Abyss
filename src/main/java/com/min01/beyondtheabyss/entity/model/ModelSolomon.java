package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.EntitySolomon;
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

public class ModelSolomon extends HierarchicalModel<EntitySolomon>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "solomon"), "main");
	private final ModelPart root;
	private final ModelPart robe;
	private final ModelPart head;
	private final ModelPart open_robe;

	public ModelSolomon(ModelPart root)
	{
		this.root = root.getChild("root");
		this.robe = this.root.getChild("robe");
		this.head = this.robe.getChild("head");
		this.open_robe = this.robe.getChild("open_robe");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition robe = root.addOrReplaceChild("robe", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, -3.0F, 12.0F, 29.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition head = robe.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 35).addBox(-4.0F, -5.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, 1.0F));

		head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(36, 24).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 0).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -8.0F));

		PartDefinition open_robe = robe.addOrReplaceChild("open_robe", CubeListBuilder.create().texOffs(0, 35).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 24.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 4.0F, -3.0F));

		open_robe.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(109, 8).mirror().addBox(0.0F, -4.0F, 0.0F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, 10.0F, 1.0F, 0.0F, -0.3927F, 0.0F));

		open_robe.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(109, 8).addBox(-3.0F, -4.0F, 0.0F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 10.0F, 1.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition right_arm = robe.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(52, 19).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(62, 31).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.0F, 5.0F, 0.0F));

		right_arm.addOrReplaceChild("broken_crown", CubeListBuilder.create().texOffs(30, 51).addBox(-1.0F, -1.0F, -3.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 8.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		robe.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(52, 19).mirror().addBox(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(62, 31).mirror().addBox(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, 5.0F, 0.0F));

		robe.addOrReplaceChild("trailing_robe", CubeListBuilder.create().texOffs(66, 22).addBox(-6.0F, -3.0F, 0.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(47, 0).addBox(-6.0F, 0.0F, 3.0F, 12.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySolomon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.head, netHeadYaw, headPitch);
		this.open_robe.visible = false;
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}