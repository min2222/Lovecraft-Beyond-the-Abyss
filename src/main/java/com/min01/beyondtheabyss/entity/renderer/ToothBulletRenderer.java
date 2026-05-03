package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.ToothBulletModel;
import com.min01.beyondtheabyss.entity.model.ToothBulletShrapnelModel;
import com.min01.beyondtheabyss.entity.model.ToothBulletShrapnel2Model;
import com.min01.beyondtheabyss.entity.projectile.ToothBulletEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ToothBulletRenderer extends EntityRenderer<ToothBulletEntity>
{
	public final ToothBulletModel model;
	public final ToothBulletShrapnelModel shrapnelModel;
	public final ToothBulletShrapnel2Model shrapnelModel2;
	public ToothBulletRenderer(Context pContext) 
	{
		super(pContext);
		this.model = new ToothBulletModel(pContext.bakeLayer(ToothBulletModel.LAYER_LOCATION));
		this.shrapnelModel = new ToothBulletShrapnelModel(pContext.bakeLayer(ToothBulletShrapnelModel.LAYER_LOCATION));
		this.shrapnelModel2 = new ToothBulletShrapnel2Model(pContext.bakeLayer(ToothBulletShrapnel2Model.LAYER_LOCATION));
	}

	@Override
	public void render(ToothBulletEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.isShrapnel()) 
		{
			pPoseStack.pushPose();
			pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot())));
			pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.5F, 0.0F);
			if(pEntity.getShrapnelType() == 2)
			{
				this.shrapnelModel2.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
			else
			{
				this.shrapnelModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
			pPoseStack.popPose();
		}
		else
		{
			pPoseStack.pushPose();
			pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot())));
			pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.5F, 0.0F);
			this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			pPoseStack.popPose();
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(ToothBulletEntity pEntity)
	{
		if(pEntity.isShrapnel())
		{
			return ResourceLocation.parse(String.format("%s:textures/entity/tooth_bullet_shrapnel%d.png", BeyondtheAbyss.MODID, pEntity.getShrapnelType()));
		}
		else if(pEntity.isGolden())
		{
			return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/golden_tooth_bullet.png");
		}
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/tooth_bullet.png");
	}
}