package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.ModelMissile;
import com.min01.beyondtheabyss.entity.projectile.EntityMissile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MissileRenderer extends EntityRenderer<EntityMissile>
{
	public final ModelMissile model;
	
	public MissileRenderer(Context pContext) 
	{
		super(pContext);
		this.model = new ModelMissile(pContext.bakeLayer(ModelMissile.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityMissile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		pPoseStack.pushPose();
		pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) + 180.0F));
		pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pPoseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMissile pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/missile.png");
	}
}
