package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBone;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBoneRenderer extends MobRenderer<EntitySiamserpentBone, ModelSiamserpentBone>
{
	public SiamserpentBoneRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSiamserpentBone(p_174304_.bakeLayer(ModelSiamserpentBone.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentBone p_114482_) 
	{
		switch(p_114482_.getVariant())
		{
		case 0:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_fins.png");
		case 1:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_arms.png");
		}
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_fins.png");
	}
}
