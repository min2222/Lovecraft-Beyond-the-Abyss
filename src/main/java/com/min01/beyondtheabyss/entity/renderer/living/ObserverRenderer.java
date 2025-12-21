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
	   
	public ObserverRenderer(Context pContext) 
	{
		super(pContext, new ModelObserver(pContext.bakeLayer(ModelObserver.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/observer_layer.png")));
	}
	
	@Override
	public void render(EntityObserver pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

        float f3 = 7.0F;
        float f4 = 3.0F;

        float xRot = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
        float yRot = Mth.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
        
        VertexConsumer consumer = pBuffer.getBuffer(BTARenderType.laser());
        
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-yRot + 180.0F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-xRot + 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        pPoseStack.translate(0, 0, -0.25F);
        Matrix4f matrix4f = pPoseStack.last().pose();
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
        pPoseStack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityObserver pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/observer.png");
	}
	
	private static void vertex01(VertexConsumer pConsumer, Matrix4f pMatrix)
	{
		pConsumer.vertex(pMatrix, 0.0F, 0.0F, 0.0F).color(255, 0, 0, 255).endVertex();
	}

	private static void vertex2(VertexConsumer pConsumer, Matrix4f pMatrix, float p_253704_, float p_253701_) 
	{
		pConsumer.vertex(pMatrix, -HALF_SQRT_3 * p_253701_, p_253704_, -0.5F * p_253701_).color(255, 0, 0, 0).endVertex();
	}

	private static void vertex3(VertexConsumer pConsumer, Matrix4f pMatrix, float p_253729_, float p_254030_)
	{
		pConsumer.vertex(pMatrix, HALF_SQRT_3 * p_254030_, p_253729_, -0.5F * p_254030_).color(255, 0, 0, 0).endVertex();
	}

	private static void vertex4(VertexConsumer pConsumer, Matrix4f pMatrix, float p_253649_, float p_253694_)
	{
		pConsumer.vertex(pMatrix, 0.0F, p_253649_, 1.0F * p_253694_).color(255, 0, 0, 0).endVertex();
	}
}
