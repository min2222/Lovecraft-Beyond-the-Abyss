package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGloomfish;
import com.min01.beyondtheabyss.entity.model.ModelGloomfish;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GloomfishRenderer extends MobRenderer<EntityGloomfish, ModelGloomfish>
{
	public GloomfishRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelGloomfish(p_174304_.bakeLayer(ModelGloomfish.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gloomfish_layer.png")));
	}
	
	@Override
	protected void setupRotations(EntityGloomfish p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if(!p_116226_.isInWater()) 
		{
			p_116227_.translate(0.5F, 0, 0);
			p_116227_.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityGloomfish p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gloomfish.png");
	}
}
