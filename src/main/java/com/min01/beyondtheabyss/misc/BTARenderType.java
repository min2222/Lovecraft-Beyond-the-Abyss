package com.min01.beyondtheabyss.misc;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BTARenderType extends RenderType
{
	public BTARenderType(String pName, VertexFormat pFormat, Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) 
	{
		super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
	}
	
    public static RenderType blend(ResourceLocation texture)
    {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
        return create("blend", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
    }
    
    public static RenderType additive(ResourceLocation texture)
    {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setTransparencyState(LIGHTNING_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(false);
        return create("additive", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
    }

	//fixed culling issue (an issue that other side of flat cube isn't glowing properly)
    public static RenderType eyesFix(ResourceLocation texture) 
    {
        RenderStateShard.TextureStateShard stateShard = new RenderStateShard.TextureStateShard(texture, false, false);
        return create("eyes_fix", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(stateShard).setTransparencyState(ADDITIVE_TRANSPARENCY).setCullState(NO_CULL).setWriteMaskState(COLOR_WRITE).createCompositeState(false));
    }
    
    public static RenderType eyesNoAlpha(ResourceLocation texture) 
    {
        RenderStateShard.TextureStateShard stateShard = new RenderStateShard.TextureStateShard(texture, false, false);
        return create("eyes_no_alpha", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(stateShard).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setWriteMaskState(COLOR_DEPTH_WRITE).createCompositeState(false));
    }
    
    public static RenderType light()
    {
    	return create("light", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER).setWriteMaskState(COLOR_WRITE).setTransparencyState(LIGHTNING_TRANSPARENCY).createCompositeState(false));
    }
}
