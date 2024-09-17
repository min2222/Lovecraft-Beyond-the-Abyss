package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.model.ModelRunicFish;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class RunicFishLayer extends GlowingLayer<EntityRunicFish, ModelRunicFish>
{
	public RunicFishLayer(RenderLayerParent<EntityRunicFish, ModelRunicFish> p_117346_, ModelRunicFish model)
	{
		super(p_117346_, model, null);
	}
    
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityRunicFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_) 
	{
    	ResourceLocation texture = new ResourceLocation(String.format("%s:textures/entity/runic_fish%d_layer.png", BeyondtheAbyss.MODID, entity.getVariant()));
		this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, 1.0F, 1.0F, 1.0F);
	}
}
