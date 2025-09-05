package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead;
import com.min01.beyondtheabyss.entity.model.ModelTwinserpentBlaster;
import com.min01.beyondtheabyss.entity.renderer.layer.TwinserpentBlasterLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TwinserpentBlasterRenderer extends MobRenderer<EntityTwinserpentHead, ModelTwinserpentBlaster>
{
	private static final ResourceLocation TEXTURE_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_blaster.png");
	private static final ResourceLocation DISABLED_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_blaster_disabled.png");
	private static final ResourceLocation DORMANT_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_blaster_dormant.png");

	public TwinserpentBlasterRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelTwinserpentBlaster(p_174304_.bakeLayer(ModelTwinserpentBlaster.LAYER_LOCATION)), 0.0F);
		this.addLayer(new TwinserpentBlasterLayer(this, this.model));
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityTwinserpentHead p_114482_) 
	{
		return p_114482_.isDisabled() ? DISABLED_BLASTER : p_114482_.isDormant() ? DORMANT_BLASTER : TEXTURE_BLASTER;
	}
}
