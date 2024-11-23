package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.renderer.layer.GnasherLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GnasherRenderer extends MobRenderer<EntityGnasher, ModelGnasher>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher.png");
	private static final ResourceLocation TEXTURE_LEADER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_leader.png");
	
	public GnasherRenderer(Context p_174008_)
	{
		super(p_174008_, new ModelGnasher(p_174008_.bakeLayer(ModelGnasher.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GnasherLayer(this, this.model));
	}
	
	@Override
	protected void setupRotations(EntityGnasher p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if(!p_116226_.isInWater()) 
		{
			p_116227_.translate(0.5F, 0, 0);
			p_116227_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGnasher p_114482_) 
	{
		return p_114482_.isLeader() ? TEXTURE_LEADER : TEXTURE;
	}
}
