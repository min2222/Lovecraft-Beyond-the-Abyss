package com.min01.beyondtheabyss.entity.renderer.living;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.MysteriousGuyEntity;
import com.min01.beyondtheabyss.entity.model.MysteriousGuyModel;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MysteriousGuyRenderer extends MobRenderer<MysteriousGuyEntity, MysteriousGuyModel>
{
	public static final ResourceLocation MARK_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/misc/exclamation_mark.png"); 
	public MysteriousGuyRenderer(Context pContext) 
	{
		super(pContext, new MysteriousGuyModel(pContext.bakeLayer(MysteriousGuyModel.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(MysteriousGuyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		if(pEntity.canTalk() && !pEntity.isTalking())
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0, 2.5F, 0);
			pPoseStack.mulPose(BTAClientUtil.MC.gameRenderer.getMainCamera().rotation());
			BTAClientUtil.drawQuad(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(MARK_TEXTURE)), new Vector2f(0.5F, 0.5F), new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), LightTexture.FULL_BRIGHT);
			pPoseStack.popPose();
		}
		
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}

	@Override
	public ResourceLocation getTextureLocation(MysteriousGuyEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/mysterious_guy.png");
	}
}
