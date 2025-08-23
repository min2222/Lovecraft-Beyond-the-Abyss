package com.min01.beyondtheabyss.shader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelRenderer.RenderChunkInfo;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.RenderChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BTAWorldShader
{
	private final Matrix4f inverseMat = new Matrix4f();
	private final Map<String, AABB> closestChunks = new HashMap<>();

    private final ResourceKey<Level> world;
    private final Function<ResourceKey<Level>, ExtendedPostChain> shader;
    private final BiFunction<Level, BlockPos, Boolean> sampler;
    private final boolean useCustomSampler;
    private final String samplerName;
    
    private ByteBuffer buffer = null;
    private int lastWidth = -1;
    private int lastHeight = -1;
    private int texId = -1;
    
    public static final List<BTAWorldShader> WORLD_SHADERS = new ArrayList<>();
    
    public BTAWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader)
    {
    	this(world, shader, null, false, "");
	}
    
    public BTAWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, BlockPos, Boolean> sampler, boolean useCustomSampler, String samplerName) 
    {
    	this.world = world;
    	this.shader = shader;
    	this.sampler = sampler;
    	this.useCustomSampler = useCustomSampler;
    	this.samplerName = samplerName;
	}
    
    public void render(PoseStack mtx, float frameTime, Camera camera, ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum)
    {
    	ClientLevel level = BTAClientUtil.MC.level;
		ResourceKey<Level> dimension = level.dimension();
		Entity camEntity = BTAClientUtil.MC.cameraEntity;
		if(camEntity != null)
		{
			double x = Mth.lerp((double)frameTime, camEntity.xOld, camEntity.getX());
			double y = Mth.lerp((double)frameTime, camEntity.yOld, camEntity.getY());
			double z = Mth.lerp((double)frameTime, camEntity.zOld, camEntity.getZ());
			Vec3 camPos = camera.getPosition();
			Vec3 playerPos = new Vec3(x, y, z);
			Vec3 pos = playerPos.subtract(camPos);
			
			if(dimension == this.world)
			{
				mtx.pushPose();
				mtx.translate(pos.x, pos.y, pos.z);
				if(this.useCustomSampler)
				{
					this.update(renderChunksInFrustum, playerPos);
					this.apply(mtx, frameTime);
				}
				else
				{
					this.apply(mtx, frameTime);
				}
				mtx.popPose();
			}
		}
    }
	
	public void update(ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum, Vec3 playerPos)
	{
		Minecraft minecraft = BTAClientUtil.MC;
		
		int width = minecraft.getWindow().getWidth();
		int height = minecraft.getWindow().getHeight();
		
	    if(width != this.lastWidth || height != this.lastHeight)
	    {
	        if(this.texId != -1)
	        {
	            TextureUtil.releaseTextureId(this.texId);
	        }

	        this.texId = TextureUtil.generateTextureId();
	        GlStateManager._bindTexture(this.texId);
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
	        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
	        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);

	        this.buffer = BufferUtils.createByteBuffer(width * height * 4);
	        this.lastWidth = width;
	        this.lastHeight = height;
	    }

		this.buffer.clear();
		
		for(RenderChunkInfo chunkInfo : renderChunksInFrustum)
		{
		    RenderChunk chunk = chunkInfo.chunk;
		    BlockPos origin = chunk.getOrigin();
		    AABB aabb = chunk.getBoundingBox();

		    if(!this.sampler.apply(minecraft.level, origin)) 
		    {
		        continue;
		    }
		    
	    	AABB expanded = new AABB(aabb.minX, minecraft.level.getMinBuildHeight(), aabb.minZ, aabb.maxX, minecraft.level.getMaxBuildHeight(), aabb.maxZ);

		    int cx = origin.getX() >> 4;
		    int cz = origin.getZ() >> 4;

		    String key = cx + "," + cz;

		    double distSqr = expanded.distanceToSqr(playerPos);

		    if(!this.closestChunks.containsKey(key) || distSqr < this.closestChunks.get(key).distanceToSqr(playerPos))
		    {
		    	this.closestChunks.put(key, expanded);
		    }
		}
		
		for(AABB aabb :	this.closestChunks.values())
		{
		    AABB shifted = new AABB(aabb.minX - playerPos.x, aabb.minY - playerPos.y, aabb.minZ - playerPos.z, aabb.maxX - playerPos.x, aabb.maxY - playerPos.y, aabb.maxZ - playerPos.z);
		    
		    this.buffer.put((byte)(Mth.clamp(shifted.minX, 0, 1) * 255)).put((byte)(Mth.clamp(shifted.minY, 0, 1) * 255)).put((byte)(Mth.clamp(shifted.minZ, 0, 1) * 255)).put((byte)255);
		    this.buffer.put((byte)(Mth.clamp(shifted.maxX, 0, 1) * 255)).put((byte)(Mth.clamp(shifted.maxY, 0, 1) * 255)).put((byte)(Mth.clamp(shifted.maxZ, 0, 1) * 255)).put((byte)255);
		}

		this.buffer.flip();

        GlStateManager._bindTexture(this.texId);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.buffer);
	}
	
	public void apply(PoseStack mtx, float frameTime)
	{
		Minecraft minecraft = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = this.shader.apply(this.world);
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> minecraft.getTextureManager().getTexture(new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			if(!this.samplerName.equals(""))
			{
				shader.setSampler(this.samplerName + "Sampler", () -> this.texId);
				shader.safeGetUniform("ChunkCount").set(this.closestChunks.values().size());
			}
			shader.safeGetUniform("InverseTransformMatrix").set(getInverseTransformMatrix(this.inverseMat, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float) (minecraft.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			shaderChain.process(frameTime);
			minecraft.getMainRenderTarget().bindWrite(false);
		}
	}
	
	public static Matrix4f getInverseTransformMatrix(Matrix4f outMat, Matrix4f modelView)
    {
		return outMat.identity().mul(RenderSystem.getProjectionMatrix()).mul(modelView).invert();
    }
	
    public static void registerWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader)
    {
    	registerWorldShader(world, shader, null, false, "");
    }
	
    public static void registerWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, BlockPos, Boolean> sampler, boolean useCustomSampler, String samplerName)
    {
    	BTAWorldShader worldShader = new BTAWorldShader(world, shader, sampler, useCustomSampler, samplerName);
    	WORLD_SHADERS.add(worldShader);
    }
}