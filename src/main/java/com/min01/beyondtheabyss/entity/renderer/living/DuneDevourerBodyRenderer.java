package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerBodyEntity;
import com.min01.beyondtheabyss.entity.model.DuneDevourerBodyModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerBodyRenderer extends MobRenderer<DuneDevourerBodyEntity, DuneDevourerBodyModel>
{
	public DuneDevourerBodyRenderer(Context pContext)
	{
		super(pContext, new DuneDevourerBodyModel(pContext.bakeLayer(DuneDevourerBodyModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(DuneDevourerBodyEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(DuneDevourerBodyEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
