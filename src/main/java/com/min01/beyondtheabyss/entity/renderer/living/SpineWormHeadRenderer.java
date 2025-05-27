package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormHead;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpineWormHeadRenderer extends MobRenderer<EntitySpineWormHead, ModelSpineWormHead>
{
	public SpineWormHeadRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSpineWormHead(p_174304_.bakeLayer(ModelSpineWormHead.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntitySpineWormHead p_115337_) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySpineWormHead p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/spine_worm_head.png");
	}
}
