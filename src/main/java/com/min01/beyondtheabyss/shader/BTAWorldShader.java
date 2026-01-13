package com.min01.beyondtheabyss.shader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.config.BTAConfig;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

//TODO required some more optimization;
public class BTAWorldShader
{
	public final Matrix4f inverseMat = new Matrix4f();
    public final ExecutorService volumeExecutor = Executors.newSingleThreadExecutor();

	public final ResourceKey<Level> world;
	public final Supplier<ExtendedPostChain> shader;
	public final ResourceKey<Biome> biome;
    public final boolean is3DSampler;
    public final String samplerName;
    
    public NativeImage volumeImage;
    
    public int volumeTextureId = -1;
    
    public byte[] sharedBuffer = new byte[VOLUME_WIDTH * VOLUME_HEIGHT * VOLUME_DEPTH];
    public boolean isProcessing = false;
    
    public static final int VOLUME_WIDTH = 128;
    public static final int VOLUME_HEIGHT = 128;
    public static final int VOLUME_DEPTH = 128;
    
    public static final ThreadLocal<BlockPos.MutableBlockPos> THREAD_POS = ThreadLocal.withInitial(BlockPos.MutableBlockPos::new);

    public static final List<BTAWorldShader> WORLD_SHADERS = new ArrayList<>();
    
    public BTAWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader)
    {
    	this(world, shader, null, false, "");
	}
    
    public BTAWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader, ResourceKey<Biome> biome, boolean is3DSampler, String samplerName) 
    {
    	this.world = world;
    	this.shader = shader;
    	this.biome = biome;
    	this.is3DSampler = is3DSampler;
    	this.samplerName = samplerName;
	}
    
    public void render(PoseStack mtx, float frameTime, Camera camera)
    {
    	if(!BTAConfig.worldShaders.get())
    	{
    		return;
    	}
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
				if(this.is3DSampler)
				{
					this.apply(mtx, frameTime);
				}
				else
				{
					mtx.translate(pos.x, pos.y, pos.z);
					this.apply(mtx, frameTime);
				}
				mtx.popPose();
			}
		}
    }

    public void requestUpdate(BlockPos centerPos, ClientLevel level) 
    {
        if(this.isProcessing)
        {
        	return;
        }

        this.isProcessing = true;
        
        BlockPos startPos = centerPos.immutable();
        
        CompletableFuture.runAsync(() ->
        {
            byte[] tempBuffer = new byte[sharedBuffer.length];
            BlockPos.MutableBlockPos mutablePos = THREAD_POS.get();
            
            for(int z = 0; z < VOLUME_DEPTH; z++)
            {
                int worldZ = startPos.getZ() - VOLUME_DEPTH / 2 + z;

                for(int y = 0; y < VOLUME_HEIGHT; y++)
                {
                    int worldY = startPos.getY() - VOLUME_HEIGHT / 2 + y;
                    
                    for(int x = 0; x < VOLUME_WIDTH; x++) 
                    {
                        int worldX = startPos.getX() - VOLUME_WIDTH / 2 + x;
                        mutablePos.set(worldX, worldY, worldZ);
                        
                        boolean canApply = level.getBiome(mutablePos).is(this.biome) && level.canSeeSky(mutablePos);
                        
                        int index = (z * VOLUME_HEIGHT * VOLUME_WIDTH) + (y * VOLUME_WIDTH) + x;
                        tempBuffer[index] = canApply ? (byte) 255 : (byte) 0;
                    }
                }
            }
            
            BTAClientUtil.MC.execute(() ->
            {
                this.sharedBuffer = tempBuffer;
                this.isProcessing = false;
            });
            
        }, this.volumeExecutor);
    }

    public int getOrCreateVolumeTextureId(PoseStack stack)
    {
        ClientLevel level = BTAClientUtil.MC.level;
        
        if(this.volumeTextureId == -1)
        {
            this.volumeTextureId = TextureUtil.generateTextureId();
            RenderSystem.bindTexture(this.volumeTextureId);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL12.GL_TEXTURE_WRAP_R, GL12.GL_CLAMP_TO_EDGE);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GlStateManager._texParameter(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            this.volumeImage = new NativeImage(NativeImage.Format.LUMINANCE, VOLUME_WIDTH, VOLUME_HEIGHT * VOLUME_DEPTH, false);

            BlockPos blockPos = BlockPos.containing(BTAClientUtil.MC.gameRenderer.getMainCamera().getPosition());
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            
            this.requestUpdate(blockPos, level);
            
            int width = VOLUME_WIDTH, height = VOLUME_HEIGHT, depth = VOLUME_DEPTH;
            int startX = blockPos.getX() - width / 2;
            int startY = blockPos.getY() - height / 2;
            int startZ = blockPos.getZ() - depth / 2;

            long baseAddress = this.volumeImage.pixels + (long)(this.volumeImage.format().luminanceOffset() / 8);
            int components = this.volumeImage.format().components();
            int imgWidth = this.volumeImage.getWidth();

            for(int z = 0; z < depth; z++) 
            {
                int worldZ = startZ + z;
                int baseImageY = z * height;
                
                for(int x = 0; x < width; x++)
                {
                    int worldX = startX + x;
                    for(int y = 0; y < height; y++)
                    {
                        int worldY = startY + y;
                        int imageY = baseImageY + y;
                        mutablePos.set(worldX, worldY, worldZ);
                        
                        if(!level.isLoaded(mutablePos))
                        {
                        	continue;
                        }
                        int bufferIdx = (z * VOLUME_HEIGHT * VOLUME_WIDTH) + (y * VOLUME_WIDTH) + x;
                        long pixelAddress = baseAddress + ((long)x + (long)imageY * imgWidth) * components;
                        MemoryUtil.memPutByte(pixelAddress, this.sharedBuffer[bufferIdx]);
                    }
                }
            }
            
            RenderSystem.bindTexture(this.volumeTextureId);
            RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL12.glTexImage3D(GL12.GL_TEXTURE_3D, 0, GL30.GL_R8, VOLUME_WIDTH, VOLUME_HEIGHT, VOLUME_DEPTH, 0, GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, this.volumeImage.pixels);
            RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
        }
        return this.volumeTextureId;
    }
	
	public void apply(PoseStack mtx, float frameTime)
	{
		Minecraft minecraft = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = this.shader.get();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> minecraft.getTextureManager().getTexture(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			if(this.is3DSampler)
			{
	            shader.setSampler(this.samplerName + "VolumeSampler", () -> this.getOrCreateVolumeTextureId(mtx));
                shader.safeGetUniform("VolumeSize").set((float)VOLUME_WIDTH, (float)VOLUME_HEIGHT, (float)VOLUME_DEPTH);
			}
			shader.safeGetUniform("InverseTransformMatrix").set(BTAClientUtil.getInverseTransformMatrix(this.inverseMat, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float) (minecraft.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			shaderChain.process(frameTime);
			minecraft.getMainRenderTarget().bindWrite(false);
		}
	}
	
    public static void registerWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader, ResourceKey<Biome> biome, String samplerName)
    {
    	registerWorldShader(world, shader, biome, true, samplerName);
    }
	
    public static void registerWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader)
    {
    	registerWorldShader(world, shader, null, false, "");
    }
	
    public static void registerWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader, ResourceKey<Biome> biome, boolean is3DSampler, String samplerName)
    {
    	BTAWorldShader worldShader = new BTAWorldShader(world, shader, biome, is3DSampler, samplerName);
    	WORLD_SHADERS.add(worldShader);
    }
}