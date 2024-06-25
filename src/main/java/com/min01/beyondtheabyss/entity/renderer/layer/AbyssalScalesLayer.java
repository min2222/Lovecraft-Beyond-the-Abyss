package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTAAbilityImpl.BTAAbilities;
import com.min01.beyondtheabyss.util.BTAUtil;
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

public class AbyssalScalesLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    public AbyssalScalesLayer(RenderLayerParent<T, M> renderer) 
    {
        super(renderer);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) 
    {
    	if(BTAUtil.hasAbility(entitylivingbaseIn, BTAAbilities.ABYSSAL_SCALES))
    	{
            VertexConsumer VertexConsumer = bufferIn.getBuffer(RenderType.entityCutout(this.getTexture(entitylivingbaseIn)));
            this.getParentModel().renderToBuffer(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
	public ResourceLocation getTexture(LivingEntity living)
	{
		ResourceLocation location = new ResourceLocation(String.format("%s:textures/entity/abyssal_scale%d.png", BeyondtheAbyss.MODID, BTAUtil.getAbilityTickCount(BTAAbilities.ABYSSAL_SCALES, living)));
		return location;
	}
}
