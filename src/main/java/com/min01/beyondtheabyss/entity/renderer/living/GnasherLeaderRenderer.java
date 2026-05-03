package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
import com.min01.beyondtheabyss.entity.model.GnasherLeaderModel;
import com.min01.beyondtheabyss.entity.renderer.layer.GnasherLeaderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GnasherLeaderRenderer extends MobRenderer<GnasherEntity, GnasherLeaderModel>
{
	public GnasherLeaderRenderer(Context pContext)
	{
		super(pContext, new GnasherLeaderModel(pContext.bakeLayer(GnasherLeaderModel.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GnasherLeaderLayer(this, this.model));
	}
	
	@Override
	protected void setupRotations(GnasherEntity pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) 
	{
		super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		if(!pEntityLiving.isInWater()) 
		{
			pPoseStack.translate(0.5F, 0, 0);
			pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(GnasherEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gnasher_leader.png");
	}
}
