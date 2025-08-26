package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelTwinserpentBone;
import com.min01.beyondtheabyss.entity.renderer.layer.TwinserpentBoneLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TwinserpentBoneRenderer extends MobRenderer<EntityTwinserpentBone, ModelTwinserpentBone>
{
	public TwinserpentBoneRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelTwinserpentBone(p_174304_.bakeLayer(ModelTwinserpentBone.LAYER_LOCATION)), 0.0F);
		this.addLayer(new TwinserpentBoneLayer(this, this.model));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityTwinserpentBone p_114482_) 
	{
		switch(p_114482_.getVariant())
		{
		case 0:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_bone_fins.png");
		case 1:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_bone_arms.png");
		case 2:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_middlebone.png");
		}
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_middlebone.png");
	}
}
