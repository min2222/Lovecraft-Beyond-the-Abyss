package com.min01.beyondtheabyss.entity.renderer.layers;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class LayerGhidruthScaleArmor<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	private int curFrame;
	
    public LayerGhidruthScaleArmor(RenderLayerParent<T, M> renderer) 
    {
        super(renderer);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) 
    {
    	ResourceLocation location = this.getTexture(entitylivingbaseIn);
    	if(location != null && entitylivingbaseIn.hasEffect(BTAEffects.GHIDRUTHS_SCALES.get()))
    	{
            VertexConsumer VertexConsumer = bufferIn.getBuffer(RenderType.entityCutout(location));
            this.getParentModel().renderToBuffer(matrixStackIn, VertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    	}
    	
    	if(!entitylivingbaseIn.hasEffect(BTAEffects.GHIDRUTHS_SCALES.get()))
    	{
    		this.curFrame = 0;
    	}
    }
    
	public ResourceLocation getTexture(LivingEntity living)
	{
		if(this.curFrame < 5 && living.tickCount % 7F == 0)
		{
			this.curFrame++;
		}
		ResourceLocation location = new ResourceLocation(String.format("%s:textures/entity/ghidruth_scale_armor_%d.png", BeyondtheAbyss.MODID, this.curFrame));
		return location;
	}
}
