package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.GhidruthEntity;
import com.min01.beyondtheabyss.entity.model.GhidruthModel;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GhidruthRenderer extends MobRenderer<GhidruthEntity, GhidruthModel>
{
	public GhidruthRenderer(Context pContext)
	{
		super(pContext, new GhidruthModel(pContext.bakeLayer(GhidruthModel.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/ghidruth_layer.png")));
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/ghidruth_dash_eye_layer.png")));
	}
	
	@Override
	protected void scale(GhidruthEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	public void render(GhidruthEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		pEntity.modelPositions.setModelPos(pEntity, this.model.root());
	}

	@Override
	public ResourceLocation getTextureLocation(GhidruthEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/ghidruth.png");
	}
}
