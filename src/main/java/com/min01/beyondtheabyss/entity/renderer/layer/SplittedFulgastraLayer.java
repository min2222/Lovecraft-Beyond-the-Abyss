package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelSplittedFulgastra;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SplittedFulgastraLayer extends GlowingLayer<EntitySplittedFulgastra, ModelSplittedFulgastra>
{
	private static final ResourceLocation LAYER_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/splitted_fulgastra_layer.png");
	private static final ResourceLocation LAYER_TEXTURE_CHARGED = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/charged_splitted_fulgastra_layer.png");
	
	public SplittedFulgastraLayer(RenderLayerParent<EntitySplittedFulgastra, ModelSplittedFulgastra> renderer, ModelSplittedFulgastra model) 
	{
		super(renderer, model, null);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, EntitySplittedFulgastra entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
    	ResourceLocation texture = entity.isCharged() ? LAYER_TEXTURE_CHARGED : LAYER_TEXTURE;
		this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, 1.0F, 1.0F, 1.0F);
	}
}
