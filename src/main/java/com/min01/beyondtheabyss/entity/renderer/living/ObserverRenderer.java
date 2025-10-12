package com.min01.beyondtheabyss.entity.renderer.living;

import org.joml.Matrix4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityObserver;
import com.min01.beyondtheabyss.entity.model.ModelObserver;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ObserverRenderer extends MobRenderer<EntityObserver, ModelObserver>
{
	private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D);
	   
	public ObserverRenderer(Context p_174304_) 
	{
		super(p_174304_, new ModelObserver(p_174304_.bakeLayer(ModelObserver.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/observer_layer.png")));
	}
	
	@Override
	public void render(EntityObserver p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_)
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);

        float f3 = 7.0F;
        float f4 = 3.0F;

        float xRot = Mth.lerp(p_115457_, p_115455_.xRotO, p_115455_.getXRot());
        float yRot = Mth.rotLerp(p_115457_, p_115455_.yHeadRotO, p_115455_.yHeadRot);
        
        VertexConsumer consumer = p_115459_.getBuffer(BTARenderType.laser());
        
        p_115458_.pushPose();
        p_115458_.mulPose(Axis.YP.rotationDegrees(-yRot + 180.0F));
        p_115458_.mulPose(Axis.XP.rotationDegrees(-xRot + 90.0F));
        p_115458_.mulPose(Axis.ZP.rotationDegrees(180.0F));
        p_115458_.translate(0, 0, -0.25F);
        Matrix4f matrix4f = p_115458_.last().pose();
        vertex01(consumer, matrix4f);
        vertex2(consumer, matrix4f, f3, f4);
        vertex3(consumer, matrix4f, f3, f4);
        vertex01(consumer, matrix4f);
        vertex3(consumer, matrix4f, f3, f4);
        vertex4(consumer, matrix4f, f3, f4);
        vertex01(consumer, matrix4f);
        vertex4(consumer, matrix4f, f3, f4);
        vertex2(consumer, matrix4f, f3, f4);
        vertex01(consumer, matrix4f);
        vertex4(consumer, matrix4f, f3, f4);
        vertex2(consumer, matrix4f, f3, f4);
        p_115458_.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityObserver p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/observer.png");
	}
	
	private static void vertex01(VertexConsumer p_254498_, Matrix4f p_253891_)
	{
		p_254498_.vertex(p_253891_, 0.0F, 0.0F, 0.0F).color(255, 0, 0, 255).endVertex();
	}

	private static void vertex2(VertexConsumer p_253956_, Matrix4f p_254053_, float p_253704_, float p_253701_) 
	{
		p_253956_.vertex(p_254053_, -HALF_SQRT_3 * p_253701_, p_253704_, -0.5F * p_253701_).color(255, 0, 0, 0).endVertex();
	}

	private static void vertex3(VertexConsumer p_253850_, Matrix4f p_254379_, float p_253729_, float p_254030_)
	{
		p_253850_.vertex(p_254379_, HALF_SQRT_3 * p_254030_, p_253729_, -0.5F * p_254030_).color(255, 0, 0, 0).endVertex();
	}

	private static void vertex4(VertexConsumer p_254184_, Matrix4f p_254082_, float p_253649_, float p_253694_)
	{
		p_254184_.vertex(p_254082_, 0.0F, p_253649_, 1.0F * p_253694_).color(255, 0, 0, 0).endVertex();
	}
}
