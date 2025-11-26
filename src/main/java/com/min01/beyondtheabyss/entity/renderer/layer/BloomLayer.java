package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.misc.BTARenderType;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BloomLayer<T extends Entity, M extends EntityModel<T>> extends GlowingLayer<T, M>
{
	public BloomLayer(RenderLayerParent<T, M> renderer, M model, ResourceLocation texture) 
	{
		super(renderer, model, texture);
	}

	@Override
	public RenderType getRenderType(ResourceLocation texture) 
	{
		return BTARenderType.bloom(texture);
	}
}
