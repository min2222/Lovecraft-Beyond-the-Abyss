package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.PostChain;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer 
{
	private static final Matrix4f PROJECTION_INVERSE = new Matrix4f();
	private static final Matrix4f VIEW_INVERSE = new Matrix4f();
	
	@Shadow
	private PostChain transparencyChain;

	@Unique
	private RenderTarget depthCopy;
	
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PostChain;process(F)V", ordinal = 1), method = "renderLevel")
	private void renderLevelPreTransparency(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		Minecraft mc = Minecraft.getInstance();
		RenderTarget main = mc.getMainRenderTarget();
		
		if(this.depthCopy == null)
		{
			this.depthCopy = new TextureTarget(mc.getWindow().getWidth(), mc.getWindow().getHeight(), true, Minecraft.ON_OSX);
		}
		else if(this.depthCopy.width != main.width || this.depthCopy.height != main.height)
		{
			this.depthCopy.resize(main.width, main.height, false);
		}
		
		this.depthCopy.setClearColor(0f, 0f, 0f, 0f);
		this.depthCopy.copyDepthFrom(main);
	}

	
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevelPostTransparency(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		//this.applyFog(mtx, frameTime);
	}
	
	@Unique
	private void applyFog(PoseStack mtx, float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

		if(!mc.player.level.dimension().location().getPath().equals(""))
		{
			return;
		}

		ExtendedPostChain shaderChain = BTAShaders.getFog();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
			PROJECTION_INVERSE.invert();

			VIEW_INVERSE.load(mtx.last().pose());
			VIEW_INVERSE.invert();

			shader.safeGetUniform("ProjInverseMat").set(PROJECTION_INVERSE);
			shader.safeGetUniform("ViewInverseMat").set(VIEW_INVERSE);

			if(this.transparencyChain != null)
			{
				mc.getMainRenderTarget().copyDepthFrom(depthCopy);
			}

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
}
