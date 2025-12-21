package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerHead;
import com.min01.beyondtheabyss.entity.model.ModelDuneDevourerHead;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerHeadRenderer extends MobRenderer<EntityDuneDevourerHead, ModelDuneDevourerHead>
{
	public DuneDevourerHeadRenderer(Context pContext)
	{
		super(pContext, new ModelDuneDevourerHead(pContext.bakeLayer(ModelDuneDevourerHead.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntityDuneDevourerHead pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDuneDevourerHead pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
