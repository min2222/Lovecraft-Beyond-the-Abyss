package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GhidruthRenderer extends MobRenderer<EntityGhidruth, ModelGhidruth>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth.png");
	public static final ResourceLocation LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_layer.png");
	public static final ResourceLocation DASH_LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_dash_eye_layer.png");
	
	public GhidruthRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelGhidruth(p_174304_.bakeLayer(ModelGhidruth.LAYER_LOCATION)), 0);
		this.addLayer(new GlowingLayer<>(this, this.model, LAYER));
		this.addLayer(new GlowingLayer<>(this, this.model, DASH_LAYER));
	}
	
	@Override
	protected void scale(EntityGhidruth p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
		p_115315_.translate(0, -0.1F, 0);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGhidruth p_115812_)
	{
		return TEXTURE;
	}
}
