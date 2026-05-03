package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerTailEntity;
import com.min01.beyondtheabyss.entity.model.DuneDevourerTailModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerTailRenderer extends MobRenderer<DuneDevourerTailEntity, DuneDevourerTailModel>
{
	public DuneDevourerTailRenderer(Context pContext)
	{
		super(pContext, new DuneDevourerTailModel(pContext.bakeLayer(DuneDevourerTailModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(DuneDevourerTailEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(DuneDevourerTailEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
