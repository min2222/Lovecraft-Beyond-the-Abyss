package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelFulgastra;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FulgastraRenderer extends MobRenderer<EntityFulgastra, ModelFulgastra>
{
	public FulgastraRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelFulgastra(p_174304_.bakeLayer(ModelFulgastra.LAYER_LOCATION)), 0.5F);
		//TODO charged layer;
	}
	
	@Override
	protected RenderType getRenderType(EntityFulgastra p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) 
	{
		return RenderType.entityTranslucent(this.getTextureLocation(p_115322_));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityFulgastra p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/fulgastra.png");
	}
}
