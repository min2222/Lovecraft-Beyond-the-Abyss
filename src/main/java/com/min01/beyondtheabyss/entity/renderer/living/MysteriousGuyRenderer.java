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
	public static final ResourceLocation MARK_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/exclamation_mark.png"); 
	public MysteriousGuyRenderer(Context p_174304_) 
	{
		super(p_174304_, new ModelMysteriousGuy(p_174304_.bakeLayer(ModelMysteriousGuy.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(EntityMysteriousGuy p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_)
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
	public ResourceLocation getTextureLocation(EntityMysteriousGuy p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mysterious_guy.png");
	}
}
