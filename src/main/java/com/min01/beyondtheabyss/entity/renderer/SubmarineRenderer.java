package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SubmarineRenderer extends EntityRenderer<EntitySubmarine>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine.png");
	private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine_layer.png");
	private final ModelSubmarine model;
	public SubmarineRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.model = new ModelSubmarine(p_174008_.bakeLayer(ModelSubmarine.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntitySubmarine p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_)
	{
		p_114488_.pushPose();
		p_114488_.mulPose(Vector3f.XP.rotationDegrees(180));
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityTranslucent(TEXTURE));
        float f1 = Mth.rotLerp(p_114487_, p_114485_.yRotO, p_114485_.getYRot());
        float f6 = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
		this.model.setupAnim(p_114485_, 0, 0, 0, f1, f6);
		this.model.renderToBuffer(p_114488_, consumer, p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		if(p_114485_.getControllingPlayer() != null)
		{
	        float strength = 0.5F + Mth.clamp(((float) Math.cos((p_114485_.glowingTicks + p_114487_) * 0.1F)) - 0.5F, -0.5F, 0.5F);

	        strength += Mth.lerp(p_114487_, p_114485_.brightnessOld, p_114485_.brightness) * 1 * Mth.PI;
	        strength = Mth.clamp(strength, 0.1F, 1);
	        
			VertexConsumer eyeConsumer = p_114489_.getBuffer(RenderType.eyes(LAYER_TEXTURE));
			this.model.setupAnim(p_114485_, 0, 0, 0, f1, f6);
			this.model.renderToBuffer(p_114488_, eyeConsumer, p_114490_, OverlayTexture.NO_OVERLAY, strength, strength, strength, 1.0F);
		}
		p_114488_.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySubmarine p_114482_) 
	{
		return TEXTURE;
	}
}
