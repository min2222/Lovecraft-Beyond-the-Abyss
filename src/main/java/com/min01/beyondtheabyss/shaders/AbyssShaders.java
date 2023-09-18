package com.min01.beyondtheabyss.shaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class AbyssShaders implements ResourceManagerReloadListener 
{
	protected static final List<ExtendedPostChain> SHADERS = new ArrayList<>(2);

	protected static ExtendedPostChain fog;

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
		fog = add(new ExtendedPostChain(BeyondtheAbyss.MODID, "fog"));
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
		return fog;
	}
}
