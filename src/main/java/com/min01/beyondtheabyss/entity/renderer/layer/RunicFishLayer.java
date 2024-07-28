package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.model.ModelRunicFish;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class RunicFishLayer extends GlowingLayer<EntityRunicFish, ModelRunicFish>
{
	public RunicFishLayer(RenderLayerParent<EntityRunicFish, ModelRunicFish> p_117346_, ModelRunicFish model)
	{
		super(p_117346_, model, null);
	}
    
	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EntityRunicFish p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_)
	{
    	ResourceLocation texture = new ResourceLocation(String.format("%s:textures/entity/runic_fish%d_layer.png", BeyondtheAbyss.MODID, p_117352_.getVariant()));
		this.coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, p_117349_, p_117350_, p_117351_, p_117352_, p_117353_, p_117354_, p_117356_, p_117357_, p_117358_, p_117355_, 1.0F, 1.0F, 1.0F);
	}
}
