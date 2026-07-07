package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.entity.model.MutavoreModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MutavoreRenderer extends MobRenderer<MutavoreEntity, MutavoreModel>
{
	public MutavoreRenderer(Context pContext)
	{
		super(pContext, new MutavoreModel(pContext.bakeLayer(MutavoreModel.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(MutavoreEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		pEntity.modelPositions.setModelPos(pEntity, this.model.root());
	}

	@Override
	public ResourceLocation getTextureLocation(MutavoreEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
