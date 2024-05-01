package com.min01.beyondtheabyss.mixin;

import java.io.IOException;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonSyntaxException;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderBuffers;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer 
{
    @Shadow
    @Final
    private Minecraft minecraft;

	@Shadow
	@Final
	private RenderBuffers renderBuffers;
    
    @Unique
    private RenderTarget target;
    
    @Unique
    private ExtendedPostChain effect;
    
	private static final Matrix4f PROJECTION_INVERSE = new Matrix4f();
	private static final Matrix4f VIEW_INVERSE = new Matrix4f();
	
    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;initOutline()V", at = @At("TAIL"))
    private void initOutline(CallbackInfo ci)
    {
    	if(this.effect != null) 
    	{
    		this.effect.close();
    	}

        try
        {
        	this.effect = new ExtendedPostChain(BeyondtheAbyss.MODID, "test");
        	this.effect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
        	this.target = this.effect.getTempTarget("final");
        }
        catch(IOException ioexception) 
        {
        	this.effect = null;
        	this.target = null;
        }
        catch(JsonSyntaxException jsonsyntaxexception) 
        {
        	this.effect = null;
        	this.target = null;
        }
    }
    
    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;resize(II)V", at = @At("TAIL"))
    private void resize(int x, int y, CallbackInfo ci)
    {
    	if(this.effect != null)
    	{
    		this.effect.resize(x, y);
    	}
    }
    
    @Inject(method = "doEntityOutline", at = @At("TAIL"))
    private void doEntityOutline(CallbackInfo ci)
    {
    	RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        this.target.blitToScreen(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), false);
        this.effect.isEnabled = false;
        RenderSystem.disableBlend();
    }
	
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevel(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		//this.applyFog(mtx, frameTime);
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

			shaderChain.process(frameTime);
			mc.getMainRenderTarget().bindWrite(false);
		}
	}
}
