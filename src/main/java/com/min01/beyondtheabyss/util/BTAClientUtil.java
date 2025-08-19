package com.min01.beyondtheabyss.util;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BTAClientUtil
{
	public static final Minecraft MC = Minecraft.getInstance();
	
	public static Vector3f projectWorldToScreen(Vector3f worldPos, Matrix4f viewMatrix, Matrix4f projMatrix, int screenWidth, int screenHeight) 
	{
	    Vector4f clipPos = projMatrix.transform(viewMatrix.transform(new Vector4f(worldPos, 1.0F)));
	    clipPos.x /= clipPos.w;
	    clipPos.y /= clipPos.w;
	    clipPos.z /= clipPos.w;
	    float screenX = (clipPos.x * 0.5F + 0.5F) * screenWidth;
	    float screenY = (1.0F - (clipPos.y * 0.5F + 0.5F)) * screenHeight;
	    float depth = clipPos.z * 0.5F + 0.5F;
	    return new Vector3f(screenX, screenY, depth);
	}
	
	public static void drawRing(float innerRadius, float outerRadius, float innerHeight, float outerHeight, float centerRatio, int segments, PoseStack stack, MultiBufferSource buffer, Vector4f innerColor, Vector4f centerColor, Vector4f outerColor, int light, RenderType renderType, Vec3 center) 
	{
	    VertexConsumer vertexBuffer = buffer.getBuffer(renderType);
	    Matrix4f matrix = stack.last().pose();
	    float centerRadius = innerRadius + (outerRadius - innerRadius) * centerRatio;
	    float centerHeight = innerHeight + (outerHeight - innerHeight) * centerRatio;
	    
	    float minHeight = (float) (center.z - outerHeight);
	    float maxHeight = (float) (center.z - innerHeight);
	    float heightRange = maxHeight - minHeight;

	    for(int i = 0; i < segments; i++) 
	    {
	        float angle1 = (float)(2 * Math.PI * i / segments);
	        float angle2 = (float)(2 * Math.PI * (i + 1) / segments);

	        float u0 = (float)i / segments;
	        float u1 = (float)(i + 1) / segments;

	        Vec3 inner1 = new Vec3(center.x + innerRadius * Math.cos(angle1), center.y + innerRadius * Math.sin(angle1), center.z - innerHeight);
	        Vec3 inner2 = new Vec3(center.x + innerRadius * Math.cos(angle2), center.y + innerRadius * Math.sin(angle2), center.z - innerHeight);
	        Vec3 center1 = new Vec3(center.x + centerRadius * Math.cos(angle1), center.y + centerRadius * Math.sin(angle1), center.z - centerHeight);
	        Vec3 center2 = new Vec3(center.x + centerRadius * Math.cos(angle2), center.y + centerRadius * Math.sin(angle2), center.z - centerHeight);
	        Vec3 outer1 = new Vec3(center.x + outerRadius * Math.cos(angle1), center.y + outerRadius * Math.sin(angle1), center.z - outerHeight);
	        Vec3 outer2 = new Vec3(center.x + outerRadius * Math.cos(angle2), center.y + outerRadius * Math.sin(angle2), center.z - outerHeight);
	        
	        boolean flat = heightRange == 0.0F;

	        float vInner1 = flat ? 0.0F : computeV(inner1.z, minHeight, heightRange);
	        float vInner2 = flat ? 0.0F : computeV(inner2.z, minHeight, heightRange);
	        float vCenter1 = flat ? 0.5F : computeV(center1.z, minHeight, heightRange);
	        float vCenter2 = flat ? 0.5F : computeV(center2.z, minHeight, heightRange);
	        float vOuter1 = flat ? 1.0F : computeV(outer1.z, minHeight, heightRange);
	        float vOuter2 = flat ? 1.0F : computeV(outer2.z, minHeight, heightRange);

	        addQuadWithUV(vertexBuffer, matrix, inner2, center2, center1, inner1, u1, u1, u0, u0, vInner2, vCenter2, vCenter1, vInner1, innerColor, centerColor, centerColor, innerColor, light);
	        addQuadWithUV(vertexBuffer, matrix, center2, outer2, outer1, center1, u1, u1, u0, u0, vCenter2, vOuter2, vOuter1, vCenter1, centerColor, outerColor, outerColor, centerColor, light);
	    }
	}
	
	public static float computeV(double z, double minHeight, double heightRange) 
	{
	    return (float) ((z - minHeight) / heightRange);
	}
	
	public static void drawCylinder(float radius, float height, int segments, PoseStack stack, MultiBufferSource buffer, Vector4f color, int light, RenderType renderType, Vec3 center, float tileU, float tileV, float scrollSpeedU, float scrollSpeedV, float exponent, float time) 
	{
	    VertexConsumer vertexBuffer = buffer.getBuffer(renderType);
	    Matrix4f matrix = stack.last().pose();

	    for(int i = 0; i < segments; i++) 
	    {
	        int i0 = i;
	        int i1 = (i + 1);

	        float angle1 = (float)(2 * Math.PI * i0 / segments);
	        float angle2 = (float)(2 * Math.PI * i1 / segments);

	        Vec3 p0 = new Vec3(center.x + radius * Math.cos(angle1), center.y + radius * Math.sin(angle1), center.z);
	        Vec3 p1 = new Vec3(center.x + radius * Math.cos(angle2), center.y + radius * Math.sin(angle2), center.z);
	        Vec3 p3 = new Vec3(center.x + radius * Math.cos(angle1), center.y + radius * Math.sin(angle1), center.z + height);
	        Vec3 p2 = new Vec3(center.x + radius * Math.cos(angle2), center.y + radius * Math.sin(angle2), center.z + height);

	        float segmentFrac0 = (float)i0 / segments;
	        float segmentFrac1 = (float)i1 / segments;

	        segmentFrac0 = (float)Math.pow(segmentFrac0, exponent);
	        segmentFrac1 = (float)Math.pow(segmentFrac1, exponent);

	        float scrollU = (scrollSpeedU * time) % 1.0F;
	        float scrollV = (scrollSpeedV * time) % 1.0F;

	        float u0 = tileU * segmentFrac0 + scrollU;
	        float u1 = tileU * segmentFrac1 + scrollU;
	        float v0 = tileV * 0.0F + scrollV;
	        float v1 = tileV * 1.0F + scrollV;

	        addQuadWithUV(vertexBuffer, matrix, p1, p2, p3, p0, u1, u1, u0, u0, v0, v1, v1, v0, color, color, color, color, light);
	    }
	}
	
	public static void drawSphere(float radius, int latSegments, int lonSegments, float scrollSpeed, PoseStack stack, MultiBufferSource buffer, Vec3 color, float alpha, int light, RenderType renderType, double time, Vec3 center) 
	{
	    VertexConsumer vertexBuffer = buffer.getBuffer(renderType);
	    Matrix4f matrix = stack.last().pose();
	    float timeScroll = (float)(time * scrollSpeed);
	    Vec3[][] ringPoints = new Vec3[latSegments + 1][lonSegments + 1];
	    for(int lat = 0; lat <= latSegments; lat++) 
	    {
	        float theta = (float)(Math.PI * lat / latSegments);
	        for(int lon = 0; lon <= lonSegments; lon++)
	        {
	            float phi = (float)(2 * Math.PI * lon / lonSegments);
	            ringPoints[lat][lon] = sphericalToCartesian(radius, theta, phi).add(center);
	        }
	    }
	    for(int lat = 0; lat < latSegments; lat++) 
	    {
	        for(int lon = 0; lon < lonSegments; lon++) 
	        {
	            Vec3 p0 = ringPoints[lat][lon];
	            Vec3 p1 = ringPoints[lat][lon + 1];
	            Vec3 p2 = ringPoints[lat + 1][lon + 1];
	            Vec3 p3 = ringPoints[lat + 1][lon];
	            float u0 = (float) lon / lonSegments;
	            float u1 = (float)(lon + 1) / lonSegments;
	            float v0 = (float) lat / latSegments + timeScroll;
	            float v1 = (float)(lat + 1) / latSegments + timeScroll;
	            addQuadWithUV(vertexBuffer, matrix, p0, p1, p2, p3, u0, u1, v0, v1, color, alpha, light);
	        }
	    }
	}
	
	public static void addQuadWithUV(VertexConsumer buffer, Matrix4f matrix, Vec3 v0, Vec3 v1, Vec3 v2, Vec3 v3, float u0, float u1, float u2, float u3, float v0uv, float v1uv, float v2uv, float v3uv, Vector4f c0, Vector4f c1, Vector4f c2, Vector4f c3, int light)
	{
	    buffer.vertex(matrix, (float)v0.x, (float)v0.y, (float)v0.z).color(c0.x, c0.y, c0.z, c0.w).uv(u0, v0uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	    buffer.vertex(matrix, (float)v1.x, (float)v1.y, (float)v1.z).color(c1.x, c1.y, c1.z, c1.w).uv(u1, v1uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	    buffer.vertex(matrix, (float)v2.x, (float)v2.y, (float)v2.z).color(c2.x, c2.y, c2.z, c2.w).uv(u2, v2uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	    buffer.vertex(matrix, (float)v3.x, (float)v3.y, (float)v3.z).color(c3.x, c3.y, c3.z, c3.w).uv(u3, v3uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	}
	
	public static void addQuadWithUV(VertexConsumer buffer, Matrix4f matrix, Vec3 v0, Vec3 v1, Vec3 v2, Vec3 v3, float u0, float u1, float v0uv, float v1uv, Vec3 color, float alpha, int light) 
	{
	    buffer.vertex(matrix, (float) v0.x, (float) v0.y, (float) v0.z).color((float) color.x, (float) color.y, (float) color.z, alpha).uv(u0, v0uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	    buffer.vertex(matrix, (float) v1.x, (float) v1.y, (float) v1.z).color((float) color.x, (float) color.y, (float) color.z, alpha).uv(u1, v0uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	    buffer.vertex(matrix, (float) v2.x, (float) v2.y, (float) v2.z).color((float) color.x, (float) color.y, (float) color.z, alpha).uv(u1, v1uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	    buffer.vertex(matrix, (float) v3.x, (float) v3.y, (float) v3.z).color((float) color.x, (float) color.y, (float) color.z, alpha).uv(u0, v1uv).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0, 1, 0).endVertex();
	}
	
	public static Vec3 sphericalToCartesian(float r, float theta, float phi) 
	{
	    float x = (float) (r * Math.sin(theta) * Math.cos(phi));
	    float y = (float) (r * Math.cos(theta));
	    float z = (float) (r * Math.sin(theta) * Math.sin(phi));
	    return new Vec3(x, y, z);
	}
	
    public static void drawQuad(PoseStack stack, VertexConsumer consumer, float size, int packedLightIn) 
    {
        float minU = 0;
        float minV = 0;
        float maxU = 1;
        float maxV = 1;
        PoseStack.Pose matrixstack$entry = stack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        drawVertex(matrix4f, matrix3f, consumer, size, size, 0, minU, minV, 1.0F, packedLightIn);
        drawVertex(matrix4f, matrix3f, consumer, size, -size, 0, minU, maxV, 1.0F, packedLightIn);
        drawVertex(matrix4f, matrix3f, consumer, -size, -size, 0, maxU, maxV, 1.0F, packedLightIn);
        drawVertex(matrix4f, matrix3f, consumer, -size, size, 0, maxU, minV, 1.0F, packedLightIn);
    }
    
    public static void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn)
    {
    	vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }
    
    public static void drawBox(AABB boundingBox, PoseStack stack, MultiBufferSource bufferIn, Vec3 rgb, int light, int alpha, RenderType renderType) 
    {
        VertexConsumer vertexbuffer = bufferIn.getBuffer(renderType);
        Matrix4f matrix4f = stack.last().pose();
        float maxX = (float) boundingBox.maxX * 0.625F;
        float minX = (float) boundingBox.minX * 0.625F;
        float maxY = (float) boundingBox.maxY * 0.625F;
        float minY = (float) boundingBox.minY * 0.625F;
        float maxZ = (float) boundingBox.maxZ * 0.625F;
        float minZ = (float) boundingBox.minZ * 0.625F;

        float maxU = maxZ - minZ;
        float maxV = maxY - minY;
        float minU = minZ - maxZ;
        float minV = minY - maxY;
        // X+
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(1.0F, 0.0F, 0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(1.0F, 0.0F, 0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(1.0F, 0.0F, 0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(1.0F, 0.0F, 0F).endVertex();

        // X-
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(-1.0F, 0.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(-1.0F, 0.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(-1.0F, 0.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(-1.0F, 0.0F, 0.0F).endVertex();

        maxU = maxX - minX;
        maxV = maxY - minY;
        minU = minX - maxX;
        minV = minY - maxY;
        // Z-
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, -1.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, -1.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, -1.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, -1.0F).endVertex();

        // Z+
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, 1.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, 1.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, 1.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 0.0F, 1.0F).endVertex();


        maxU = maxZ - minZ;
        maxV = maxX - minX;
        minU = minZ - maxZ;
        minV = minX - maxX;
        // Y+
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();

        // Y-
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, -1.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, -1.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, -1.0F, 0.0F).endVertex();
        vertexbuffer.vertex(matrix4f, (float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ).color((float)rgb.x, (float)rgb.y, (float)rgb.z, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(0.0F, -1.0F, 0.0F).endVertex();
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends LivingEntity> HierarchicalModel<T> getModelFromEntity(T entity)
	{
		EntityRenderer renderer = MC.getEntityRenderDispatcher().getRenderer(entity);
		if(renderer instanceof LivingEntityRenderer livingRenderer)
		{
			return (HierarchicalModel<T>) livingRenderer.getModel();
		}
		return null;
	}
	
	//https://github.com/EEEAB/EEEABsMobs/blob/master/src/main/java/com/eeeab/animate/client/util/ModelPartUtils.java#L57
    
    public static Vec3 getWorldPositionOfMultiPart(Entity entity, ModelPart root, Vec3 rotation, String... modelPartName)
    {
    	return getWorldPosition(entity, root, false, rotation, modelPartName);
    }
    
    public static Vec3 getWorldPosition(Entity entity, ModelPart root, Vec3 rotation, String... modelPartName)
    {
    	return getWorldPosition(entity, root, true, rotation, modelPartName);
    }
    
    public static Vec3 getWorldPosition(Entity entity, ModelPart root, boolean translateToEntity, Vec3 rotation, String... modelPartName)
    {
        PoseStack poseStack = new PoseStack();
        if(translateToEntity)
        {
            float partialTick = MC.getFrameTime();
            double x = Mth.lerp((double)partialTick, entity.xOld, entity.getX());
            double y = Mth.lerp((double)partialTick, entity.yOld, entity.getY());
            double z = Mth.lerp((double)partialTick, entity.zOld, entity.getZ());
        	poseStack.translate(x, y, z);
        }
        Quaternionf quat = new Quaternionf().rotateXYZ((float) Math.toRadians(rotation.x), (float) Math.toRadians(-rotation.y + 180.0F), (float) Math.toRadians(rotation.z));
        poseStack.mulPose(quat);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        ModelPart nextPart = null;
        for(int i = 0; i < modelPartName.length; i++)
        {
            if(i == 0)
            {
                nextPart = root.getChild(modelPartName[0]);
                nextPart.translateAndRotate(poseStack);
            }
            else 
            {
                ModelPart child = nextPart.getChild(modelPartName[i]);
                child.translateAndRotate(poseStack);
                nextPart = child;
            }
        }
        PoseStack.Pose last = poseStack.last();
        Matrix4f matrix4f = last.pose();
        Vector4f vector4f = new Vector4f(0, 0, 0, 1);
        vector4f.mul(matrix4f);
        return new Vec3(vector4f.x(), vector4f.y(), vector4f.z());
    }
	
	public static void animateHead(ModelPart head, float netHeadYaw, float headPitch)
	{
		head.yRot += Math.toRadians(netHeadYaw);
		head.xRot += Math.toRadians(headPitch);
	}
}
