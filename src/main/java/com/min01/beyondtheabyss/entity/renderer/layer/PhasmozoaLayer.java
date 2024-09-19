package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
import com.min01.beyondtheabyss.entity.model.ModelPhasmozoa;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class PhasmozoaLayer extends GlowingLayer<EntityPhasmozoa, ModelPhasmozoa>
{
	public PhasmozoaLayer(RenderLayerParent<EntityPhasmozoa, ModelPhasmozoa> renderer, ModelPhasmozoa model) 
	{
		super(renderer, model, null);
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityPhasmozoa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_) 
	{
    	ResourceLocation texture = new ResourceLocation(String.format("%s:textures/entity/phasmozoa%d_layer.png", BeyondtheAbyss.MODID, entity.getVariant()));
		this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public RenderType getRenderType(ResourceLocation texture) 
	{
		return RenderType.entityTranslucent(texture);
	}
	
	@Override
	public float getAlpha(EntityPhasmozoa entity) 
	{
		return entity.getSpectreAlpha();
	}
}
