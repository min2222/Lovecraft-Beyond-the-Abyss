package com.min01.beyondtheabyss.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class GlowingLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    private final ResourceLocation texture;

    public GlowingLayer(RenderLayerParent<T, M> renderer, ResourceLocation texture) 
    {
        super(renderer);
        this.texture = texture;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) 
    {
        VertexConsumer VertexConsumer = bufferIn.getBuffer(RenderType.eyes(this.texture));
        this.getParentModel().renderToBuffer(matrixStackIn, VertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
