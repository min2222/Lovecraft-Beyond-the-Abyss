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
    public static ShaderInstance testShader;
    public static ShaderInstance illusionShader;

    private static final ShaderStateShard TEST_SHADER = new ShaderStateShard(() -> testShader);
    private static final ShaderStateShard ILLUSION_SHADER = new ShaderStateShard(() -> illusionShader);
    
	public BTARenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_)
	{
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
	}
	
    public static List<Pair<ShaderInstance, Consumer<ShaderInstance>>> registerShaders(ResourceProvider resourceManager)
    {
		try 
		{
			return List.of(Pair.of(new ShaderInstance(resourceManager, new ResourceLocation(BeyondtheAbyss.MODID, "rendertype_test"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> 
			{
				testShader = shaderInstance;
			}),Pair.of(new ShaderInstance(resourceManager, new ResourceLocation(BeyondtheAbyss.MODID, "rendertype_illusion"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> 
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
	
	public static RenderType test(ResourceLocation p_110477_) 
	{
        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_110477_, false, false);
		RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(TEST_SHADER).setTextureState(renderstateshard$texturestateshard).setCullState(NO_CULL).setLightmapState(LIGHTMAP).createCompositeState(true);
		return create("test", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, false, rendertype$compositestate);
	}
}
