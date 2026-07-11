package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentBoneEntity;
import com.min01.beyondtheabyss.entity.model.SiamserpentMiddleBoneModel;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentMiddleBoneLayer;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentMiddleBoneRenderer extends MobRenderer<SiamserpentBoneEntity, SiamserpentMiddleBoneModel>
{
	private final SiamserpentBoneRenderer boneRenderer;
	
	public SiamserpentMiddleBoneRenderer(Context pContext)
	{
		super(pContext, new SiamserpentMiddleBoneModel(pContext.bakeLayer(SiamserpentMiddleBoneModel.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentMiddleBoneLayer(this, this.model));
		this.boneRenderer = new SiamserpentBoneRenderer(pContext);
	}
	
	@Override
	public void render(SiamserpentBoneEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.getVariant() == 0 || pEntity.getVariant() == 1)
		{
			this.boneRenderer.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
		else
		{
			super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
			EntityPartBuilder builder = pEntity.partBuilder;
			builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(SiamserpentBoneEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone.png");
	}
}
