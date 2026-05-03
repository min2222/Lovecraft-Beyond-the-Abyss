package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SubmarineEntity;
import com.min01.beyondtheabyss.entity.model.SubmarineModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SubmarineLayer extends GlowingLayer<SubmarineEntity, SubmarineModel>
{
	public SubmarineLayer(RenderLayerParent<SubmarineEntity, SubmarineModel> renderer, SubmarineModel model) 
	{
		super(renderer, model, null);
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, SubmarineEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		if(entity.getFirstPassenger() != null)
		{
			ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/submarine_layer.png");
	        float strength = 0.5F + Mth.clamp(((float) Math.cos((entity.glowingTicks + ageInTicks) * 0.1F)) - 0.5F, -0.5F, 0.5F);
	        strength += Mth.lerp(ageInTicks, entity.brightnessOld, entity.brightness) * Mth.PI;
	        strength = Mth.clamp(strength, 0.1F, 1.0F);
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, strength, strength, strength);
		}
	}
}
