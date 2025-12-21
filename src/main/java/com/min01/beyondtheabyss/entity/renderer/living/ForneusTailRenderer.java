package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusTail;
import com.min01.beyondtheabyss.entity.model.ModelForneusTail;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusTailRenderer extends MobRenderer<EntityForneusTail, ModelForneusTail>
{
	public ForneusTailRenderer(Context pContext)
	{
		super(pContext, new ModelForneusTail(pContext.bakeLayer(ModelForneusTail.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(EntityForneusTail pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(EntityForneusTail pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityForneusTail pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
