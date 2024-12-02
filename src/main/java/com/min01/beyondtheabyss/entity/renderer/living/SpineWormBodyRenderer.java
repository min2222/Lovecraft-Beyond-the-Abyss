package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormBody;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormBody;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpineWormBodyRenderer extends MobRenderer<EntitySpineWormBody, ModelSpineWormBody>
{
	public SpineWormBodyRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSpineWormBody(p_174304_.bakeLayer(ModelSpineWormBody.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntitySpineWormBody p_115337_) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySpineWormBody p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/spine_worm.png");
	}
}
