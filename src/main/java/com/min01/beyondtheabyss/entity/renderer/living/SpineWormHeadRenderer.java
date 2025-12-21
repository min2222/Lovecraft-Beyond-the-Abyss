package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.entity.model.ModelSpineWormHead;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpineWormHeadRenderer extends MobRenderer<EntitySpineWormHead, ModelSpineWormHead>
{
	public SpineWormHeadRenderer(Context pContext)
	{
		super(pContext, new ModelSpineWormHead(pContext.bakeLayer(ModelSpineWormHead.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(EntitySpineWormHead pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySpineWormHead pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/spine_worm_head.png");
	}
}
