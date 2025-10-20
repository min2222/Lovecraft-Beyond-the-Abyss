package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusBody;
import com.min01.beyondtheabyss.entity.model.ModelForneusBody;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusBodyRenderer extends MobRenderer<EntityForneusBody, ModelForneusBody>
{
	public ForneusBodyRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelForneusBody(p_174304_.bakeLayer(ModelForneusBody.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(EntityForneusBody p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(EntityForneusBody p_115337_) 
	{
		return 0.0F;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityForneusBody p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
