package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentHead;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentHeadLayer extends GlowingLayer<EntitySiamserpentHead, ModelSiamserpentHead>
{
	private static final ResourceLocation TEXTURE_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_layer.png");
	private static final ResourceLocation TEXTURE_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_layer.png");
	private static final ResourceLocation TEXTURE_BLASTER_LASER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_laser.png");
	
	public SiamserpentHeadLayer(RenderLayerParent<EntitySiamserpentHead, ModelSiamserpentHead> p_117346_, ModelSiamserpentHead model)
	{
		super(p_117346_, model, null);
	}
    
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntitySiamserpentHead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_) 
	{
		if(!entity.isDormant() && !entity.isDisabled())
		{
	    	ResourceLocation texture = entity.getHeadType() == HeadType.SLASHER ? TEXTURE_SLASHER : TEXTURE_BLASTER;
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, 1.0F, 1.0F, 1.0F);
			if(entity.getHeadType() == HeadType.BLASTER)
			{
		    	this.model.root().getChild("SiamserpentBlaster").getChild("RayofEnergy").visible = entity.getAnimationState() == 3;
		    	this.model.root().getChild("SiamserpentBlaster").getChild("InnerRay").visible = entity.getAnimationState() == 3;
				this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, TEXTURE_BLASTER_LASER, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, 0.7F, 0.7F, 0.7F);
			}
		}
	}
}
