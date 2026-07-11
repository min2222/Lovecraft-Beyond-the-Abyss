package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
import com.min01.beyondtheabyss.entity.model.GnasherLeaderModel;
import com.min01.beyondtheabyss.entity.renderer.layer.GnasherLeaderLayer;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.MultiBufferSource;
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
	public void render(GnasherEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}

	@Override
	public ResourceLocation getTextureLocation(GnasherEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/gnasher_leader.png");
	}
}
