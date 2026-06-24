package com.min01.beyondtheabyss.mixin;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.shader.BTAWorldShader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;

@Mixin(value = LevelRenderer.class, priority = -20000)
public abstract class MixinLevelRenderer
{
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevelTail(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		RenderSystem.depthMask(false);
		BTAWorldShader.WORLD_SHADERS.forEach(t -> 
		{
			t.render(mtx, frameTime, camera);
		});
		RenderSystem.depthMask(true);
	}
}
