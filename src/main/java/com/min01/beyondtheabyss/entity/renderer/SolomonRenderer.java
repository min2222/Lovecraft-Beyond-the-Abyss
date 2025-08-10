package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.EntitySolomon;
import com.min01.beyondtheabyss.entity.model.ModelSolomon;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SolomonRenderer extends MobRenderer<EntitySolomon, ModelSolomon>
{
	public SolomonRenderer(Context p_174304_) 
	{
		super(p_174304_, new ModelSolomon(p_174304_.bakeLayer(ModelSolomon.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySolomon p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/solomon.png");
	}
}
