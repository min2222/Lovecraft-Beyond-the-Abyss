package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLithoshrimp;
import com.min01.beyondtheabyss.entity.model.ModelLithoshrimp;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LithoshrimpRenderer extends MobRenderer<EntityLithoshrimp, ModelLithoshrimp>
{
	public LithoshrimpRenderer(Context pContext) 
	{
		super(pContext, new ModelLithoshrimp(pContext.bakeLayer(ModelLithoshrimp.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityLithoshrimp pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/lithoshrimp.png");
	}
}
