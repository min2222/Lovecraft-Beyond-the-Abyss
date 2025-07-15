package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class DeepAbyssPortalRenderer extends EntityRenderer<EntityDeepAbyssPortal>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/deep_abyss_portal.png");
	
	public DeepAbyssPortalRenderer(Context p_174008_)
	{
		super(p_174008_);
	}
	
	@Override
	public void render(EntityDeepAbyssPortal p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_)
	{
		p_114488_.pushPose();
		RenderSystem.disableDepthTest();
		p_114488_.mulPose(Axis.XP.rotationDegrees(90.0F));
		float scale = Math.min(p_114485_.tickCount * 0.2F, 5.0F);
		p_114488_.scale(scale, scale, scale);
		p_114488_.translate(0, 0, -0.001F);
		p_114488_.mulPose(Axis.ZP.rotationDegrees(p_114485_.tickCount));
		BTAClientUtil.drawQuad(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), 0.5F, p_114490_);
		p_114488_.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityDeepAbyssPortal p_114482_)
	{
		return TEXTURE;
	}
}
