package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBone;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBoneLayer extends GlowingLayer<EntitySiamserpentBone, ModelSiamserpentBone>
{
	public SiamserpentBoneLayer(RenderLayerParent<EntitySiamserpentBone, ModelSiamserpentBone> p_117346_, ModelSiamserpentBone model)
	{
		super(p_117346_, model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone_layer.png"));
	}
    
	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntitySiamserpentBone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_) 
	{
		if(entity.getVariant() == 2)
		{
			this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, this.texture, poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, p_117358_, 0.2F, 0.2F, 0.2F);
		}
	}
}
