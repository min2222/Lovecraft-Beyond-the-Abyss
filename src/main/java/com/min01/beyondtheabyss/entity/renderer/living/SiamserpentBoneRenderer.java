package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentBoneEntity;
import com.min01.beyondtheabyss.entity.model.SiamserpentBoneModel;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentBoneRenderer extends MobRenderer<SiamserpentBoneEntity, SiamserpentBoneModel>
{
	public SiamserpentBoneRenderer(Context pContext)
	{
		super(pContext, new SiamserpentBoneModel(pContext.bakeLayer(SiamserpentBoneModel.LAYER_LOCATION)), 0.0F);
	}
	
	@Override
	public void render(SiamserpentBoneEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}
	
	@Override
	public ResourceLocation getTextureLocation(SiamserpentBoneEntity pEntity) 
	{
		switch(pEntity.getVariant())
		{
		case 0:
			return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_fins.png");
		case 1:
			return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_arms.png");
		}
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_fins.png");
	}
}
