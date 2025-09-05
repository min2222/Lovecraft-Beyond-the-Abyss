package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasherLeader;
import com.min01.beyondtheabyss.entity.renderer.layer.GnasherLeaderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GnasherLeaderRenderer extends MobRenderer<EntityGnasher, ModelGnasherLeader>
{
	public GnasherLeaderRenderer(Context p_174008_)
	{
		super(p_174008_, new ModelGnasherLeader(p_174008_.bakeLayer(ModelGnasherLeader.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GnasherLeaderLayer(this, this.model));
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
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_leader.png");
	}
}
