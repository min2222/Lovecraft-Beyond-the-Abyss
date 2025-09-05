package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelTwinserpentMiddleBone;

import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class TwinserpentMiddleBoneLayer extends GlowingLayer<EntityTwinserpentBone, ModelTwinserpentMiddleBone>
{
	public TwinserpentMiddleBoneLayer(RenderLayerParent<EntityTwinserpentBone, ModelTwinserpentMiddleBone> p_117346_, ModelTwinserpentMiddleBone model)
	{
		super(p_117346_, model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_middlebone_layer.png"));
	}
}
