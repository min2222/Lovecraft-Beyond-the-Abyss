package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentBoneEntity;
import com.min01.beyondtheabyss.entity.model.SiamserpentMiddleBoneModel;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentMiddleBoneLayer extends GlowingLayer<SiamserpentBoneEntity, SiamserpentMiddleBoneModel>
{
	public SiamserpentMiddleBoneLayer(RenderLayerParent<SiamserpentBoneEntity, SiamserpentMiddleBoneModel> renderer, SiamserpentMiddleBoneModel model)
	{
		super(renderer, model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone_layer.png"));
	}
}
