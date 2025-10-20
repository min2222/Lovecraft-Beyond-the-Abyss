package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerHead;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerHead;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerHeadRenderer extends MobRenderer<EntityDuneDevourerHead, ModelDuneDevourerHead>
{
	public DuneDevourerHeadRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelDuneDevourerHead(p_174304_.bakeLayer(ModelDuneDevourerHead.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntityDuneDevourerHead p_115337_) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDuneDevourerHead p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
