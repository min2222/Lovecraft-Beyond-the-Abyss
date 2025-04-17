package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosBody;
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

public class ModelKormosBody extends HierarchicalModel<EntityKormosBody>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "kormos_body"), "main");
	private final ModelPart root;
	private final ModelPart neck;
	private final ModelPart front_body;
	private final ModelPart body;
	private final ModelPart back_body;

	public ModelKormosBody(ModelPart root) {
		this.root = root.getChild("root");
		this.neck = this.root.getChild("neck");
		this.front_body = this.root.getChild("front_body");
		this.body = this.root.getChild("body");
		this.back_body = this.root.getChild("back_body");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(368, 114).addBox(-47.0F, -16.0F, 4.0F, 94.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(368, 0).addBox(-37.0F, -66.0F, -24.0F, 74.0F, 66.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		root.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(125, 740).addBox(-37.0F, -66.0F, -40.0F, 74.0F, 66.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(772, 907).addBox(61.0F, -32.0F, -20.0F, 72.0F, 20.0F, 54.0F, new CubeDeformation(0.0F))
		.texOffs(0, 919).addBox(37.0F, -32.0F, -20.0F, 24.0F, 20.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(638, 842).addBox(-61.0F, -32.0F, -20.0F, 24.0F, 20.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(502, 686).addBox(-133.0F, -32.0F, -20.0F, 72.0F, 20.0F, 54.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(125, 740).addBox(-37.0F, -66.0F, -40.0F, 74.0F, 66.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(0, 618).addBox(-47.0F, -14.0F, -37.0F, 94.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(820, 1008).addBox(-47.0F, -14.0F, -20.0F, 94.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1008).addBox(-47.0F, -14.0F, -4.0F, 94.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(727, 426).addBox(-47.0F, -14.0F, 11.0F, 94.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(704, 498).addBox(-47.0F, -14.0F, 26.0F, 94.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		root.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(125, 740).addBox(-37.0F, -66.0F, -40.0F, 74.0F, 66.0F, 80.0F, new CubeDeformation(0.0F))
		.texOffs(650, 848).addBox(-55.0F, -30.0F, -15.0F, 18.0F, 18.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(520, 694).addBox(-117.0F, -30.0F, -15.0F, 62.0F, 18.0F, 46.0F, new CubeDeformation(0.0F))
		.texOffs(650, 848).mirror().addBox(37.0F, -30.0F, -15.0F, 18.0F, 18.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(520, 694).mirror().addBox(55.0F, -30.0F, -15.0F, 62.0F, 18.0F, 46.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 1024, 1024);
	}

	@Override
	public void setupAnim(EntityKormosBody entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.neck, netHeadYaw, headPitch);
		BTAClientUtil.animateHead(this.front_body, netHeadYaw, headPitch);
		BTAClientUtil.animateHead(this.body, netHeadYaw, headPitch);
		BTAClientUtil.animateHead(this.back_body, netHeadYaw, headPitch);
		this.neck.visible = entity.getIndex() == 0;
		this.front_body.visible = entity.getIndex() == 1;
		this.body.visible = entity.getIndex() > 1 && entity.getIndex() != entity.getChainLength() - 4;
 		this.back_body.visible = entity.getIndex() == entity.getChainLength() - 4;
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