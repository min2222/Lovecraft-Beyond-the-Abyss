package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.NecroshellEntity;
import com.min01.beyondtheabyss.entity.model.NecroshellModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NecroshellRenderer extends MobRenderer<NecroshellEntity, NecroshellModel>
{
	public NecroshellRenderer(Context pContext) 
	{
		super(pContext, new NecroshellModel(pContext.bakeLayer(NecroshellModel.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(NecroshellEntity pEntity) 
	{
		if(pEntity.isSlasherShell())
		{
			return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/necroshell_slasher.png");
		}
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/necroshell_blaster.png");
	}
}
