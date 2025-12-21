package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.entity.model.ModelCorpseAngler;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class CorpseAnglerRenderer extends MobRenderer<EntityCorpseAngler, ModelCorpseAngler>
{
	public CorpseAnglerRenderer(Context pContext)
	{
		super(pContext, new ModelCorpseAngler(pContext.bakeLayer(ModelCorpseAngler.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, this.model, ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/corpse_angler_layer.png")));
	}
	
	@Override
	public void render(EntityCorpseAngler pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		Vec3 pos = BTAClientUtil.getWorldPosition(pEntity, this.model.root(), new Vec3(0, pEntity.yBodyRot, 0), "corpse_angler", "angler", "1", "2", "3", "4", "5", "6", "Bait", "Gnasher");
		pEntity.posArray[0] = pos;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(pEntity.getUUID(), pos, 0));
	}
	
	@Override
	protected void setupRotations(EntityCorpseAngler pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) 
	{
		super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
		if(!pEntityLiving.isInWater()) 
		{
			pPoseStack.translate(1.5F, 0.0F, 0.0F);
			pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityCorpseAngler pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/corpse_angler.png");
	}
}
