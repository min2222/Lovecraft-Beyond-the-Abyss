package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
import com.min01.beyondtheabyss.entity.model.ModelPhasmozoa;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;

public class PhasmozoaLayer extends GlowingLayer<EntityPhasmozoa, ModelPhasmozoa>
{
	public PhasmozoaLayer(RenderLayerParent<EntityPhasmozoa, ModelPhasmozoa> p_117346_, ModelPhasmozoa model) 
	{
		super(p_117346_, model, null);
	}
	
	@Override
	public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, EntityPhasmozoa p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_)
	{
    	ResourceLocation texture = new ResourceLocation(String.format("%s:textures/entity/phasmozoa%d_layer.png", BeyondtheAbyss.MODID, p_117352_.getVariant()));
		coloredGlowingModelCopyLayerRender(this.getParentModel(), this.model, texture, p_117349_, p_117350_, p_117351_, p_117352_, p_117353_, p_117354_, p_117356_, p_117357_, p_117358_, p_117355_, 1, 1, 1);
	}
	
	protected static void coloredGlowingModelCopyLayerRender(ModelPhasmozoa p_117360_, ModelPhasmozoa p_117361_, ResourceLocation p_117362_, PoseStack p_117363_, MultiBufferSource p_117364_, int p_117365_, EntityPhasmozoa p_117366_, float p_117367_, float p_117368_, float p_117369_, float p_117370_, float p_117371_, float p_117372_, float p_117373_, float p_117374_, float p_117375_)
	{
		if(!p_117366_.isInvisible())
		{
			p_117360_.copyPropertiesTo(p_117361_);
			p_117361_.prepareMobModel(p_117366_, p_117367_, p_117368_, p_117372_);
			p_117361_.setupAnim(p_117366_, p_117367_, p_117368_, p_117369_, p_117370_, p_117371_);
			renderColoredGlowingModel(p_117361_, p_117362_, p_117363_, p_117364_, p_117365_, p_117366_, p_117373_, p_117374_, p_117375_);
		}
	}

	protected static void renderColoredGlowingModel(ModelPhasmozoa p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, EntityPhasmozoa p_117382_, float p_117383_, float p_117384_, float p_117385_)
	{
		VertexConsumer vertexconsumer = p_117380_.getBuffer(BTARenderType.entityTranslucentColorWrite(p_117378_));
		p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117382_.isSpectre() ? LightTexture.FULL_BLOCK : p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), p_117383_, p_117384_, p_117385_, p_117382_.getSpectreAlpha());
	}
}
