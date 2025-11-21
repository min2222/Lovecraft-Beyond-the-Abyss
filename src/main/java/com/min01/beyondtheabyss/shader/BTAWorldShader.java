package com.min01.beyondtheabyss.shader;

import java.util.ArrayList;
import java.util.List;
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
	public final Matrix4f inverseMat = new Matrix4f();

	public final ResourceKey<Level> world;
	public final Function<ResourceKey<Level>, ExtendedPostChain> shader;
	public final BiFunction<Level, BlockPos, Boolean> sampler;
    public final boolean is3DSampler;
    public final String samplerName;
    
    public NativeImage volumeImage;
    
    public int volumeTextureId = -1;
    
    public static final int VOLUME_WIDTH = 16;
    public static final int VOLUME_HEIGHT = 16;
    public static final int VOLUME_DEPTH = 16;

    public static final List<BTAWorldShader> WORLD_SHADERS = new ArrayList<>();
    
    public BTAWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader)
    {
    	this(world, shader, null, false, "");
	}
    
    public BTAWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, BlockPos, Boolean> sampler, boolean is3DSampler, String samplerName) 
    {
    	this.world = world;
    	this.shader = shader;
    	this.sampler = sampler;
    	this.is3DSampler = is3DSampler;
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

    public int getOrCreateVolumeTextureId(PoseStack stack)
    {
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
            
            for(int z = 0; z < VOLUME_DEPTH; z++) 
            {
                for(int y = 0; y < VOLUME_HEIGHT; y++) 
                {
                    for(int x = 0; x < VOLUME_WIDTH; x++) 
                    {
                        int worldX = blockPos.getX() - VOLUME_WIDTH / 2 + x;
                        int worldY = blockPos.getY() - VOLUME_HEIGHT / 2 + y;
                        int worldZ = blockPos.getZ() - VOLUME_DEPTH / 2 + z;
                        
                        mutablePos.set(worldX, worldY, worldZ);
                        
                        int imageX = x;
                        int imageY = (z * VOLUME_HEIGHT) + y;
                        
                        boolean canApply = BTAClientUtil.MC.level != null && this.sampler.apply(BTAClientUtil.MC.level, mutablePos);
                        int density = canApply ? 255 : 0;
                        
                    	if(imageX < this.volumeImage.getWidth() && imageY < this.volumeImage.getHeight())
                    	{
                            this.volumeImage.setPixelLuminance(imageX, imageY, (byte) density);
                    	}
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

		ExtendedPostChain shaderChain = this.shader.apply(this.world);
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> minecraft.getTextureManager().getTexture(new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			if(this.is3DSampler)
			{
	            shader.setSampler(this.samplerName + "VolumeSampler", () -> this.getOrCreateVolumeTextureId(mtx));
                shader.safeGetUniform("VolumeSize").set((float)VOLUME_WIDTH, (float)VOLUME_HEIGHT, (float)VOLUME_DEPTH);
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
	
    public static void registerWorldShader(ResourceKey<Level> world, Function<ResourceKey<Level>, ExtendedPostChain> shader, BiFunction<Level, BlockPos, Boolean> sampler, boolean is3DSampler, String samplerName)
    {
    	BTAWorldShader worldShader = new BTAWorldShader(world, shader, sampler, is3DSampler, samplerName);
    	WORLD_SHADERS.add(worldShader);
    }
}