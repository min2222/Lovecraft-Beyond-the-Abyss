package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFallenDiver;
import com.min01.beyondtheabyss.entity.model.ModelFallenDiver;
import com.min01.beyondtheabyss.entity.renderer.layer.DivingSetLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FallenDiverRenderer extends MobRenderer<EntityFallenDiver, ModelFallenDiver>
{
	public static final ResourceLocation FALLEN_DIVER_SET = new ResourceLocation(BeyondtheAbyss.MODID, "textures/armor/fallen_diver_set.png");
	
	public FallenDiverRenderer(Context p_174169_)
	{
		super(p_174169_, new ModelFallenDiver(p_174169_.bakeLayer(ModelFallenDiver.LAYER_LOCATION)), 0.5F);
		this.addLayer(new DivingSetLayer<>(this, FALLEN_DIVER_SET));
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityFallenDiver p_114891_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/fallen_diver.png");
	}
}
