package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.entity.model.ModelLatcher;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LatcherRenderer extends MobRenderer<EntityLatcher, ModelLatcher>
{
	public LatcherRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelLatcher(p_174304_.bakeLayer(ModelLatcher.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityLatcher p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/latcher.png");
	}
}
