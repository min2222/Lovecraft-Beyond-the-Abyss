package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.EntityMysteriousGuy;
import com.min01.beyondtheabyss.entity.model.ModelMysteriousGuy;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MysteriousGuyRenderer extends MobRenderer<EntityMysteriousGuy, ModelMysteriousGuy>
{
	public static final ResourceLocation MARK_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/misc/exclamation_mark.png"); 
	public MysteriousGuyRenderer(Context pContext) 
	{
		super(pContext, new ModelMysteriousGuy(pContext.bakeLayer(ModelMysteriousGuy.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(EntityMysteriousGuy pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		if(pEntity.canTalk() && !pEntity.isTalking())
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0, 2.5F, 0);
			pPoseStack.mulPose(BTAClientUtil.MC.gameRenderer.getMainCamera().rotation());
			BTAClientUtil.drawQuad(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(MARK_TEXTURE)), 0.5F, LightTexture.FULL_BRIGHT);
			pPoseStack.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMysteriousGuy pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/mysterious_guy.png");
	}
}
