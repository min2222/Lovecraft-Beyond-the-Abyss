package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalBulbray;
import com.min01.beyondtheabyss.entity.model.ModelAbyssalBulbray;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AbyssalBulbrayRenderer extends MobRenderer<EntityAbyssalBulbray, ModelAbyssalBulbray>
{
	public AbyssalBulbrayRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelAbyssalBulbray(p_174304_.bakeLayer(ModelAbyssalBulbray.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/abyssal_bulbray_layer.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityAbyssalBulbray p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/abyssal_bulbray.png");
	}
}
