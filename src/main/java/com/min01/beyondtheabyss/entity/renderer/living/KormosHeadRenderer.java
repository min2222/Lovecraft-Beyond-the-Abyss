package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosHead;
import com.min01.beyondtheabyss.entity.model.ModelKormosHead;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KormosHeadRenderer extends MobRenderer<EntityKormosHead, ModelKormosHead>
{
	public KormosHeadRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelKormosHead(p_174304_.bakeLayer(ModelKormosHead.LAYER_LOCATION)), 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityKormosHead p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/kormos.png");
	}
}
