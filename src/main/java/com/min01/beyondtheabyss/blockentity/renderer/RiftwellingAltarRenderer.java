package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.model.RiftwellingAltarModel;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

public class RiftwellingAltarRenderer implements BlockEntityRenderer<RiftwellingAltarBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/riftwelling_altar.png");
    private static final ResourceLocation LAYER_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/riftwelling_altar_layer.png");
    
	private final RiftwellingAltarModel model;
	
	public RiftwellingAltarRenderer(BlockEntityRendererProvider.Context pContext)
	{
		this.model = new RiftwellingAltarModel(pContext.bakeLayer(RiftwellingAltarModel.LAYER_LOCATION));
	}

	@Override
	public void render(RiftwellingAltarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		pPoseStack.pushPose();
		pPoseStack.translate(0.5F, 0.5F, 0.5F);
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.0F, 0.0F);
		VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
		this.model.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(0.5F, 0.5F, 0.5F);
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.0F, 0.0F);
		VertexConsumer eyeConsumer = pBuffer.getBuffer(RenderType.eyes(LAYER_TEXTURE));
		this.model.renderToBuffer(pPoseStack, eyeConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pPoseStack.popPose();
		
		if(!pBlockEntity.getItem().isEmpty())
		{
			float tick = BTAClientUtil.MC.player.tickCount + pPartialTick;
			pPoseStack.pushPose();
			pPoseStack.scale(0.5F, 0.5F, 0.5F);
			pPoseStack.translate(1.0F, 3.0F, 1.0F);
			pPoseStack.translate(0, 0.05F * Mth.sin(tick / 15), 0);
			pPoseStack.mulPose(Axis.YP.rotationDegrees(tick));
			BTAClientUtil.MC.getItemRenderer().renderStatic(pBlockEntity.getItem(), ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
			pPoseStack.popPose();
		}
	}
}
