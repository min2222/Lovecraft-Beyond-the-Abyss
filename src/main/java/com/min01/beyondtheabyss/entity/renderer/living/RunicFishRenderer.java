package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.model.ModelRunicFish;
import com.min01.beyondtheabyss.entity.renderer.layer.RunicFishLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RunicFishRenderer extends MobRenderer<EntityRunicFish, ModelRunicFish>
{
	public RunicFishRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelRunicFish(p_174304_.bakeLayer(ModelRunicFish.LAYER_LOCATION)), 0.5F);
		this.addLayer(new RunicFishLayer(this, this.model));
	}
	
	@Override
	protected void setupRotations(EntityRunicFish p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if (!p_116226_.isInWater()) 
		{
			p_116227_.translate(0.5F, 0, 0);
			p_116227_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityRunicFish p_115812_) 
	{
		return new ResourceLocation(String.format("%s:textures/entity/runic_fish%d.png", BeyondtheAbyss.MODID, p_115812_.getVariant()));
	}
}
