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
	protected static ExtendedPostChain BLUR;
	protected static ExtendedPostChain MIST;

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
		BLUR = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "blur"));
		MIST = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "mist"));
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
	
	public static ExtendedPostChain getBlur()
	{
		return BLUR;
	}
	
	public static ExtendedPostChain getMist()
	{
		return MIST;
	}
}
