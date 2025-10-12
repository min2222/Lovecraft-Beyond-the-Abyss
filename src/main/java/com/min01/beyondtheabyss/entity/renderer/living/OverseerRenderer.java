package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityOverseer;
import com.min01.beyondtheabyss.entity.model.ModelOverseer;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OverseerRenderer extends MobRenderer<EntityOverseer, ModelOverseer>
{
	public OverseerRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelOverseer(p_174304_.bakeLayer(ModelOverseer.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/overseer_layer.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityOverseer p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/overseer.png");
	}
}
