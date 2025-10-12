package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBlaster;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentBlasterLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBlasterRenderer extends MobRenderer<EntitySiamserpentHead, ModelSiamserpentBlaster>
{
	private static final ResourceLocation TEXTURE_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster.png");
	private static final ResourceLocation DISABLED_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_disabled.png");
	private static final ResourceLocation DORMANT_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_dormant.png");

	public SiamserpentBlasterRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSiamserpentBlaster(p_174304_.bakeLayer(ModelSiamserpentBlaster.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentBlasterLayer(this, this.model));
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentHead p_114482_) 
	{
		return p_114482_.isDisabled() ? DISABLED_BLASTER : p_114482_.isDormant() ? DORMANT_BLASTER : TEXTURE_BLASTER;
	}
}
