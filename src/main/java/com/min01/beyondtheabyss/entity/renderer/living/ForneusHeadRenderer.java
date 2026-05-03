package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusHeadEntity;
import com.min01.beyondtheabyss.entity.model.ForneusHeadModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusHeadRenderer extends MobRenderer<ForneusHeadEntity, ForneusHeadModel>
{
	public ForneusHeadRenderer(Context pContext)
	{
		super(pContext, new ForneusHeadModel(pContext.bakeLayer(ForneusHeadModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(ForneusHeadEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(ForneusHeadEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(ForneusHeadEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
