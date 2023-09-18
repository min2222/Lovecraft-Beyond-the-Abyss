package com.min01.beyondtheabyss.shaders;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class ExtendedPostChain extends PostChain
{
	public ExtendedPostChain(TextureManager p_110018_, ResourceManager p_110019_, RenderTarget p_110020_, ResourceLocation p_110021_) throws IOException, JsonSyntaxException
	{
		super(p_110018_, p_110019_, p_110020_, p_110021_);
	}
	
	public ExtendedPostChain(String domain, String name) throws JsonSyntaxException, IOException
	{
		this(Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getMainRenderTarget(), new ResourceLocation(domain, "shaders/post/" + name + ".json"));
		this.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
	}

	public EffectInstance getMainShader()
	{
		return this.passes.get(0).getEffect();
	}

	@Override
	public void process(float frameTime)
	{
		Window w = Minecraft.getInstance().getWindow();
		if(this.screenWidth != w.getWidth() || this.screenHeight != w.getHeight())
		{
			this.resize(w.getWidth(), w.getHeight());
		}
		super.process(frameTime);
	}
}
