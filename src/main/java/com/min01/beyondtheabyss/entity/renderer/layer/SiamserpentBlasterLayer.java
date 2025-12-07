package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBlaster;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBlasterLayer extends GlowingLayer<EntitySiamserpentHead, ModelSiamserpentBlaster>
{
	private static final ResourceLocation TEXTURE_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_layer.png");
	private static final ResourceLocation TEXTURE_BLASTER_LASER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_laser.png");
	
	public SiamserpentBlasterLayer(RenderLayerParent<EntitySiamserpentHead, ModelSiamserpentBlaster> p_117346_, ModelSiamserpentBlaster model)
	{
		super(p_117346_, model, null);
	}
    
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, EntitySiamserpentHead entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		if(!entity.isDormant() && !entity.isDisabled())
		{
	    	ResourceLocation texture = TEXTURE_BLASTER;
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, 1.0F, 1.0F, 1.0F);
	    	this.model.root().getChild("SiamserpentBlaster").getChild("RayofEnergy").visible = entity.getAnimationState() == 3;
	    	this.model.root().getChild("SiamserpentBlaster").getChild("InnerRay").visible = entity.getAnimationState() == 3;
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, TEXTURE_BLASTER_LASER, poseStack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, 0.7F, 0.7F, 0.7F);
		}
	}
}
