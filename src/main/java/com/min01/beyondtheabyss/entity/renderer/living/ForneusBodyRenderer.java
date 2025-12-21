package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusBody;
import com.min01.beyondtheabyss.entity.model.ModelForneusBody;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusBodyRenderer extends MobRenderer<EntityForneusBody, ModelForneusBody>
{
	public ForneusBodyRenderer(Context pContext)
	{
		super(pContext, new ModelForneusBody(pContext.bakeLayer(ModelForneusBody.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(EntityForneusBody pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(EntityForneusBody pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityForneusBody pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
