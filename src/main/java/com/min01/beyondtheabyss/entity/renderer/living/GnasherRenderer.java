package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IMultiModel;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.renderer.layer.GnasherLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GnasherRenderer extends MobRenderer<EntityGnasher, ModelGnasher> implements IMultiModel<EntityGnasher>
{
	private final GnasherLeaderRenderer leaderRenderer;
	
	public GnasherRenderer(Context p_174008_)
	{
		super(p_174008_, new ModelGnasher(p_174008_.bakeLayer(ModelGnasher.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GnasherLayer(this, this.model));
		this.leaderRenderer = new GnasherLeaderRenderer(p_174008_);
	}
	
	@Override
	public void render(EntityGnasher p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		if(p_115455_.isLeader())
		{
			this.leaderRenderer.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		}
		else
		{
			super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		}
	}
	
	@Override
	public HierarchicalModel<EntityGnasher> getModel(EntityGnasher entity) 
	{
		if(entity.isLeader())
		{
			return this.leaderRenderer.getModel();
		}
		return this.getModel();
	}
	
	@Override
	protected void setupRotations(EntityGnasher p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if(!p_116226_.isInWater()) 
		{
			p_116227_.translate(0.5F, 0, 0);
			p_116227_.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGnasher p_114482_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher.png");
	}
}
