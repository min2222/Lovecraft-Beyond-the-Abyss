package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IMultiModel;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentMiddleBone;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentMiddleBoneLayer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentMiddleBoneRenderer extends MobRenderer<EntitySiamserpentBone, ModelSiamserpentMiddleBone> implements IMultiModel<EntitySiamserpentBone>
{
	private final SiamserpentBoneRenderer boneRenderer;
	
	public SiamserpentMiddleBoneRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelSiamserpentMiddleBone(p_174304_.bakeLayer(ModelSiamserpentMiddleBone.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentMiddleBoneLayer(this, this.model));
		this.boneRenderer = new SiamserpentBoneRenderer(p_174304_);
	}
	
	@Override
	public void render(EntitySiamserpentBone p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		if(p_115455_.getVariant() == 0 || p_115455_.getVariant() == 1)
		{
			this.boneRenderer.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		}
		else
		{
			super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		}
	}
	
	@Override
	public HierarchicalModel<EntitySiamserpentBone> getModel(EntitySiamserpentBone entity) 
	{
		if(entity.getVariant() == 0 || entity.getVariant() == 1)
		{
			return this.boneRenderer.getModel();
		}
		return this.getModel();
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentBone p_114482_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone.png");
	}
}
