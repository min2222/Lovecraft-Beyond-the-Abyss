package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.CystShrapnelModel;
import com.min01.beyondtheabyss.entity.model.MutavoreCystModel;
import com.min01.beyondtheabyss.entity.projectile.MutavoreCystEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MutavoreCystRenderer extends EntityRenderer<MutavoreCystEntity>
{
	public final MutavoreCystModel model;
	public final CystShrapnelModel shrapnelModel;
	
	public MutavoreCystRenderer(Context pContext) 
	{
		super(pContext);
		this.model = new MutavoreCystModel(pContext.bakeLayer(MutavoreCystModel.LAYER_LOCATION));
		this.shrapnelModel = new CystShrapnelModel(pContext.bakeLayer(CystShrapnelModel.LAYER_LOCATION));
	}
	
	@Override
	public void render(MutavoreCystEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.isShrapnel())
		{
			pPoseStack.pushPose();
			pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot())));
			pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.scale(1.25F, 1.25F, 1.25F);
			pPoseStack.translate(0.0F, -1.5F, 0.0F);
			this.shrapnelModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/cyst_shrapnel.png"))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			pPoseStack.popPose();
		}
		else
		{
			pPoseStack.pushPose();
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.5F, 0.0F);
			this.model.setupAnim(pEntity, 0, 0, 0, 0, 0);
			this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			pPoseStack.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(MutavoreCystEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
