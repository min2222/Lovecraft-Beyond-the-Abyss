package com.min01.beyondtheabyss.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.joml.Matrix4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.platform.NativeImage;
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
import net.minecraft.client.renderer.texture.DynamicTexture;
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
	private final List<AABB> chunkList = new ArrayList<>();

    private final ResourceKey<Level> world;
    private final Function<ResourceKey<Level>, ExtendedPostChain> shader;
    private final BiFunction<Level, BlockPos, Boolean> sampler;
    private final boolean useCustomSampler;
    private final String samplerName;
    
    private DynamicTexture texture;
    private int lastWidth;
    private int lastHeight;
    
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
		int height = minecraft.getWindow().getWidth();
		
		this.chunkList.clear();
		
		if(this.lastWidth != width || this.lastHeight != height)
		{
			this.texture = new DynamicTexture(width, height, true);
			this.lastWidth = width;
			this.lastHeight = height;

			NativeImage image = this.texture.getPixels();
			
		    for(int x = 0; x < width; x++) 
		    {
				for(int y = 0; y < height; y++) 
				{
			    	image.setPixelRGBA(x, y, 0x00000000);
				}
		    }
			
			this.texture.upload();
			return;
		}
		
		NativeImage image = this.texture.getPixels();
		
	    for(int x = 0; x < width; x++) 
	    {
			for(int y = 0; y < height; y++) 
			{
		    	image.setPixelRGBA(x, y, 0x00000000);
			}
	    }
		
		Map<String, AABB> closestChunks = new HashMap<>();
		
		//FIXME in endless desert, sandstorm only exist in underground;
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
			
			if(!closestChunks.containsKey(key) || distSqr < closestChunks.get(key).distanceToSqr(playerPos))
			{
				closestChunks.put(key, expanded);
				AABB shifted = new AABB(expanded.minX - playerPos.x, expanded.minY - playerPos.y, expanded.minZ - playerPos.z, expanded.maxX - playerPos.x, expanded.maxY - playerPos.y, expanded.maxZ - playerPos.z);
				this.chunkList.add(shifted);
			}
		}
		
		for(int i = 0; i < this.chunkList.size(); i++)
		{
			AABB aabb = this.chunkList.get(i);
			
			int minX = (int)aabb.minX;
			int minY = (int)aabb.minY;
			int minZ = (int)aabb.minZ;
			
			int maxX = (int)aabb.maxX;
			int maxY = (int)aabb.maxY;
			int maxZ = (int)aabb.maxZ;
			
			int base = i * 6;

			image.setPixelRGBA((base + 0) % width, (base + 0) / width, this.packIntToRGBA(minX));
			image.setPixelRGBA((base + 1) % width, (base + 1) / width, this.packIntToRGBA(minY));
			image.setPixelRGBA((base + 2) % width, (base + 2) / width, this.packIntToRGBA(minZ));

			image.setPixelRGBA((base + 3) % width, (base + 3) / width, this.packIntToRGBA(maxX));
			image.setPixelRGBA((base + 4) % width, (base + 4) / width, this.packIntToRGBA(maxY));
			image.setPixelRGBA((base + 5) % width, (base + 5) / width, this.packIntToRGBA(maxZ));
		}
		
	    this.texture.upload();
	}
	
	public int packIntToRGBA(int value) 
	{
	    int r = (value >>  0) & 0xFF;
	    int g = (value >>  8) & 0xFF;
	    int b = (value >> 16) & 0xFF;
	    int a = (value >> 24) & 0xFF;
	    return (a << 24) | (b << 16) | (g << 8) | r;
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
				shader.setSampler(this.samplerName + "Sampler", () -> this.texture.getId());
				shader.safeGetUniform("ChunkCount").set(this.chunkList.size());
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