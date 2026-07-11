package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.LithoshrimpEntity;
import com.min01.beyondtheabyss.entity.model.LithoshrimpModel;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LithoshrimpRenderer extends MobRenderer<LithoshrimpEntity, LithoshrimpModel>
{
	public LithoshrimpRenderer(Context pContext) 
	{
		super(pContext, new LithoshrimpModel(pContext.bakeLayer(LithoshrimpModel.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(LithoshrimpEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}
	
	@Override
	public ResourceLocation getTextureLocation(LithoshrimpEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/lithoshrimp.png");
	}
}
