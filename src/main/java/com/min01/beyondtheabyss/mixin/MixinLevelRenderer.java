package com.min01.beyondtheabyss.mixin;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.shader.BTAEntityEffect;
import com.min01.beyondtheabyss.shader.BTAWorldShader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;

@Mixin(value = LevelRenderer.class, priority = -20000)
public abstract class MixinLevelRenderer
{
    @Nullable
    @Shadow
    private ClientLevel level;
    
    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;initOutline()V", at = @At("TAIL"))
    private void initOutline(CallbackInfo ci)
    {
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.initEffect();
    	});
    }
    
    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;resize(II)V", at = @At("TAIL"))
    private void resize(int x, int y, CallbackInfo ci)
    {
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.resize(x, y);
    	});
    }
	
    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V", shift = At.Shift.BEFORE))
    private void renderLevelProcess(PoseStack poseStack, float frameTime, long l, boolean b, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) 
    {
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.process();
    	});
    }
	
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevelTail(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		RenderSystem.depthMask(false);
		new ArrayList<>(BTAWorldShader.WORLD_SHADERS).forEach(t -> 
		{
			t.render(mtx, frameTime, camera);
		});
		
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.doEntityEffect();
    	});
		RenderSystem.depthMask(true);
	}
}
