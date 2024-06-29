package com.min01.beyondtheabyss.misc;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

public class BTARenderType extends RenderType
{
    public static ShaderInstance illusionShader;

    private static final ShaderStateShard ILLUSION_SHADER = new ShaderStateShard(() -> illusionShader);
    
	public BTARenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_)
	{
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}
	
    public static List<Pair<ShaderInstance, Consumer<ShaderInstance>>> registerShaders(ResourceProvider resourceManager)
    {
		try 
		{
			return List.of(Pair.of(new ShaderInstance(resourceManager, new ResourceLocation(BeyondtheAbyss.MODID, "rendertype_illusion"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> 
			{
				illusionShader = shaderInstance;
			}));
		}
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
    }
    
    public static RenderType illusion(ResourceLocation p_173253_)
    {
        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_173253_, false, false);
        return create("illusion", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(ILLUSION_SHADER).setTextureState(renderstateshard$texturestateshard).setTransparencyState(ADDITIVE_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).createCompositeState(false));
    }
    
    public static RenderType entityTranslucentColorWrite(ResourceLocation location)
    {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(location, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(true);
        return create("entity_translucent_color_write", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
    }
}
