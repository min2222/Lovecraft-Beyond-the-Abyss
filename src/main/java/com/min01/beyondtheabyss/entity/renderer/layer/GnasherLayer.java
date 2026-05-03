package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
import com.min01.beyondtheabyss.entity.model.GnasherModel;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class GnasherLayer extends GlowingLayer<GnasherEntity, GnasherModel>
{
	public GnasherLayer(RenderLayerParent<GnasherEntity, GnasherModel> renderer, GnasherModel model) 
	{
		super(renderer, model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer.png"));
	}
}
