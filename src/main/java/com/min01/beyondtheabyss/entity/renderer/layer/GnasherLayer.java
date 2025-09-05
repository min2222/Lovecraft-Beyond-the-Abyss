package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class GnasherLayer extends GlowingLayer<EntityGnasher, ModelGnasher>
{
	public GnasherLayer(RenderLayerParent<EntityGnasher, ModelGnasher> renderer, ModelGnasher model) 
	{
		super(renderer, model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer.png"));
	}
}
