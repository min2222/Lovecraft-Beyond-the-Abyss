package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
import com.min01.beyondtheabyss.entity.model.ModelPhasmozoa;
import com.min01.beyondtheabyss.entity.renderer.layer.PhasmozoaLayer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PhasmozoaRenderer extends MobRenderer<EntityPhasmozoa, ModelPhasmozoa>
{
	public PhasmozoaRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelPhasmozoa(p_174304_.bakeLayer(ModelPhasmozoa.LAYER_LOCATION)), 0.5F);
		this.addLayer(new PhasmozoaLayer(this, this.model));
	}
	
	@Override
	protected RenderType getRenderType(EntityPhasmozoa p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) 
	{
		return RenderType.eyes(this.getTextureLocation(p_115322_));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityPhasmozoa p_115812_) 
	{
		return new ResourceLocation(String.format("%s:textures/entity/phasmozoa%d.png", BeyondtheAbyss.MODID, p_115812_.getVariant()));
	}
}
