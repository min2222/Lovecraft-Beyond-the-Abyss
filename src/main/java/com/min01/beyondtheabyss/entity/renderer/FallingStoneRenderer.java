package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.entity.FallingStoneEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.model.data.ModelData;

public class FallingStoneRenderer extends EntityRenderer<FallingStoneEntity>
{
	private final BlockRenderDispatcher dispatcher;
	
	public FallingStoneRenderer(Context pContext)
	{
		super(pContext);
		this.dispatcher = pContext.getBlockRenderDispatcher();
	}
	
	@Override
	public void render(FallingStoneEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		BlockState state = pEntity.getBlockState();
		if(state.getRenderShape() == RenderShape.MODEL) 
		{
			Level level = pEntity.level;
			if(state != level.getBlockState(pEntity.blockPosition()) && state.getRenderShape() != RenderShape.INVISIBLE) 
			{
				pPoseStack.pushPose();
				BlockPos pos = BlockPos.containing(pEntity.getX(), pEntity.getBoundingBox().maxY, pEntity.getZ());
				pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntity.getRotation()));
				pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getRotation()));
				pPoseStack.mulPose(Axis.XP.rotationDegrees(pEntity.getRotation()));
				pPoseStack.translate(-0.5F, 0.0F, -0.5F);
				BakedModel model = this.dispatcher.getBlockModel(state);
				for(RenderType renderType : model.getRenderTypes(state, RandomSource.create(state.getSeed(pEntity.blockPosition())), ModelData.EMPTY))
				{
					renderType = RenderTypeHelper.getMovingBlockRenderType(renderType);
					this.dispatcher.getModelRenderer().tesselateBlock(level, model, state, pos, pPoseStack, pBuffer.getBuffer(renderType), false, RandomSource.create(), state.getSeed(pEntity.blockPosition()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
				}
				pPoseStack.popPose();
			}
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(FallingStoneEntity pEntity)
	{
		return null;
	}
}
