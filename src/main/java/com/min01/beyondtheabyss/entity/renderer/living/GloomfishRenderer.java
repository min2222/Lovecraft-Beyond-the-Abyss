package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.GloomfishEntity;
import com.min01.beyondtheabyss.entity.model.GloomfishModel;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GloomfishRenderer extends MobRenderer<GloomfishEntity, GloomfishModel>
{
	public GloomfishRenderer(Context pContext)
	{
		super(pContext, new GloomfishModel(pContext.bakeLayer(GloomfishModel.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gloomfish_layer.png")));
	}
	
	@Override
	protected void setupRotations(GloomfishEntity pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) 
	{
		super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		if(!pEntityLiving.isInWater()) 
		{
			pPoseStack.translate(0.25F, 0.0F, 0.0F);
 			pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(GloomfishEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gloomfish.png");
	}
}
