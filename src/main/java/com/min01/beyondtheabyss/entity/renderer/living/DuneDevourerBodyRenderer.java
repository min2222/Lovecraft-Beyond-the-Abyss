package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerBody;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerBody;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerBodyRenderer extends MobRenderer<EntityDuneDevourerBody, ModelDuneDevourerBody>
{
	public DuneDevourerBodyRenderer(Context pContext)
	{
		super(pContext, new ModelDuneDevourerBody(pContext.bakeLayer(ModelDuneDevourerBody.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntityDuneDevourerBody pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDuneDevourerBody pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
