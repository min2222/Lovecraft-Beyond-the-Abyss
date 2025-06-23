package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.entity.renderer.layer.SubmarineLayer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class SubmarineRenderer extends LivingEntityRenderer<EntitySubmarine, ModelSubmarine>
{
	public SubmarineRenderer(Context p_174008_)
	{
		super(p_174008_, new ModelSubmarine(p_174008_.bakeLayer(ModelSubmarine.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SubmarineLayer(this, this.model));
	}
	
	@Override
	protected RenderType getRenderType(EntitySubmarine p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) 
	{
		return RenderType.entityTranslucent(this.getTextureLocation(p_115322_));
	}
	
	@Override
	protected float getFlipDegrees(EntitySubmarine p_115337_)
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySubmarine p_114482_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine.png");
	}
	
	@Override
	protected boolean shouldShowName(EntitySubmarine p_115333_) 
	{
		return false;
	}
}
