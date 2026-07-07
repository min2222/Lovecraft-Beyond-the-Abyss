package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.CorpseAnglerEntity;
import com.min01.beyondtheabyss.entity.model.CorpseAnglerModel;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CorpseAnglerRenderer extends MobRenderer<CorpseAnglerEntity, CorpseAnglerModel>
{
	public CorpseAnglerRenderer(Context pContext)
	{
		super(pContext, new CorpseAnglerModel(pContext.bakeLayer(CorpseAnglerModel.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/corpse_angler_layer.png")));
	}
	
	@Override
	public void render(CorpseAnglerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		pEntity.modelPositions.setModelPos(pEntity, this.model.root());
	}
	
	@Override
	protected void setupRotations(CorpseAnglerEntity pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) 
	{
		super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		if(!pEntityLiving.isInWater()) 
		{
			pPoseStack.translate(1.5F, 0.0F, 0.0F);
			pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(CorpseAnglerEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/corpse_angler.png");
	}
}
