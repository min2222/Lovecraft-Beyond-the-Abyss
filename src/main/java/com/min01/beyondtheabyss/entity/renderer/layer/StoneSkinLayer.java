package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class StoneSkinLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    public StoneSkinLayer(RenderLayerParent<T, M> renderer) 
    {
        super(renderer);
    }

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float p_117358_)
	{
    	if(entity.hasEffect(BTAEffects.STONE_SKIN.get()))
    	{
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTexture(entity)));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
	public ResourceLocation getTexture(LivingEntity living)
	{
		int tickCount = BTAUtil.getPlayerTickCount(living) / 5;
		if(tickCount >= 25)
		{
			tickCount = 5;
		}
		return new ResourceLocation(String.format("%s:textures/misc/stone_skin%d.png", BeyondtheAbyss.MODID, tickCount));
	}
}