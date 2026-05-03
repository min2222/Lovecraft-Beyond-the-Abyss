package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.model.SiamserpentSlasherModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentSlasherLayer extends GlowingLayer<SiamserpentHeadEntity, SiamserpentSlasherModel>
{
	private static final ResourceLocation TEXTURE_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_layer.png");
	
	public SiamserpentSlasherLayer(RenderLayerParent<SiamserpentHeadEntity, SiamserpentSlasherModel> renderer, SiamserpentSlasherModel model)
	{
		super(renderer, model, null);
	}
    
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, SiamserpentHeadEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		if(!entity.isDormant() && !entity.isDisabled())
		{
	    	ResourceLocation texture = TEXTURE_SLASHER;
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, poseStack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, 1.0F, 1.0F, 1.0F);
		}
	}
}
