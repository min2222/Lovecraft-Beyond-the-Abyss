package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusTail;
import com.min01.beyondtheabyss.entity.model.ModelForneusTail;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusTailRenderer extends MobRenderer<EntityForneusTail, ModelForneusTail>
{
	public ForneusTailRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelForneusTail(p_174304_.bakeLayer(ModelForneusTail.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(EntityForneusTail p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityForneusTail p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
