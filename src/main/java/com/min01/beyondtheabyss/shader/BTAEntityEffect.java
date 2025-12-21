package com.min01.beyondtheabyss.shader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;

public class BTAEntityEffect
{
	public static final List<BTAEntityEffect> EFFECTS = new ArrayList<>();
	public static final BTAEntityEffect PLAIN_FOG = new BTAEntityEffect(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "shaders/post/plain_fog.json"));
	
	public RenderTarget entityTarget;
	public PostChain entityEffect;
	
	public final ResourceLocation location;
	
	public final Minecraft minecraft = BTAClientUtil.MC;
	
	public BTAEntityEffect(ResourceLocation location) 
	{
		this.location = location;
		EFFECTS.add(this);
	}
	
	public void doEntityEffect()
	{
		if(this.shouldShowEntityEffect())
		{
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
			this.entityTarget.blitToScreen(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), false);
			this.entityTarget.clear(Minecraft.ON_OSX);
			this.minecraft.getMainRenderTarget().bindWrite(false);
			RenderSystem.disableBlend();
			RenderSystem.defaultBlendFunc();
		}
	}
	
	public void initEffect()
	{
		if(this.entityEffect != null)
		{
			this.entityEffect.close();
		}
		try 
		{
			this.entityEffect = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), this.location);
			this.entityEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
			this.entityTarget = this.entityEffect.getTempTarget("final");
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			this.entityEffect = null;
			this.entityTarget = null;
		} 
		catch(JsonSyntaxException e) 
		{
			e.printStackTrace();
			this.entityEffect = null;
			this.entityTarget = null;
		}
	}
	
	public void resize(int pWidth, int pHeight)
	{
		this.minecraft.levelRenderer.needsUpdate();
		if(this.entityEffect != null) 
		{
			this.entityEffect.resize(pWidth, pHeight);
		}
	}
	
	public void process()
	{
		if(this.shouldShowEntityEffect())
		{
			EffectInstance shader = this.entityEffect.passes.get(0).getEffect();
			if(shader != null)
			{
				shader.setSampler("ImageSampler", () -> this.minecraft.getTextureManager().getTexture(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
				shader.safeGetUniform("iResolution").set((float) this.minecraft.getWindow().getWidth(), (float) this.minecraft.getWindow().getHeight());
				shader.safeGetUniform("iTime").set((((float) (this.minecraft.level.getGameTime() % 2400000)) + this.minecraft.getFrameTime()) / 20.0F);
			}
			this.entityEffect.process(this.minecraft.getFrameTime());
			this.minecraft.getMainRenderTarget().bindWrite(false);
		}
	}
	
	public boolean shouldShowEntityEffect()
	{
		return this.entityTarget != null && this.entityEffect != null && this.minecraft.player != null;
	}
}
