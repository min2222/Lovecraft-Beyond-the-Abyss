package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.entity.model.MutavoreModel;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

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
		
		Vec3 minePos = BTAClientUtil.getWorldPosition(pEntity, this.model.root(), new Vec3(0, pEntity.yBodyRot, 0), new String[] {"body", "mound", "chunk3", "mine"});
		Vec3 minePos2 = BTAClientUtil.getWorldPosition(pEntity, this.model.root(), new Vec3(0, pEntity.yBodyRot, 0), new String[] {"body", "mound", "chunk2", "mine2"});
		Vec3 minePos3 = BTAClientUtil.getWorldPosition(pEntity, this.model.root(), new Vec3(0, pEntity.yBodyRot, 0), new String[] {"body", "mound", "chunk2", "mine3"});
		Vec3 minePos4 = BTAClientUtil.getWorldPosition(pEntity, this.model.root(), new Vec3(0, pEntity.yBodyRot, 0), new String[] {"body", "mound", "chunk3", "mine4"});
		pEntity.posArray[0] = minePos;
		pEntity.posArray[1] = minePos2;
		pEntity.posArray[2] = minePos3;
		pEntity.posArray[3] = minePos4;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(pEntity.getUUID(), minePos, 0));
		BTANetwork.sendToServer(new UpdatePosArrayPacket(pEntity.getUUID(), minePos2, 1));
		BTANetwork.sendToServer(new UpdatePosArrayPacket(pEntity.getUUID(), minePos3, 2));
		BTANetwork.sendToServer(new UpdatePosArrayPacket(pEntity.getUUID(), minePos4, 3));
	}

	@Override
	public ResourceLocation getTextureLocation(MutavoreEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
