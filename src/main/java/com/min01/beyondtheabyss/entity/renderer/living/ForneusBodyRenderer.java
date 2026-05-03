package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusBodyEntity;
import com.min01.beyondtheabyss.entity.model.ForneusBodyModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusBodyRenderer extends MobRenderer<ForneusBodyEntity, ForneusBodyModel>
{
	public ForneusBodyRenderer(Context pContext)
	{
		super(pContext, new ForneusBodyModel(pContext.bakeLayer(ForneusBodyModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(ForneusBodyEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(ForneusBodyEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(ForneusBodyEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
