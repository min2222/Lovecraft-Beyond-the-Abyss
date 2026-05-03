package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusTailEntity;
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

public class ForneusTailModel extends HierarchicalModel<ForneusTailEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "forneus_tail"), "main");
	private final ModelPart root;
	private final ModelPart tails;

	public ForneusTailModel(ModelPart root) 
	{
		this.root = root.getChild("root");
		this.tails = this.root.getChild("tails");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tails = root.addOrReplaceChild("tails", CubeListBuilder.create().texOffs(0, 688).addBox(-56.0F, -31.2202F, -108.0F, 112.0F, 112.0F, 216.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -80.7798F, 5.0F));

		tails.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 1016).addBox(0.0F, -143.0F, -100.0F, 84.0F, 55.0F, 220.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-48.0F, 81.7798F, -10.0F, 0.0F, 0.0F, 0.3491F));

		tails.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(656, 1008).addBox(-84.0F, -143.0F, -100.0F, 84.0F, 55.0F, 220.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(48.0F, 81.7798F, -10.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition leg1 = tails.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-44.0F, 69.7798F, -82.0F));

		leg1.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(608, 1184).addBox(-50.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 1068).addBox(-50.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg2 = tails.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(44.0F, 69.7798F, -82.0F));

		leg2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(552, 1375).addBox(42.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1360, 2703).addBox(-38.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg3 = tails.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-44.0F, 69.7798F, 78.0F));

		leg3.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(608, 1016).addBox(-50.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1088, 296).addBox(-50.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg4 = tails.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(44.0F, 69.7798F, 78.0F));

		leg4.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(552, 1417).addBox(42.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(2094, 2845).addBox(-38.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg5 = tails.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-44.0F, 69.7798F, 38.0F));

		leg5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(608, 1058).addBox(-50.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1600, 656).addBox(-50.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg6 = tails.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(44.0F, 69.7798F, 38.0F));

		leg6.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(552, 1459).addBox(42.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1644, 2787).addBox(-38.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg7 = tails.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-44.0F, 69.7798F, -2.0F));

		leg7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(608, 1100).addBox(-50.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 1008).addBox(-50.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg8 = tails.addOrReplaceChild("leg8", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(44.0F, 69.7798F, -2.0F));

		leg8.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(1200, 1475).addBox(42.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1702, 2703).addBox(-38.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg9 = tails.addOrReplaceChild("leg9", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-44.0F, 69.7798F, -42.0F));

		leg9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(608, 1142).addBox(-50.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 1038).addBox(-50.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg10 = tails.addOrReplaceChild("leg10", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -12.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(44.0F, 69.7798F, -42.0F));

		leg10.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(552, 1501).addBox(42.0F, 6.0F, 80.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1685, 2520).addBox(-38.0F, -14.0F, 80.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition tail = tails.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-4.5685F, 12.7798F, 110.0F));

		tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(704, 0).addBox(-40.0F, -120.0F, -98.0F, 80.0F, 80.0F, 216.0F, new CubeDeformation(0.0F))
		.texOffs(1240, 688).addBox(-40.0F, -136.0F, -100.0F, 88.0F, 34.0F, 220.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.0F, 68.0F, 96.0F, 0.0F, 0.0F, 0.7854F));

		tail.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(1848, 0).addBox(40.0F, -136.0F, -100.0F, 16.0F, 16.0F, 220.0F, new CubeDeformation(0.0F))
		.texOffs(1264, 942).addBox(-48.0F, -136.0F, -100.0F, 88.0F, 34.0F, 220.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(61.1371F, 68.0F, 96.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition fin = tail.addOrReplaceChild("fin", CubeListBuilder.create(), PartPose.offset(3.5685F, 12.0F, 180.0F));

		fin.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 1795).addBox(24.0F, -120.0F, -98.0F, 16.0F, 30.0F, 216.0F, new CubeDeformation(0.0F))
		.texOffs(1656, 1788).addBox(24.0F, -90.0F, -98.0F, 16.0F, 40.0F, 216.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -30.0F, 68.0F, 0.0F, 0.0F, 1.5708F));

		fin.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(1776, 1196).addBox(-40.0F, -90.0F, -98.0F, 16.0F, 40.0F, 216.0F, new CubeDeformation(0.0F))
		.texOffs(1104, 1800).addBox(-40.0F, -120.0F, -98.0F, 16.0F, 30.0F, 216.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, -30.0F, 68.0F, 0.0F, 0.0F, -1.5708F));

		return LayerDefinition.create(meshdefinition, 4096, 4096);
	}

	@Override
	public void setupAnim(ForneusTailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.tails, netHeadYaw, headPitch);
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