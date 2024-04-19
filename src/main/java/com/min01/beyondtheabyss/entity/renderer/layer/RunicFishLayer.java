package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.model.ModelRunicFish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RunicFishLayer extends RenderLayer<EntityRunicFish, ModelRunicFish>
{
	public RunicFishLayer(RenderLayerParent<EntityRunicFish, ModelRunicFish> p_117346_)
	{
		super(p_117346_);
	}

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityRunicFish entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) 
    {
    	ResourceLocation texture = new ResourceLocation(String.format("%s:textures/entity/runic_fish%d_layer.png", BeyondtheAbyss.MODID, entitylivingbaseIn.getVariant()));
        VertexConsumer VertexConsumer = bufferIn.getBuffer(RenderType.eyes(texture));
        this.getParentModel().renderToBuffer(matrixStackIn, VertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
