package com.min01.beyondtheabyss.shader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class BTAShaders implements ResourceManagerReloadListener 
{
	protected static final List<ExtendedPostChain> SHADERS = new ArrayList<>();

	protected static ExtendedPostChain FOG;
	protected static ExtendedPostChain PLAIN_FOG;
	protected static ExtendedPostChain SANDSTORM;

	@Override
	public void onResourceManagerReload(ResourceManager manager)
	{
		this.clear();
		try
		{
			init(manager);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void init(ResourceManager manager) throws IOException
	{
		FOG = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "fog"));
		PLAIN_FOG = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "plain_fog"));
		SANDSTORM = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "sandstorm"));
	}

	public void clear()
	{
		SHADERS.forEach(PostChain::close);
		SHADERS.clear();
	}

	public static ExtendedPostChain add(ExtendedPostChain shader)
	{
		SHADERS.add(shader);
		return shader;
	}
	
	public static ExtendedPostChain getSandstorm()
	{
		return SANDSTORM;
	}
	
	public static ExtendedPostChain getPlainFog()
	{
		return PLAIN_FOG;
	}
	
	public static ExtendedPostChain getFog()
	{
		return FOG;
	}
}
