package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityNecroshell;
import com.min01.beyondtheabyss.entity.model.ModelNecroshell;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NecroshellRenderer extends MobRenderer<EntityNecroshell, ModelNecroshell>
{
	public NecroshellRenderer(Context p_174304_) 
	{
		super(p_174304_, new ModelNecroshell(p_174304_.bakeLayer(ModelNecroshell.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityNecroshell p_114482_) 
	{
		if(p_114482_.isSlasherShell())
		{
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/necroshell_slasher.png");
		}
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/necroshell_blaster.png");
	}
}
