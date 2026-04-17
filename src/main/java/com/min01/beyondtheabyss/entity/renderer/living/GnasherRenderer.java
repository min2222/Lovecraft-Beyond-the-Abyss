package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.renderer.layer.GnasherLayer;
import com.min01.solomonlib.multipart.IMultiModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GnasherRenderer extends MobRenderer<EntityGnasher, ModelGnasher> implements IMultiModel<EntityGnasher>
{
	private final GnasherLeaderRenderer leaderRenderer;
	
	public GnasherRenderer(Context pContext)
	{
		super(pContext, new ModelGnasher(pContext.bakeLayer(ModelGnasher.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GnasherLayer(this, this.model));
		this.leaderRenderer = new GnasherLeaderRenderer(pContext);
	}
	
	@Override
	public void render(EntityGnasher pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.isLeader())
		{
			this.leaderRenderer.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
		else
		{
			super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
	}
	
	@Override
	public HierarchicalModel<EntityGnasher> getModel(EntityGnasher entity) 
	{
		if(entity.isLeader())
		{
			return this.leaderRenderer.getModel();
		}
		return this.getModel();
	}
	
	@Override
	protected void setupRotations(EntityGnasher pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) 
	{
		super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		if(!pEntityLiving.isInWater()) 
		{
			pPoseStack.translate(0.5F, 0, 0);
			pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGnasher pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gnasher.png");
	}
}
