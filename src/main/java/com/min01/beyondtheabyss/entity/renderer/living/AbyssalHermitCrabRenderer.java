package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.model.ModelAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AbyssalHermitCrabRenderer extends MobRenderer<EntityAbyssalHermitCrab, ModelAbyssalHermitCrab>
{
	public AbyssalHermitCrabRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelAbyssalHermitCrab(p_174304_.bakeLayer(ModelAbyssalHermitCrab.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/abyssal_hermit_crab_layer.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityAbyssalHermitCrab p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/abyssal_hermit_crab.png");
	}
}
