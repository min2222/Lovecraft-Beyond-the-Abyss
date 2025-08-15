package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.EntitySolomon;
import com.min01.beyondtheabyss.entity.model.ModelSolomon;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SolomonRenderer extends MobRenderer<EntitySolomon, ModelSolomon>
{
	public static final ResourceLocation MARK_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/exclamation_mark.png"); 
	public SolomonRenderer(Context p_174304_) 
	{
		super(p_174304_, new ModelSolomon(p_174304_.bakeLayer(ModelSolomon.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(EntitySolomon p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_)
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		if(p_115455_.canTalk() && !p_115455_.isTalking())
		{
			p_115458_.pushPose();
			p_115458_.translate(0, 2.5F, 0);
			p_115458_.mulPose(BTAClientUtil.MC.gameRenderer.getMainCamera().rotation());
			BTAClientUtil.drawQuad(p_115458_, p_115459_.getBuffer(RenderType.entityCutoutNoCull(MARK_TEXTURE)), 0.5F, LightTexture.FULL_BRIGHT);
			p_115458_.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySolomon p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/solomon.png");
	}
}
