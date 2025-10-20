package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerBody;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerBody;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerBodyRenderer extends MobRenderer<EntityDuneDevourerBody, ModelDuneDevourerBody>
{
	public DuneDevourerBodyRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelDuneDevourerBody(p_174304_.bakeLayer(ModelDuneDevourerBody.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntityDuneDevourerBody p_115337_) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDuneDevourerBody p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
