package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.model.SiamserpentBlasterModel;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentBlasterLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBlasterRenderer extends MobRenderer<SiamserpentHeadEntity, SiamserpentBlasterModel>
{
	private static final ResourceLocation TEXTURE_BLASTER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster.png");
	private static final ResourceLocation DISABLED_BLASTER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_disabled.png");
	private static final ResourceLocation DORMANT_BLASTER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_dormant.png");

	public SiamserpentBlasterRenderer(Context pContext)
	{
		super(pContext, new SiamserpentBlasterModel(pContext.bakeLayer(SiamserpentBlasterModel.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentBlasterLayer(this, this.model));
	}
	
	@Override
	public ResourceLocation getTextureLocation(SiamserpentHeadEntity pEntity) 
	{
		return pEntity.isDisabled() ? DISABLED_BLASTER : pEntity.isDormant() ? DORMANT_BLASTER : TEXTURE_BLASTER;
	}
}
