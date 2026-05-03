package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
import com.min01.beyondtheabyss.entity.model.GnasherLeaderModel;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class GnasherLeaderLayer extends GlowingLayer<GnasherEntity, GnasherLeaderModel>
{
	public GnasherLeaderLayer(RenderLayerParent<GnasherEntity, GnasherLeaderModel> renderer, GnasherLeaderModel model) 
	{
		super(renderer, model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer_leader.png"));
	}
}
