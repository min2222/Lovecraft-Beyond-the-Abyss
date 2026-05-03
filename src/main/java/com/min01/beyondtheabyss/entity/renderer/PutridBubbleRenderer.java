package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.PutridBubbleModel;
import com.min01.beyondtheabyss.entity.projectile.PutridBubbleEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PutridBubbleRenderer extends EntityRenderer<PutridBubbleEntity>
{
	public final PutridBubbleModel model;
	public PutridBubbleRenderer(Context pContext) 
	{
		super(pContext);
		this.model = new PutridBubbleModel(pContext.bakeLayer(PutridBubbleModel.LAYER_LOCATION));
	}

	@Override
	public void render(PutridBubbleEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		float tick = pEntity.explosionTick * 0.08F;
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.scale(1.0F + tick, 1.0F + tick, 1.0F + tick);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, Math.max(1.0F - tick, 0.0F));
		pPoseStack.popPose();
	}
	
	@Override
	public ResourceLocation getTextureLocation(PutridBubbleEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/putrid_bubble.png");
	}
}