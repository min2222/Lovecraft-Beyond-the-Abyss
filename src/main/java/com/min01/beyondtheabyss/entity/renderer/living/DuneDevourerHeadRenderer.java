package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerHeadEntity;
import com.min01.beyondtheabyss.entity.model.DuneDevourerHeadModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerHeadRenderer extends MobRenderer<DuneDevourerHeadEntity, DuneDevourerHeadModel>
{
	public DuneDevourerHeadRenderer(Context pContext)
	{
		super(pContext, new DuneDevourerHeadModel(pContext.bakeLayer(DuneDevourerHeadModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(DuneDevourerHeadEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(DuneDevourerHeadEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
