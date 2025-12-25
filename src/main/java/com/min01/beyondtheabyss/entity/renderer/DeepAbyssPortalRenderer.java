package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IMultiModel;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.entity.model.ModelDeepAbyssPortal;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DeepAbyssPortalRenderer extends EntityRenderer<EntityDeepAbyssPortal> implements IMultiModel<EntityDeepAbyssPortal>
{
	private final ModelDeepAbyssPortal model;
	
	public DeepAbyssPortalRenderer(Context pContext)
	{
		super(pContext);
		this.model = new ModelDeepAbyssPortal(pContext.bakeLayer(ModelDeepAbyssPortal.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityDeepAbyssPortal pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, 0, 0);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pPoseStack.popPose();
		
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, 0, 0);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(BTARenderType.eyesFix(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/deep_abyss_portal_layer.png"))), pPackedLight, OverlayTexture.NO_OVERLAY, 0.8F, 0.8F, 0.8F, 1.0F);
		pPoseStack.popPose();
	}
	
	@Override
	public HierarchicalModel<EntityDeepAbyssPortal> getModel(EntityDeepAbyssPortal entity)
	{
		return this.model;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDeepAbyssPortal pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/deep_abyss_portal.png");
	}
}
