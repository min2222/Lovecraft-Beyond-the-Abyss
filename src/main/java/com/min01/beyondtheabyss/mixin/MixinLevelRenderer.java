package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer 
{
	private static final Matrix4f PROJECTION_INVERSE = new Matrix4f();
	private static final Matrix4f VIEW_INVERSE = new Matrix4f();
	
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevel(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		this.applyFog(mtx, frameTime);
		this.applyBlur(frameTime);
		//this.applyTest(mtx, frameTime);
	}
	
	@Unique
	private void applyTest(PoseStack mtx, float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

		ExtendedPostChain shaderChain = BTAShaders.getTest();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			PROJECTION_INVERSE.load(RenderSystem.getProjectionMatrix());
			PROJECTION_INVERSE.invert();

			VIEW_INVERSE.load(mtx.last().pose());
			VIEW_INVERSE.invert();

			shader.safeGetUniform("ProjInverseMat").set(PROJECTION_INVERSE);
			shader.safeGetUniform("ViewInverseMat").set(VIEW_INVERSE);
			
			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
	
	@Unique
	private void applyBlur(float frameTime)
	{
		Minecraft mc = Minecraft.getInstance();

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
		Minecraft mc = Minecraft.getInstance();

		/*if(!mc.player.level.dimension().location().getPath().equals(BTAWorlds.FOGGY_PLAIN))
		{
			return;
		}*/

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
			shader.safeGetUniform("Darkness").set(0.5F);

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
}
