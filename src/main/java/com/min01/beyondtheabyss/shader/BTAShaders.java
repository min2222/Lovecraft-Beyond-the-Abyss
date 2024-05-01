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
	protected static final List<ExtendedPostChain> SHADERS = new ArrayList<>(2);

	protected static ExtendedPostChain FOG;
	protected static ExtendedPostChain TEST;

	@Override
	public void onResourceManagerReload(ResourceManager mgr)
	{
		this.clear();
		try
		{
			init(mgr);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void init(ResourceManager mgr) throws IOException
	{
		FOG = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "fog"));
		TEST = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "test"));
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

	public static ExtendedPostChain getFog()
	{
		return FOG;
	}
	
	public static ExtendedPostChain getTest()
	{
		return TEST;
	}
}
