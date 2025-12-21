package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityOverseer;
import com.min01.beyondtheabyss.entity.model.ModelOverseer;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OverseerRenderer extends MobRenderer<EntityOverseer, ModelOverseer>
{
	public OverseerRenderer(Context pContext)
	{
		super(pContext, new ModelOverseer(pContext.bakeLayer(ModelOverseer.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/overseer_layer.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityOverseer pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/overseer.png");
	}
}
