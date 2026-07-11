package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusHeadEntity;
import com.min01.beyondtheabyss.entity.model.ForneusHeadModel;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ForneusHeadRenderer extends MobRenderer<ForneusHeadEntity, ForneusHeadModel>
{
	public ForneusHeadRenderer(Context pContext)
	{
		super(pContext, new ForneusHeadModel(pContext.bakeLayer(ForneusHeadModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	protected void scale(ForneusHeadEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime)
	{
		pPoseStack.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	protected float getFlipDegrees(ForneusHeadEntity pLivingEntity) 
	{
		return 0.0F;
	}
	
	@Override
	public void render(ForneusHeadEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}

	@Override
	public ResourceLocation getTextureLocation(ForneusHeadEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/forneus.png");
	}
}
