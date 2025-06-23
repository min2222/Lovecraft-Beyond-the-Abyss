package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SubmarineLayer extends GlowingLayer<EntitySubmarine, ModelSubmarine>
{
	public SubmarineLayer(RenderLayerParent<EntitySubmarine, ModelSubmarine> renderer, ModelSubmarine model) 
	{
		super(renderer, model, null);
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntitySubmarine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_) 
	{
		if(entity.getFirstPassenger() != null)
		{
			ResourceLocation texture = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine_layer.png");
	        float strength = 0.5F + Mth.clamp(((float) Math.cos((entity.glowingTicks + ageInTicks) * 0.1F)) - 0.5F, -0.5F, 0.5F);
	        strength += Mth.lerp(ageInTicks, entity.brightnessOld, entity.brightness) * Mth.PI;
	        strength = Mth.clamp(strength, 0.1F, 1.0F);
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, strength, strength, strength);
		}
	}
}
