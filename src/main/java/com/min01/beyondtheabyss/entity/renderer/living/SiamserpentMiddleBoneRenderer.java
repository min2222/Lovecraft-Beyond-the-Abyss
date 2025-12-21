package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IMultiModel;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentMiddleBone;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentMiddleBoneLayer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentMiddleBoneRenderer extends MobRenderer<EntitySiamserpentBone, ModelSiamserpentMiddleBone> implements IMultiModel<EntitySiamserpentBone>
{
	private final SiamserpentBoneRenderer boneRenderer;
	
	public SiamserpentMiddleBoneRenderer(Context pContext)
	{
		super(pContext, new ModelSiamserpentMiddleBone(pContext.bakeLayer(ModelSiamserpentMiddleBone.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentMiddleBoneLayer(this, this.model));
		this.boneRenderer = new SiamserpentBoneRenderer(pContext);
	}
	
	@Override
	public void render(EntitySiamserpentBone pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.getVariant() == 0 || pEntity.getVariant() == 1)
		{
			this.boneRenderer.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
		else
		{
			super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
	}
	
	@Override
	public HierarchicalModel<EntitySiamserpentBone> getModel(EntitySiamserpentBone entity) 
	{
		if(entity.getVariant() == 0 || entity.getVariant() == 1)
		{
			return this.boneRenderer.getModel();
		}
		return this.getModel();
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentBone pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone.png");
	}
}
