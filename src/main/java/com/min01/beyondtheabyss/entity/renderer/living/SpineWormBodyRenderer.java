package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SpineWormBodyEntity;
import com.min01.beyondtheabyss.entity.model.SpineWormBodyModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpineWormBodyRenderer extends MobRenderer<SpineWormBodyEntity, SpineWormBodyModel>
{
	public SpineWormBodyRenderer(Context pContext)
	{
		super(pContext, new SpineWormBodyModel(pContext.bakeLayer(SpineWormBodyModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(SpineWormBodyEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(SpineWormBodyEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/spine_worm_body.png");
	}
}
