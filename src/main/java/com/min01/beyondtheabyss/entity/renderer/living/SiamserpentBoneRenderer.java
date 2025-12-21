package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBone;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBoneRenderer extends MobRenderer<EntitySiamserpentBone, ModelSiamserpentBone>
{
	public SiamserpentBoneRenderer(Context pContext)
	{
		super(pContext, new ModelSiamserpentBone(pContext.bakeLayer(ModelSiamserpentBone.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentBone pEntity) 
	{
		switch(pEntity.getVariant())
		{
		case 0:
			return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_fins.png");
		case 1:
			return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_arms.png");
		}
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_fins.png");
	}
}
