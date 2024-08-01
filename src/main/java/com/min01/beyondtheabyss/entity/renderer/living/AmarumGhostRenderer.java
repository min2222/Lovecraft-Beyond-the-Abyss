package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAmarumGhost;
import com.min01.beyondtheabyss.entity.model.ModelAmarumGhost;
import com.min01.beyondtheabyss.entity.renderer.layer.AmarumGhostLayer;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

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
	protected void scale(EntityAmarumGhost p_115314_, PoseStack p_115315_, float p_115316_)
	{
		float f = p_115314_.getSwelling(p_115316_);
		float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
		f = Mth.clamp(f, 0.0F, 1.0F);
		f *= f;
		f *= f;
		float f2 = (1.0F + f * 0.4F) * f1;
		float f3 = (1.0F + f * 0.1F) / f1;
		p_115315_.scale(f2, f3, f2);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityAmarumGhost p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/amarum_ghost.png");
	}
}
