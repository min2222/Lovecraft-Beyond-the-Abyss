package com.min01.beyondtheabyss.entity.renderer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.misc.EntityDeepAbyssPortal;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
		if(p_114485_.tickCount < 25)
		{
			p_114488_.scale(p_114485_.tickCount * 0.2F, p_114485_.tickCount * 0.2F, p_114485_.tickCount * 0.2F);
		}
		else if(p_114485_.tickCount >= 25)
		{
			p_114488_.scale(5, 5, 5);
		}
		p_114488_.translate(0, 0, -0.001F);
		p_114488_.mulPose(Axis.ZP.rotationDegrees(p_114485_.tickCount));
		this.renderFlatQuad(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), p_114490_);
		p_114488_.popPose();
	}
	
    private void renderFlatQuad(PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) 
    {
        float minU = 0;
        float minV = 0;
        float maxU = 1;
        float maxV = 1;
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        drawVertex(matrix4f, matrix3f, builder, 0.5F, 0.5F, 0, minU, minV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, 0.5F, -0.5F, 0, minU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, -0.5F, -0.5F, 0, maxU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, -0.5F, 0.5F, 0, maxU, minV, 1, packedLightIn);
    }
    
    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn)
    {
    	vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }

	@Override
	public ResourceLocation getTextureLocation(EntityDeepAbyssPortal p_114482_)
	{
		return TEXTURE;
	}
}
