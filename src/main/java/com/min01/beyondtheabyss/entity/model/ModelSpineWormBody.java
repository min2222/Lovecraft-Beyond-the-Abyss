package com.min01.beyondtheabyss.entity.model;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormBody;
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
import net.minecraft.world.phys.Vec3;

public class ModelSpineWormBody extends HierarchicalModel<EntitySpineWormBody>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondtheAbyss.MODID, "spine_worm_body"), "main");
	private final ModelPart root;

	public ModelSpineWormBody(ModelPart root)
	{
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("segment", CubeListBuilder.create().texOffs(64, 0).addBox(-7.0F, 2.0F, -5.0F, 14.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-9.5F, -4.0F, -6.5F, 19.0F, 6.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).addBox(-9.5F, -4.0F, -6.5F, 19.0F, 6.0F, 13.0F, new CubeDeformation(0.25F))
		.texOffs(64, 27).addBox(-1.0F, -3.0F, 6.5F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(64, 21).addBox(-21.5F, -4.0F, 3.5F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(64, 21).mirror().addBox(9.5F, -4.0F, 3.5F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).addBox(-8.0F, 4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 58).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(64, 12).addBox(-6.5F, -5.0F, -4.0F, 13.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntitySpineWormBody entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) 
	{
		this.root().getAllParts().forEach(ModelPart::resetPose);
		BTAClientUtil.animateHead(this.root.getChild("segment"), netHeadYaw, headPitch + 90.0F);
		
		if(entity.getHead() != null)
		{
	        Vec3 toTarget = entity.getHead().position().subtract(entity.position());
	        double dist = toTarget.length();
	        double moveDist = Math.min(dist, 0.5F);
			this.root.visible = moveDist > 0.0F;
			if(entity.getHead().isVehicle())
			{
				if(entity.getIndex() == entity.getChainLength())
				{
					this.root.visible = false;
				}
			}
		}
		else
		{
			this.root.visible = false;
		}
	}
	
	@Override
	public ModelPart root() 
	{
		return this.root;
	}
	
	public void setAttributes(float rotX, float rotY)
	{
		this.root.xRot = (float)Math.toRadians(rotX);
		this.root.yRot = (float)Math.toRadians(rotY);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) 
	{
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}