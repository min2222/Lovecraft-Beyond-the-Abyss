package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.entity.model.ModelDeepVampire;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DeepVampireRenderer extends MobRenderer<EntityDeepVampire, ModelDeepVampire>
{
	public DeepVampireRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelDeepVampire(p_174304_.bakeLayer(ModelDeepVampire.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/deep_vampire_layer.png")));
	}
	
	@Override
	protected void setupRotations(EntityDeepVampire p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if (!p_116226_.isInWater()) 
		{
			p_116227_.translate(0.5F, 0, 0);
			p_116227_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDeepVampire p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/deep_vampire.png");
	}
}
