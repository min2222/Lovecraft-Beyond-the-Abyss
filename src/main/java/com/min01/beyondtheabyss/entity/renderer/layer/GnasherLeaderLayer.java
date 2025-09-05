package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasherLeader;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class GnasherLeaderLayer extends GlowingLayer<EntityGnasher, ModelGnasherLeader>
{
	public GnasherLeaderLayer(RenderLayerParent<EntityGnasher, ModelGnasherLeader> renderer, ModelGnasherLeader model) 
	{
		super(renderer, model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer_leader.png"));
	}
}
