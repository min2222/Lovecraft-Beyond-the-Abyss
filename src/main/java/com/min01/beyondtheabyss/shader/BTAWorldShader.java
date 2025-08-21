package com.min01.beyondtheabyss.shader;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;

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
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BTAWorldShader
{
	private final Matrix4f inverseMat = new Matrix4f();
	
	private boolean setup = true;
    private DynamicTexture texture;
    private DepthReader depthReader;
    
    private final ResourceKey<Level> world;
    private final Function<ResourceKey<Level>, ExtendedPostChain> shader;
    private final BiFunction<Level, Vec3, Vec3> pos;
    private final BiConsumer<EffectInstance, Vec3> effect;
    private final BiFunction<Level, BlockPos, Boolean> sampler;
    private final boolean useCustomSampler;
    private final String samplerName;
    
    public static final List<BTAWorldShader> WORLD_SHADERS = new ArrayList<>();
    
    public BTAWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, Vec3, Vec3> pos, BiConsumer<EffectInstance, Vec3> effect)
    {
    	this(world, shader, pos, effect, null, false, "");
	}
    
    public BTAWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, Vec3, Vec3> pos, BiConsumer<EffectInstance, Vec3> effect, BiFunction<Level, BlockPos, Boolean> sampler, boolean useCustomSampler, String samplerName) 
    {
    	this.world = world;
    	this.shader = shader;
    	this.pos = pos;
    	this.effect = effect;
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

			if(dimension == this.world)
			{
				mtx.pushPose();
				Vec3 pos = this.pos.apply(level, playerPos).subtract(camPos);
				mtx.translate(pos.x, pos.y, pos.z);
				if(this.useCustomSampler)
				{
					if(this.setup)
					{
						this.setup();
					}
					else
					{
						this.update(renderChunksInFrustum, camEntity);
						this.apply(mtx, frameTime, playerPos, this.texture.getId());
					}
				}
				else
				{
					this.apply(mtx, frameTime, playerPos, -1);
				}
				mtx.popPose();
			}
		}
    }
    
    public void setup()
    {
    	this.setup = false;
		int width = BTAClientUtil.MC.getWindow().getWidth();
		int height = BTAClientUtil.MC.getWindow().getHeight();
		this.texture = new DynamicTexture(width, height, true);
		NativeImage maskImage = this.texture.getPixels();
		
		for(int y = 0; y < height; y++) 
		{
		    for(int x = 0; x < width; x++) 
		    {
		        maskImage.setPixelRGBA(x, y, 0x00000000);
		    }
		}

		this.depthReader = new DepthReader(width, height);
		this.texture.upload();
    }
	
	public void update(ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum, Entity camEntity)
	{
		Minecraft minecraft = BTAClientUtil.MC;
		int width = minecraft.getWindow().getWidth();
		int height = minecraft.getWindow().getHeight();
		Camera camera = minecraft.gameRenderer.getMainCamera();
		Frustum frustum = minecraft.levelRenderer.getFrustum();
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.rotation(camera.rotation());
		viewMatrix.transpose();
		Vec3 pos = camera.getPosition();
		viewMatrix.translate((float)-pos.x, (float)-pos.y, (float)-pos.z);
		float fov = (float) Math.toRadians(minecraft.options.fov().get());
		float aspectRatio = (float) width / (float) height;
		float near = 0.05F;
		float far = 1000F;
		Matrix4f projMatrix = new Matrix4f().perspective(fov, aspectRatio, near, far, true);
		NativeImage maskImage = this.texture.getPixels();
	    List<Vector3f> list = new ArrayList<>();

        for(int x = 0; x < width; x++) 
        {
    	    for(int y = 0; y < height; y++) 
    	    {
                maskImage.setPixelRGBA(x, y, 0x00000000);
    	    }
        }
	    
	    for(RenderChunkInfo chunkInfo : renderChunksInFrustum)
	    {
	    	RenderChunk chunk = chunkInfo.chunk;
    		BlockPos origin = chunk.getOrigin();
    		if(!frustum.isVisible(chunk.getBoundingBox()) || !this.sampler.apply(minecraft.level, origin))
    		{
    			continue;
    		}

    		Vector3f worldPos = new Vector3f(origin.getX(), origin.getY(), origin.getZ());
        	list.add(worldPos);
	    }
	    
	    for(Vector3f worldPos : list)
	    {	
	    	float expansion = 1.0F;

	    	Vector3f[] expandedCorners = new Vector3f[] 
	    	{
	    	    new Vector3f(worldPos.x - 0.5F - expansion, worldPos.y - 0.5F - expansion, worldPos.z - 0.5F - expansion), 
	    	    new Vector3f(worldPos.x + 0.5F + expansion, worldPos.y - 0.5F - expansion, worldPos.z - 0.5F - expansion), 
	    	    new Vector3f(worldPos.x - 0.5F - expansion, worldPos.y + 0.5F + expansion, worldPos.z - 0.5F - expansion), 
	    	    new Vector3f(worldPos.x - 0.5F - expansion, worldPos.y - 0.5F - expansion, worldPos.z + 0.5F + expansion), 
	    	    new Vector3f(worldPos.x + 0.5F + expansion, worldPos.y + 0.5F + expansion, worldPos.z - 0.5F - expansion), 
	    	    new Vector3f(worldPos.x + 0.5F + expansion, worldPos.y - 0.5F - expansion, worldPos.z + 0.5F + expansion), 
	    	    new Vector3f(worldPos.x - 0.5F - expansion, worldPos.y + 0.5F + expansion, worldPos.z + 0.5F + expansion), 
	    	    new Vector3f(worldPos.x + 0.5F + expansion, worldPos.y + 0.5F + expansion, worldPos.z + 0.5F + expansion)
	    	};

	    	float minX = width;
	    	float minY = height;
	    	float maxX = 0;
	    	float maxY = 0;
	    	
	    	for(Vector3f corner : expandedCorners)
	    	{
	    	    Vector3f screenPos = BTAClientUtil.projectWorldToScreen(corner, viewMatrix, projMatrix, width, height);
	    	    minX = Math.min(minX, screenPos.x);
	    	    minY = Math.min(minY, screenPos.y);
	    	    maxX = Math.max(maxX, screenPos.x);
	    	    maxY = Math.max(maxY, screenPos.y);
	    	}
	    	
	    	int startX = Math.max(0, (int)Math.floor(minX));
	    	int startY = Math.max(0, (int)Math.floor(minY));
	    	int endX = Math.min(width - 1, (int)Math.ceil(maxX));
	    	int endY = Math.min(height - 1, (int)Math.ceil(maxY));
	    	
	    	for(int x = startX; x <= endX; x++)
	    	{
	    	    for(int y = startY; y <= endY; y++)
	    	    {
	                maskImage.setPixelRGBA(x, y, 0xFFFFFFFF);
	    	    }
	    	}
	    }
	    
	    this.texture.upload();
	}
	
	public void apply(PoseStack mtx, float frameTime, Vec3 pos, int texId)
	{
		Minecraft mc = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = this.shader.apply(this.world);
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(mc.getWindow().getWidth(), mc.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> mc.getTextureManager().getTexture(new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			if(texId != -1)
			{
				shader.setSampler(this.samplerName, () -> texId);
			}
			shader.safeGetUniform("InverseTransformMatrix").set(getInverseTransformMatrix(this.inverseMat, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float) (mc.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			this.effect.accept(shader, pos);
			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
	
	public static Matrix4f getInverseTransformMatrix(Matrix4f outMat, Matrix4f modelView)
    {
		return outMat.identity().mul(RenderSystem.getProjectionMatrix()).mul(modelView).invert();
    }
	
    public static void registerWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, Vec3, Vec3> pos, BiConsumer<EffectInstance, Vec3> effect)
    {
    	registerWorldShader(world, shader, pos, effect, null, false, "");
    }
	
    public static void registerWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, Vec3, Vec3> pos, BiConsumer<EffectInstance, Vec3> effect, BiFunction<Level, BlockPos, Boolean> sampler, boolean useCustomSampler, String samplerName)
    {
    	BTAWorldShader worldShader = new BTAWorldShader(world, shader, pos, effect, sampler, useCustomSampler, samplerName);
    	WORLD_SHADERS.add(worldShader);
    }
    
	public static class DepthReader 
	{
	    private int width;
	    private int height;
	    private int pboId;
	    private FloatBuffer depthBuffer;

	    public DepthReader(int width, int height)
	    {
	        this.width = width;
	        this.height = height;

	        this.pboId = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, this.pboId);
	        GL15.glBufferData(GL21.GL_PIXEL_PACK_BUFFER, width * height * Float.BYTES, GL15.GL_STREAM_READ);
	        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);

	        this.depthBuffer = BufferUtils.createFloatBuffer(width * height);
	    }

	    public FloatBuffer readDepth(int depthTexId)
	    {
	        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, this.pboId);

	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTexId);
	        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0);

	        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, this.pboId);
	        ByteBuffer mapped = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY, this.width * this.height * Float.BYTES, null);
	        if(mapped != null)
	        {
	        	this.depthBuffer.clear();
	            this.depthBuffer.put(mapped.asFloatBuffer());
	            this.depthBuffer.flip();
	            GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
	        }

	        GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);

	        return this.depthBuffer;
	    }
	}
}
