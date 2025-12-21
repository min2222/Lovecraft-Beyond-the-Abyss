package com.min01.beyondtheabyss.shader;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class ExtendedPostChain extends PostChain
{
	public ExtendedPostChain(TextureManager pTextureManager, ResourceManager pResourceManager, RenderTarget pScreenTarget, ResourceLocation pName) throws IOException, JsonSyntaxException
	{
		super(pTextureManager, pResourceManager, pScreenTarget, pName);
	}
	
	public ExtendedPostChain(String domain, String name) throws JsonSyntaxException, IOException
	{
		this(BTAClientUtil.MC.getTextureManager(), BTAClientUtil.MC.getResourceManager(), BTAClientUtil.MC.getMainRenderTarget(), ResourceLocation.fromNamespaceAndPath(domain, "shaders/post/" + name + ".json"));
		this.resize(BTAClientUtil.MC.getWindow().getWidth(), BTAClientUtil.MC.getWindow().getHeight());
	}

	public EffectInstance getMainShader()
	{
		return this.passes.get(0).getEffect();
	}

	@Override
	public void process(float frameTime)
	{
		Window window = BTAClientUtil.MC.getWindow();
		if(this.screenWidth != window.getWidth() || this.screenHeight != window.getHeight())
		{
			this.resize(window.getWidth(), window.getHeight());
		}
		super.process(frameTime);
	}
}
