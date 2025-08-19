package com.min01.beyondtheabyss.mixin;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.lights.DynamicLights;
import com.min01.beyondtheabyss.lights.LevelRendererAccessor;
import com.min01.beyondtheabyss.misc.DepthReader;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer implements LevelRendererAccessor
{
    private static final Matrix4f INVERSE_MAT = new Matrix4f();
	
    @Nullable
    @Shadow
    private ClientLevel level;

    @Shadow
    private Frustum cullingFrustum;
    
    private boolean setup = true;
    
    private DynamicTexture sandstormTex;
    
    private DepthReader depthReader;
    
	@Invoker("setSectionDirty")
	@Override
	public abstract void scheduleChunkRebuild(int x, int y, int z, boolean important);

	@Inject(at = @At(value = "HEAD"), method = "renderLevel")
	private void renderLevelHead(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		BTAClientUtil.MC.getProfiler().incrementCounter("dynamic_lighting");
	    DynamicLights.get().updateAll(LevelRenderer.class.cast(this));
        Player player = BTAClientUtil.MC.player;
        if(player != null)
        {
            if(player.isPassenger() && player.getVehicle() instanceof EntitySubmarine submarine && !BTAClientUtil.MC.gameRenderer.getMainCamera().isDetached())
            {
        		float yRot = Mth.rotLerp(frameTime, submarine.yRotO, submarine.getYRot());
                float xRot = Mth.lerp(frameTime, submarine.xRotO, submarine.getXRot());
                mtx.mulPose(new Quaternionf().rotationZYX(0.0F, (float) Math.toRadians(yRot), (float) Math.toRadians(xRot)));
            }
        }
	}
	
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevelTail(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		if(this.level != null)
		{
			ResourceKey<Level> dimension = this.level.dimension();
			Entity camEntity = BTAClientUtil.MC.cameraEntity;
			if(camEntity != null && camEntity.isAlive())
			{
				double x = Mth.lerp((double)frameTime, camEntity.xOld, camEntity.getX());
				double y = Mth.lerp((double)frameTime, camEntity.yOld, camEntity.getY());
				double z = Mth.lerp((double)frameTime, camEntity.zOld, camEntity.getZ());
				Vec3 camPos = camera.getPosition();
				Vec3 playerPos = new Vec3(x, y, z);
				
				mtx.pushPose();
				BlockPos surfacePos = this.level.getHeightmapPos(Types.WORLD_SURFACE_WG, new BlockPos(0, 256, 0));
				Vec3 pos = new Vec3(0, surfacePos.getY(), 0).subtract(camPos);
				mtx.translate(pos.x, pos.y, pos.z);
				if(dimension == BTAWorlds.EVERGREEN)
				{
					this.applyFog(mtx, frameTime, playerPos);
				}
				mtx.popPose();
				
				mtx.pushPose();
				Vec3 pos1 = playerPos.subtract(camPos);
				mtx.translate(pos1.x, pos1.y, pos1.z);
				if(dimension == BTAWorlds.MIRRORED_CITY)
				{
					this.applyMist(mtx, frameTime);
				}
				mtx.popPose();
				
				mtx.pushPose();
				BlockPos surfacePos2 = this.level.getHeightmapPos(Types.WORLD_SURFACE_WG, new BlockPos(0, 256, 0));
				Vec3 pos2 = new Vec3(0, surfacePos2.getY(), 0).subtract(camPos);
				mtx.translate(pos2.x, pos2.y, pos2.z);
				if(dimension == BTAWorlds.ENDLESS_DESERT)
				{
					if(this.level.canSeeSky(camEntity.blockPosition()))
					{
						
					}
					else
					{

					}
					
					if(this.setup)
					{
						this.setup();
					}
					else
					{
						this.update(frameTime);
						this.applySandstorm(mtx, frameTime, this.sandstormTex.getId());
					}
				}
				mtx.popPose();
			}
		}
	}
	
	@Inject(at = @At("TAIL"), method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", cancellable = true)
	private static void getLightColor(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir)
	{
		if(!level.getBlockState(pos).isSolidRender(level, pos))
		{
			cir.setReturnValue(DynamicLights.get().getLightmapWithDynamicLight(pos, cir.getReturnValue()));
		}
	}
	
	private void setup()
	{
		this.setup = false;
		int width = BTAClientUtil.MC.getWindow().getWidth();
		int height = BTAClientUtil.MC.getWindow().getHeight();
		this.sandstormTex = new DynamicTexture(width, height, true);
		
		NativeImage maskImage = this.sandstormTex.getPixels();
		
		for(int y = 0; y < height; y++) 
		{
		    for(int x = 0; x < width; x++) 
		    {
		        maskImage.setPixelRGBA(x, y, 0x00000000);
		    }
		}
		
		this.depthReader = new DepthReader(width, height);
		
		this.sandstormTex.upload();
	}
	
	private void update(float partialTicks)
	{
		Minecraft minecraft = BTAClientUtil.MC;
		int width = minecraft.getWindow().getWidth();
		int height = minecraft.getWindow().getHeight();
		Camera camera = minecraft.gameRenderer.getMainCamera();
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
		NativeImage maskImage = this.sandstormTex.getPixels();
		
	    for(int y = 0; y < height; y++) 
	    {
	        for(int x = 0; x < width; x++) 
	        {
	            maskImage.setPixelRGBA(x, y, 0x00000000);
	        }
	    }

	    List<Vector3f> visibleBlocks = this.getVisibleBlocks(5, partialTicks);
	    FloatBuffer buffer = this.depthReader.readDepth(minecraft.getMainRenderTarget().getDepthTextureId());
	    for(Vector3f vec3 : visibleBlocks)
	    {
	    	Vector3f[] corners = new Vector3f[] 
	    			{
	    			  new Vector3f(vec3.x, vec3.y, vec3.z), 
	    			  new Vector3f(vec3.x + 1, vec3.y, vec3.z), 
	    			  new Vector3f(vec3.x, vec3.y + 1, vec3.z), 
	    			  new Vector3f(vec3.x, vec3.y, vec3.z + 1), 
	    			  new Vector3f(vec3.x + 1, vec3.y + 1, vec3.z), 
	    			  new Vector3f(vec3.x + 1, vec3.y, vec3.z + 1), 
	    			  new Vector3f(vec3.x, vec3.y + 1, vec3.z + 1), 
	    			  new Vector3f(vec3.x + 1, vec3.y + 1, vec3.z + 1)
	    			};
	    	
	        float minX = width;
	        float minY = height;
	        float maxX = 0;
	        float maxY = 0;
	        float minDepth = Float.MAX_VALUE;

	        for(Vector3f corner : corners)
	        {
	        	Vector3f screenPos1 = BTAClientUtil.projectWorldToScreen(corner, viewMatrix, projMatrix, width, height);
	        	minX = Math.min(minX, screenPos1.x);
	        	minY = Math.min(minY, screenPos1.y);
	        	maxX = Math.max(maxX, screenPos1.x);
	        	maxY = Math.max(maxY, screenPos1.y);
	        	minDepth = Math.min(minDepth, screenPos1.z);
	        }

	        int startX = Math.max(0, (int)Math.floor(minX));
	        int startY = Math.max(0, (int)Math.floor(minY));
	        int endX = Math.min(width - 1, (int)Math.ceil(maxX));
	        int endY = Math.min(height - 1, (int)Math.ceil(maxY));

	        for(int y = startY; y <= endY; y++)
	        {
	            for(int x = startX; x <= endX; x++)
	            {
	            	float epsilon = 0.01F;
	            	float depth = buffer.get(y * width + x);
	                
	                if(depth <= 0.0F)
	                	continue;

	                if(minDepth <= depth + epsilon) 
	                {
	                    maskImage.setPixelRGBA(x, y, 0xFFFFFFFF);
	                }
	            }
	        }
	    }

	    this.sandstormTex.upload();
	}
	
	private List<Vector3f> getVisibleBlocks(int maxDistance, float partialTicks) 
	{
        List<Vector3f> visibleBlocks = new ArrayList<>();
		Entity camEntity = BTAClientUtil.MC.gameRenderer.getMainCamera().getEntity();
		Vec3 startPos = BTAUtil.getLookPos(camEntity.getRotationVector(), camEntity.getEyePosition(partialTicks), 0, 0, 1);
		Vec3 p = BTAUtil.getLookPos(camEntity.getRotationVector(), camEntity.getEyePosition(partialTicks), 0, 0, 5);
		for(int x = -maxDistance; x < maxDistance; x++)
		{
			for(int y = -5; y < maxDistance; y++)
			{
				for(int z = 2; z < maxDistance; z++)
				{
					Vec3 endPos = BTAUtil.getLookPos(camEntity.getRotationVector(), camEntity.getEyePosition(partialTicks), x, y, z);
					BlockHitResult blockHit = this.level.clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, camEntity));
					Vec3 hitPos = blockHit.getLocation();
					Vec3 hit = BTAUtil.getLookPos(camEntity.getRotationVector(), hitPos, 0, 0, -1);
	                if(this.level.canSeeSky(BlockPos.containing(hit)))
	                {
	                	visibleBlocks.add(new Vector3f((float)hit.x, (float)hit.y, (float)hit.z));
	                	break;
	                }
				}
			}
		}
		
		//test purpose;
		BlockHitResult blockHit = this.level.clip(new ClipContext(startPos, p, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, camEntity));
		Vec3 hit = BTAUtil.getLookPos(camEntity.getRotationVector(), blockHit.getLocation(), 0, 0, -1);
		if(this.level.canSeeSky(BlockPos.containing(hit)))
		{
			return List.of(new Vector3f((float)hit.x, (float)hit.y, (float)hit.z));
		}
		return List.of();
	}
	
	@Unique
	private void applySandstorm(PoseStack mtx, float frameTime, int texId)
	{
		Minecraft mc = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = BTAShaders.getSandstorm();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(mc.getWindow().getWidth(), mc.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> mc.getTextureManager().getTexture(new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			shader.setSampler("SandSampler", () -> texId);
			shader.safeGetUniform("InverseTransformMatrix").set(this.getInverseTransformMatrix(INVERSE_MAT, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float) (mc.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
	
	@Unique
	private void applyMist(PoseStack mtx, float frameTime)
	{
		Minecraft mc = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = BTAShaders.getMist();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(mc.getWindow().getWidth(), mc.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> mc.getTextureManager().getTexture(new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			shader.safeGetUniform("InverseTransformMatrix").set(this.getInverseTransformMatrix(INVERSE_MAT, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float) (mc.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
	
	@Unique
	private void applyFog(PoseStack mtx, float frameTime, Vec3 pos)
	{
		Minecraft mc = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = BTAShaders.getFog();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(mc.getWindow().getWidth(), mc.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> mc.getTextureManager().getTexture(new ResourceLocation(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			shader.safeGetUniform("InverseTransformMatrix").set(this.getInverseTransformMatrix(INVERSE_MAT, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float) (mc.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			shader.safeGetUniform("PlayerPos").set((float)pos.x, (float)pos.y, (float)pos.z);
			shader.safeGetUniform("ViewDist").set(BTAClientUtil.MC.options.renderDistance().get());
			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
	
	@Unique
	private Matrix4f getInverseTransformMatrix(Matrix4f outMat, Matrix4f modelView)
    {
		return outMat.identity().mul(RenderSystem.getProjectionMatrix()).mul(modelView).invert();
    }
}
