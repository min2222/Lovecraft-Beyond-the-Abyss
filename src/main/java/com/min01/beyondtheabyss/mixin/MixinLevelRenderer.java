package com.min01.beyondtheabyss.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.lights.DynamicLights;
import com.min01.beyondtheabyss.lights.LevelRendererAccessor;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer implements LevelRendererAccessor
{
    private static final Matrix4f INVERSE_MAT = new Matrix4f();
	
    @Nullable
    @Shadow
    private ClientLevel level;
    
	@Invoker("setSectionDirty")
	@Override
	public abstract void scheduleChunkRebuild(int x, int y, int z, boolean important);

	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevel(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
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
				Vec3 pos = playerPos.subtract(camPos);
				mtx.pushPose();
				mtx.translate(pos.x, pos.y, pos.z);
				if(dimension == BTAWorlds.MIRRORED_CITY)
				{
					this.applyMist(mtx, frameTime);
				}
				mtx.popPose();
			}
		}
	}
	
	@Inject(at = @At(value = "HEAD"), method = "renderLevel")
	private void renderLevelHead(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		BTAClientUtil.MC.getProfiler().incrementCounter("dynamic_lighting");
	    DynamicLights.get().updateAll(LevelRenderer.class.cast(this));
	}
	
	@Inject(at = @At("TAIL"), method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", cancellable = true)
	private static void getLightColor(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir)
	{
		if(!level.getBlockState(pos).isSolidRender(level, pos))
		{
			cir.setReturnValue(DynamicLights.get().getLightmapWithDynamicLight(pos, cir.getReturnValue()));
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
	private Matrix4f getInverseTransformMatrix(Matrix4f outMat, Matrix4f modelView)
    {
        outMat.setIdentity();
        outMat.multiply(RenderSystem.getProjectionMatrix());
        outMat.multiply(modelView);
        outMat.invert();
        return outMat;
    }
}
