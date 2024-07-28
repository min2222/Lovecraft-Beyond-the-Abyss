package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAmarumGhost;
import com.min01.beyondtheabyss.entity.model.ModelAmarumGhost;
import com.min01.beyondtheabyss.entity.renderer.layer.AmarumGhostLayer;
import com.min01.beyondtheabyss.misc.BTARenderType;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AmarumGhostRenderer extends MobRenderer<EntityAmarumGhost, ModelAmarumGhost>
{
	public AmarumGhostRenderer(Context p_174304_) 
	{
		super(p_174304_, new ModelAmarumGhost(p_174304_.bakeLayer(ModelAmarumGhost.LAYER_LOCATION)), 0.1F);
		this.addLayer(new AmarumGhostLayer(this, this.model));
	}
	
	@Override
	protected RenderType getRenderType(EntityAmarumGhost p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) 
	{
		return BTARenderType.eyesFix(this.getTextureLocation(p_115322_));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityAmarumGhost p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/amarum_ghost.png");
	}
}
