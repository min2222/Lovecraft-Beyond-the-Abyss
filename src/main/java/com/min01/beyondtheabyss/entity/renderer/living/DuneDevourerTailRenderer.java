package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerTail;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerTail;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerTailRenderer extends MobRenderer<EntityDuneDevourerTail, ModelDuneDevourerTail>
{
	public DuneDevourerTailRenderer(Context pContext)
	{
		super(pContext, new ModelDuneDevourerTail(pContext.bakeLayer(ModelDuneDevourerTail.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntityDuneDevourerTail pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDuneDevourerTail pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
