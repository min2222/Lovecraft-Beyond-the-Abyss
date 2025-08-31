package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelSplittedFulgastra;
import com.min01.beyondtheabyss.entity.renderer.layer.SplittedFulgastraLayer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SplittedFulgastraRenderer extends MobRenderer<EntitySplittedFulgastra, ModelSplittedFulgastra>
{
	public SplittedFulgastraRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSplittedFulgastra(p_174304_.bakeLayer(ModelSplittedFulgastra.LAYER_LOCATION)), 0.5F);
		this.addLayer(new SplittedFulgastraLayer(this, this.model));
	}
	
	@Override
	protected RenderType getRenderType(EntitySplittedFulgastra p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) 
	{
		return RenderType.entityTranslucent(this.getTextureLocation(p_115322_));
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySplittedFulgastra p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/splitted_fulgastra.png");
	}
}
