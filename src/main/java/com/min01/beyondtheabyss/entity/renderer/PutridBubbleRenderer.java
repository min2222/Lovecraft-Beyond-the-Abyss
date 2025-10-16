package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.ModelPutridBubble;
import com.min01.beyondtheabyss.entity.projectile.EntityPutridBubble;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PutridBubbleRenderer extends EntityRenderer<EntityPutridBubble>
{
	public final ModelPutridBubble model;
	public PutridBubbleRenderer(Context p_174008_) 
	{
		super(p_174008_);
		this.model = new ModelPutridBubble(p_174008_.bakeLayer(ModelPutridBubble.LAYER_LOCATION));
	}

	@Override
	public void render(EntityPutridBubble p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		float tick = p_114485_.explosionTick * 0.08F;
		p_114488_.pushPose();
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.scale(1.0F + tick, 1.0F + tick, 1.0F + tick);
		p_114488_.translate(0.0F, -1.5F, 0.0F);
		this.model.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(p_114485_))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, Math.max(1.0F - tick, 0.0F));
		p_114488_.popPose();
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityPutridBubble p_114482_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/putrid_bubble.png");
	}
}