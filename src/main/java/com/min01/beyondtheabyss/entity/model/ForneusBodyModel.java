package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusBodyEntity;
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

public class ForneusBodyModel extends HierarchicalModel<ForneusBodyEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "forneus_body"), "main");
	private final ModelPart root;
	private final ModelPart body;

	public ForneusBodyModel(ModelPart root)
	{
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -94.7523F, 5.0F));

		body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-80.0F, -160.0F, -101.0F, 160.0F, 160.0F, 192.0F, new CubeDeformation(0.0F))
		.texOffs(608, 1283).addBox(-96.0F, -120.0F, -105.0F, 56.0F, 136.0F, 200.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-56.5685F, 94.7523F, 5.0F, 0.0F, 0.0F, 0.7854F));

		body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1264, 1196).addBox(40.0F, -120.0F, -105.0F, 56.0F, 136.0F, 200.0F, new CubeDeformation(0.0F))
		.texOffs(1656, 1532).addBox(40.0F, -176.0F, -105.0F, 56.0F, 56.0F, 200.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(56.5685F, 94.7523F, 5.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition leg11 = body.addOrReplaceChild("leg11", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-93.0F, 32.7523F, 42.0F));

		leg11.addOrReplaceChild("bone321", CubeListBuilder.create().texOffs(608, 1226).addBox(-50.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 1098).addBox(-50.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg12 = body.addOrReplaceChild("leg12", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(93.0F, 32.7523F, 42.0F));

		leg12.addOrReplaceChild("bone322", CubeListBuilder.create().texOffs(552, 1543).addBox(42.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(3370, 1127).addBox(-38.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg323 = body.addOrReplaceChild("leg323", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-93.0F, 32.7523F, 2.0F));

		leg323.addOrReplaceChild("bone323", CubeListBuilder.create().texOffs(1284, 296).addBox(-50.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 1128).addBox(-50.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg324 = body.addOrReplaceChild("leg324", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(93.0F, 32.7523F, 2.0F));

		leg324.addOrReplaceChild("bone324", CubeListBuilder.create().texOffs(1792, 520).addBox(42.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(2570, 2027).addBox(-38.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg325 = body.addOrReplaceChild("leg325", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-93.0F, 32.7523F, 82.0F));

		leg325.addOrReplaceChild("bone325", CubeListBuilder.create().texOffs(552, 1291).addBox(-50.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1880, 1158).addBox(-50.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg326 = body.addOrReplaceChild("leg326", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(93.0F, 32.7523F, 82.0F));

		leg326.addOrReplaceChild("bone326", CubeListBuilder.create().texOffs(1792, 562).addBox(42.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(3487, 2770).addBox(-38.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg327 = body.addOrReplaceChild("leg327", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-93.0F, 32.7523F, -38.0F));

		leg327.addOrReplaceChild("bone327", CubeListBuilder.create().texOffs(1296, 252).addBox(-50.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1952, 1452).addBox(-50.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg328 = body.addOrReplaceChild("leg328", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(93.0F, 32.7523F, -38.0F));

		leg328.addOrReplaceChild("bone328", CubeListBuilder.create().texOffs(1792, 604).addBox(42.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(2545, 1577).addBox(-38.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		PartDefinition leg329 = body.addOrReplaceChild("leg329", CubeListBuilder.create().texOffs(0, 0).addBox(-60.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-93.0F, 32.7523F, -78.0F));

		leg329.addOrReplaceChild("bone329", CubeListBuilder.create().texOffs(552, 1333).addBox(-50.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(1952, 1482).addBox(-50.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-74.0F, 13.0F, -88.0F));

		PartDefinition leg330 = body.addOrReplaceChild("leg330", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -15.0F, 64.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(93.0F, 32.7523F, -78.0F));

		leg330.addOrReplaceChild("bone330", CubeListBuilder.create().texOffs(1796, 646).addBox(42.0F, 6.0F, 77.0F, 8.0F, 32.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(2820, 1786).addBox(-38.0F, -14.0F, 77.0F, 88.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(74.0F, 13.0F, -88.0F));

		return LayerDefinition.create(meshdefinition, 4096, 4096);
	}

	@Override
	public void setupAnim(ForneusBodyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.body, netHeadYaw, headPitch);
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