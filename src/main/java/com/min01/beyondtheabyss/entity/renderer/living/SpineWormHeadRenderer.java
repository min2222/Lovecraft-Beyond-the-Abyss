package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SpineWormHeadEntity;
import com.min01.beyondtheabyss.entity.model.SpineWormHeadModel;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpineWormHeadRenderer extends MobRenderer<SpineWormHeadEntity, SpineWormHeadModel>
{
	public SpineWormHeadRenderer(Context pContext)
	{
		super(pContext, new SpineWormHeadModel(pContext.bakeLayer(SpineWormHeadModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(SpineWormHeadEntity pLivingEntity) 
	{
		return 0.0F;
	}
	
	@Override
	public void render(SpineWormHeadEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}

	@Override
	public ResourceLocation getTextureLocation(SpineWormHeadEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/spine_worm_head.png");
	}
}
