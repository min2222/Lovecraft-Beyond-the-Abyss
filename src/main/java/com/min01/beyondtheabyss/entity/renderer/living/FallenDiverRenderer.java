package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.entity.deepabyss.EntityFallenDiver;
import com.min01.beyondtheabyss.entity.model.ModelFallenDiver;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FallenDiverRenderer extends MobRenderer<EntityFallenDiver, ModelFallenDiver>
{
	private static final ResourceLocation DROWNED_LOCATION = new ResourceLocation("textures/entity/zombie/drowned.png");
	   
	public FallenDiverRenderer(Context p_174169_)
	{
		super(p_174169_, new ModelFallenDiver(p_174169_.bakeLayer(ModelFallenDiver.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityFallenDiver p_114891_)
	{
		return DROWNED_LOCATION;
	}
}
