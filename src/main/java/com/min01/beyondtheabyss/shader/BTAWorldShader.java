package com.min01.beyondtheabyss.shader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
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

    private final ResourceKey<Level> world;
    private final Function<ResourceKey<Level>, ExtendedPostChain> shader;
    private final BiFunction<Level, BlockPos, Boolean> sampler;
    private final boolean useCustomSampler;
    private final String samplerName;
    
    private NativeImage volumeImage;
    
    private int volumeTextureId = -1;
    private int volumeWidth = 64;
    private int volumeHeight = 64;
    private int volumeDepth = 64;
    
    private BlockPos lastVolumeCenter = null;
    private int lastChunkRenderDist;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ConcurrentLinkedQueue<List<BlockPos>> queue = new ConcurrentLinkedQueue<>();
    
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
    
    public void render(PoseStack mtx, float frameTime, Camera camera)
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
				if(this.useCustomSampler)
				{
					this.update(camPos);
					this.apply(mtx, frameTime, camera);
				}
				else
				{
					mtx.translate(pos.x, pos.y, pos.z);
					this.apply(mtx, frameTime, camera);
				}
				mtx.popPose();
			}
		}
    }

    public void update(Vec3 centerPos)
    {
    	if(BTAClientUtil.MC.screen != null)
    	{
    		return;
    	}
    	
    	int dist = BTAClientUtil.MC.options.renderDistance().get();
    	if(this.volumeImage == null || this.lastChunkRenderDist != dist)
    	{
		    int volume = dist * 16;
		    this.volumeWidth = volume;
		    this.volumeHeight = volume;
		    this.volumeDepth = volume;
		    this.lastChunkRenderDist = dist;
            this.volumeImage = new NativeImage(NativeImage.Format.LUMINANCE, this.volumeWidth, this.volumeHeight * this.volumeDepth, false);
    	}
    	
    	if(this.volumeTextureId == -1)
        {
        	this.volumeTextureId = TextureUtil.generateTextureId();
            RenderSystem.bindTexture(this.volumeTextureId);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL12.GL_TEXTURE_WRAP_R, GL12.GL_CLAMP_TO_EDGE);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }
	    
        BlockPos centerBlockPos = BlockPos.containing(centerPos);
        
        this.lastVolumeCenter = centerBlockPos;
        
        this.executor.submit(() -> 
        {
            try 
            {
                List<BlockPos> list = new ArrayList<>();
                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int z = 0; z < this.volumeDepth; z++) 
                {
                    for(int y = 0; y < this.volumeHeight; y++) 
                    {
                        for(int x = 0; x < this.volumeWidth; x++) 
                        {
                            int worldX = this.lastVolumeCenter.getX() - this.volumeWidth / 2 + x;
                            int worldY = this.lastVolumeCenter.getY() - this.volumeHeight / 2 + y;
                            int worldZ = this.lastVolumeCenter.getZ() - this.volumeDepth / 2 + z;
                            mutablePos.set(worldX, worldY, worldZ);
                            
                            int imageX = x;
                            int imageY = (z * this.volumeHeight) + y;

                            boolean canApply = BTAClientUtil.MC.level != null && this.sampler.apply(BTAClientUtil.MC.level, mutablePos);
                            int density = canApply ? 255 : 0;

                            list.add(new BlockPos(imageX, imageY, density));
                        }
                    }
                }
                this.queue.add(list);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        });

        List<BlockPos> list = this.queue.poll();

        if(list != null)
        {
            for(BlockPos blockPos : list)
            {
            	if(blockPos.getX() < this.volumeImage.getWidth() && blockPos.getY() < this.volumeImage.getHeight())
            	{
                    this.volumeImage.setPixelLuminance(blockPos.getX(), blockPos.getY(), (byte) blockPos.getZ());
            	}
            }

            RenderSystem.bindTexture(this.volumeTextureId);
            RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL12.glTexImage3D(GL12.GL_TEXTURE_3D, 0, GL30.GL_R8, this.volumeWidth, this.volumeHeight, this.volumeDepth, 0, GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, this.volumeImage.pixels);
            RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
        }
    }
	
	public void apply(PoseStack mtx, float frameTime, Camera camera)
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
				Vec3 cameraPos = camera.getPosition();
	            shader.setSampler(this.samplerName + "VolumeSampler", () -> this.volumeTextureId);
	            if(this.lastVolumeCenter != null)
	            {
	                shader.safeGetUniform("VolumeCenter").set((float)this.lastVolumeCenter.getX(), (float)this.lastVolumeCenter.getY(), (float)this.lastVolumeCenter.getZ());
	            }
	            shader.safeGetUniform("VolumeSize").set((float)this.volumeWidth, (float)this.volumeHeight, (float)this.volumeDepth);
	            shader.safeGetUniform("CameraPos").set((float)cameraPos.x, (float)cameraPos.y, (float)cameraPos.z);
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