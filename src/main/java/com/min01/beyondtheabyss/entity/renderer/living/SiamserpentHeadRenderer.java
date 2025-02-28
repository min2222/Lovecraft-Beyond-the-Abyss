package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentHead;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentHeadLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentHeadRenderer extends MobRenderer<EntitySiamserpentHead, ModelSiamserpentHead>
{
	private static final ResourceLocation TEXTURE_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher.png");
	private static final ResourceLocation TEXTURE_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster.png");
	private static final ResourceLocation DISABLED_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_disabled.png");
	private static final ResourceLocation DISABLED_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_disabled.png");
	private static final ResourceLocation DORMANT_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_dormant.png");
	private static final ResourceLocation DORMANT_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_dormant.png");

	public SiamserpentHeadRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSiamserpentHead(p_174304_.bakeLayer(ModelSiamserpentHead.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentHeadLayer(this, this.model));
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentHead p_114482_) 
	{
		switch(p_114482_.getHeadType())
		{
		case SLASHER:
			return p_114482_.isDisabled() ? DISABLED_SLASHER : p_114482_.isDormant() ? DORMANT_SLASHER : TEXTURE_SLASHER;
		case BLASTER:
			return p_114482_.isDisabled() ? DISABLED_BLASTER : p_114482_.isDormant() ? DORMANT_BLASTER : TEXTURE_BLASTER;
		}
		return TEXTURE_SLASHER;
	}
}
