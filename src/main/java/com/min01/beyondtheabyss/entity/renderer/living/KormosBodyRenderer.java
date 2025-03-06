package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosBody;
import com.min01.beyondtheabyss.entity.model.ModelKormosBody;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KormosBodyRenderer extends MobRenderer<EntityKormosBody, ModelKormosBody>
{
	public KormosBodyRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelKormosBody(p_174304_.bakeLayer(ModelKormosBody.LAYER_LOCATION)), 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityKormosBody p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/kormos.png");
	}
}
