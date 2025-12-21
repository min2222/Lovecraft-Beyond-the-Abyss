package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusHead;
import com.min01.beyondtheabyss.entity.model.ModelForneusHead;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusHeadRenderer extends MobRenderer<EntityForneusHead, ModelForneusHead>
{
	public ForneusHeadRenderer(Context pContext)
	{
		super(pContext, new ModelForneusHead(pContext.bakeLayer(ModelForneusHead.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(EntityForneusHead pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(EntityForneusHead pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityForneusHead pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
