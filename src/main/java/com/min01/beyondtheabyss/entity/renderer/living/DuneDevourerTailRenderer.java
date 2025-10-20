package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerTail;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerTail;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerTailRenderer extends MobRenderer<EntityDuneDevourerTail, ModelDuneDevourerTail>
{
	public DuneDevourerTailRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelDuneDevourerTail(p_174304_.bakeLayer(ModelDuneDevourerTail.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntityDuneDevourerTail p_115337_) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDuneDevourerTail p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
