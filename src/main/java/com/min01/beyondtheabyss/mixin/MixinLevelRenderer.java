package com.min01.beyondtheabyss.mixin;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.misc.BTADynamicLights;
import com.min01.beyondtheabyss.misc.LevelRendererAccessor;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer implements LevelRendererAccessor
{
	private static final Matrix4f PROJECTION_INVERSE = new Matrix4f();
	private static final Matrix4f VIEW_INVERSE = new Matrix4f();
	
	@Invoker("setSectionDirty")
	@Override
	public abstract void scheduleChunkRebuild(int x, int y, int z, boolean important);

	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevel(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		//this.applyFog(mtx, frameTime);
		//this.applyBlur(frameTime);
	}        
	
	@Inject(at = @At(value = "HEAD"), method = "renderLevel")
	private void renderLevelHead(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		BTAClientUtil.MC.getProfiler().incrementCounter("dynamic_lighting");
	    BTADynamicLights.get().updateAll(LevelRenderer.class.cast(this));
	}
	
	@Inject(at = @At("TAIL"), method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", cancellable = true)
	private static void getLightColor(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir)
	{
		if(!level.getBlockState(pos).isSolidRender(level, pos))
		{
			cir.setReturnValue(BTADynamicLights.get().getLightmapWithDynamicLight(pos, cir.getReturnValue()));
		}
	}
	
	@Unique
	private void applyBlur(float frameTime)
	{
		Minecraft mc = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = BTAShaders.getBlur();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("BlurStrength").set(0.01F);

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
	
	@Unique
	private void applyFog(PoseStack mtx, float frameTime)
	{
		Minecraft mc = BTAClientUtil.MC;

		/*if(!mc.player.level.dimension().location().getPath().equals(BTAWorlds.FOGGY_PLAIN))
		{
			return;
		}*/

		ExtendedPostChain shaderChain = BTAShaders.getFog();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			PROJECTION_INVERSE.add(RenderSystem.getProjectionMatrix());
			PROJECTION_INVERSE.invert();

			VIEW_INVERSE.add(mtx.last().pose());
			VIEW_INVERSE.invert();

			shader.safeGetUniform("ProjInverseMat").set(PROJECTION_INVERSE);
			shader.safeGetUniform("ViewInverseMat").set(VIEW_INVERSE);
			shader.safeGetUniform("Darkness").set(0.5F);

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
}
