package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosTail;
import com.min01.beyondtheabyss.entity.model.ModelKormosTail;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KormosTailRenderer extends MobRenderer<EntityKormosTail, ModelKormosTail>
{
	public KormosTailRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelKormosTail(p_174304_.bakeLayer(ModelKormosTail.LAYER_LOCATION)), 0.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityKormosTail p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/kormos.png");
	}
}
