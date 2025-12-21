package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.shader.BTAEntityEffect;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BTARenderType extends RenderType
{
	public static final RenderStateShard.OutputStateShard PLAIN_FOG_OUTPUT = new RenderStateShard.OutputStateShard("plain_fog_target", () -> 
    {
        RenderTarget target = BTAEntityEffect.PLAIN_FOG.entityTarget;
        if(target != null) 
        {
            target.copyDepthFrom(BTAClientUtil.MC.getMainRenderTarget());
            target.bindWrite(false);
        }
    }, 
    () ->
    {
    	BTAClientUtil.MC.getMainRenderTarget().bindWrite(false);
    });
	
	public BTARenderType(String pName, VertexFormat pFormat, Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) 
	{
		super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
	}
	
    public static RenderType plainFog(ResourceLocation texture) 
    {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setOutputState(PLAIN_FOG_OUTPUT).createCompositeState(true);
        return create("plain_fog", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
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
	
    public static RenderType eyesFix(ResourceLocation texture) 
    {
        RenderStateShard.TextureStateShard stateShard = new RenderStateShard.TextureStateShard(texture, false, false);
        return create("eyes_fix", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(stateShard).setTransparencyState(ADDITIVE_TRANSPARENCY).setCullState(NO_CULL).setWriteMaskState(COLOR_WRITE).createCompositeState(false));
    }
    
    public static RenderType laser()
    {
    	return create("lightning", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER).setWriteMaskState(COLOR_WRITE).setTransparencyState(LIGHTNING_TRANSPARENCY).createCompositeState(false));
    }
}
