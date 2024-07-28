package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAmarumGhost;
import com.min01.beyondtheabyss.entity.model.ModelAmarumGhost;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class AmarumGhostLayer extends GlowingLayer<EntityAmarumGhost, ModelAmarumGhost>
{
	public AmarumGhostLayer(RenderLayerParent<EntityAmarumGhost, ModelAmarumGhost> p_117346_, ModelAmarumGhost model) 
	{
		super(p_117346_, model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/amarum_ghost_layer.png"));
	}
	
	@Override
	public RenderType getRenderType(ResourceLocation texture) 
	{
		return RenderType.entityTranslucent(texture);
	}
	
	@Override
	public float getAlpha(EntityAmarumGhost entity) 
	{
		return 0.5F;
	}
}
