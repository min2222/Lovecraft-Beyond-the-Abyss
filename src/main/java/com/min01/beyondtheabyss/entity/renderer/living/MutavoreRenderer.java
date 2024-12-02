package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.model.ModelMutavore;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MutavoreRenderer extends MobRenderer<EntityMutavore, ModelMutavore>
{
	public MutavoreRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelMutavore(p_174304_.bakeLayer(ModelMutavore.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMutavore p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
