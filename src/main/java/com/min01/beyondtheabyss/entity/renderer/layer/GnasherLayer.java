package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class GnasherLayer extends GlowingLayer<EntityGnasher, ModelGnasher>
{
	private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer.png");
	private static final ResourceLocation LAYER_TEXTURE_LEADER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer_leader.png");
	
	public GnasherLayer(RenderLayerParent<EntityGnasher, ModelGnasher> renderer, ModelGnasher model) 
	{
		super(renderer, model, null);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityGnasher entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_) 
	{
    	ResourceLocation texture = entity.isLeader() ? LAYER_TEXTURE_LEADER : LAYER_TEXTURE;
		this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, 1.0F, 1.0F, 1.0F);
	}
}
