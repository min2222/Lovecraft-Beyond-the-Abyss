package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.mirroredcity.OverseerEntity;
import com.min01.beyondtheabyss.entity.model.OverseerModel;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OverseerRenderer extends MobRenderer<OverseerEntity, OverseerModel>
{
	public OverseerRenderer(Context pContext)
	{
		super(pContext, new OverseerModel(pContext.bakeLayer(OverseerModel.LAYER_LOCATION)), 0.0F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/overseer_layer.png")));
	}
	
	@Override
	public void render(OverseerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}

	@Override
	public ResourceLocation getTextureLocation(OverseerEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/overseer.png");
	}
}
