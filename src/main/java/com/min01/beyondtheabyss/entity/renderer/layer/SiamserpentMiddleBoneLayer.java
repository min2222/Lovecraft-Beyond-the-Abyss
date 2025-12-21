package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentMiddleBone;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentMiddleBoneLayer extends GlowingLayer<EntitySiamserpentBone, ModelSiamserpentMiddleBone>
{
	public SiamserpentMiddleBoneLayer(RenderLayerParent<EntitySiamserpentBone, ModelSiamserpentMiddleBone> renderer, ModelSiamserpentMiddleBone model)
	{
		super(renderer, model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone_layer.png"));
	}
}
