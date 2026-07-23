package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ChainTrapMawEntity;
import com.min01.beyondtheabyss.entity.model.ChainTrapChainModel;
import com.min01.beyondtheabyss.entity.model.ChainTrapMawModel;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ChainTrapMawRenderer extends EntityRenderer<ChainTrapMawEntity>
{
	public final ChainTrapMawModel model;
	public final ChainTrapChainModel chainModel;
	public ChainTrapMawRenderer(Context pContext) 
	{
		super(pContext);
		this.model = new ChainTrapMawModel(pContext.bakeLayer(ChainTrapMawModel.LAYER_LOCATION));
		this.chainModel = new ChainTrapChainModel(pContext.bakeLayer(ChainTrapChainModel.LAYER_LOCATION));
	}
	
	@Override
	public void render(ChainTrapMawEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		if(pEntity.chain != null)
		{
			for(int i = 0; i < pEntity.chain.getSegments().size(); i++)
			{
				ChainSegment segment = pEntity.chain.getSegments().get(i);
				Vec3 pos = segment.position(pPartialTick).subtract(pEntity.position());
				Vec2 rot = segment.getRot(pPartialTick);
				pPoseStack.pushPose();
				pPoseStack.scale(-1.0F, -1.0F, 1.0F);
				pPoseStack.translate(-pos.x, -pos.y, pos.z);
				pPoseStack.mulPose(Axis.YP.rotationDegrees(rot.y));
				pPoseStack.mulPose(Axis.XP.rotationDegrees(-rot.x - 90.0F));
				pPoseStack.translate(0, -1.5F, 0);
				if(i == pEntity.chain.getSegments().size() - 1)
				{
					this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, 0, 0);
					this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				}
				else
				{
					this.chainModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/chain_trap_chain.png"))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				}
				pPoseStack.popPose();
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(ChainTrapMawEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/chain_trap_maw.png");
	}
}
