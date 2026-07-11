package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerTailEntity;
import com.min01.beyondtheabyss.entity.model.DuneDevourerTailModel;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuneDevourerTailRenderer extends MobRenderer<DuneDevourerTailEntity, DuneDevourerTailModel>
{
	public DuneDevourerTailRenderer(Context pContext)
	{
		super(pContext, new DuneDevourerTailModel(pContext.bakeLayer(DuneDevourerTailModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected float getFlipDegrees(DuneDevourerTailEntity pLivingEntity) 
	{
		return 0.0F;
	}

	@Override
	public void render(DuneDevourerTailEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}
	
	@Override
	public ResourceLocation getTextureLocation(DuneDevourerTailEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/dune_devourer.png");
	}
}
