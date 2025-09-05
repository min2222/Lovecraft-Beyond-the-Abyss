package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IMultiModel;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead.HeadType;
import com.min01.beyondtheabyss.entity.model.ModelTwinserpentSlasher;
import com.min01.beyondtheabyss.entity.renderer.layer.TwinserpentSlasherLayer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TwinserpentSlasherRenderer extends MobRenderer<EntityTwinserpentHead, ModelTwinserpentSlasher> implements IMultiModel<EntityTwinserpentHead>
{
	private static final ResourceLocation TEXTURE_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_slasher.png");
	private static final ResourceLocation DISABLED_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_slasher_disabled.png");
	private static final ResourceLocation DORMANT_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/twinserpent_slasher_dormant.png");

	private final TwinserpentBlasterRenderer blasterRenderer;
	
	public TwinserpentSlasherRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelTwinserpentSlasher(p_174304_.bakeLayer(ModelTwinserpentSlasher.LAYER_LOCATION)), 0.0F);
		this.addLayer(new TwinserpentSlasherLayer(this, this.model));
		this.blasterRenderer = new TwinserpentBlasterRenderer(p_174304_);
	}
	
	@Override
	public void render(EntityTwinserpentHead p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		if(p_115455_.getHeadType() == HeadType.BLASTER)
		{
			this.blasterRenderer.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		}
		else
		{
			super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		}
	}
	
	@Override
	public HierarchicalModel<EntityTwinserpentHead> getModel(EntityTwinserpentHead entity) 
	{
		if(entity.getHeadType() == HeadType.BLASTER)
		{
			return this.blasterRenderer.getModel();
		}
		return this.getModel();
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityTwinserpentHead p_114482_) 
	{
		return p_114482_.isDisabled() ? DISABLED_SLASHER : p_114482_.isDormant() ? DORMANT_SLASHER : TEXTURE_SLASHER;
	}
}
