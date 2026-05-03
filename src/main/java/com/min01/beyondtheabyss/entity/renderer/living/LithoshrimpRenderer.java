package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.LithoshrimpEntity;
import com.min01.beyondtheabyss.entity.model.LithoshrimpModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LithoshrimpRenderer extends MobRenderer<LithoshrimpEntity, LithoshrimpModel>
{
	public LithoshrimpRenderer(Context pContext) 
	{
		super(pContext, new LithoshrimpModel(pContext.bakeLayer(LithoshrimpModel.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(LithoshrimpEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/lithoshrimp.png");
	}
}
