package com.min01.beyondtheabyss.entity.render;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GhidruthRenderer extends MobRenderer<EntityGhidruth, ModelGhidruth>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth.png");
	
	public GhidruthRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelGhidruth(p_174304_.bakeLayer(ModelGhidruth.LAYER_LOCATION)), 0);
	}
	
	@Override
	protected void scale(EntityGhidruth p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGhidruth p_115812_)
	{
		return TEXTURE;
	}
}
