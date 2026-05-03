package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.ChainTrapBlock;
import com.min01.beyondtheabyss.block.model.ChainTrapModel;
import com.min01.beyondtheabyss.blockentity.deepabyss.ChainTrapBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.Vec3;

public class ChainTrapRenderer implements BlockEntityRenderer<ChainTrapBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/chain_trap_closed.png");
    private static final ResourceLocation TEXTURE_OPENED = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/chain_trap_opened.png");
    
	private final ChainTrapModel model;
	
	public ChainTrapRenderer(BlockEntityRendererProvider.Context pContext)
	{
		this.model = new ChainTrapModel(pContext.bakeLayer(ChainTrapModel.LAYER_LOCATION));
	}

	@Override
	public void render(ChainTrapBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		BlockState blockState = pBlockEntity.getBlockState();
		pPoseStack.pushPose();
		pPoseStack.translate(0.5F, 0.5F, 0.5F);
		this.rotate(blockState.getValue(FaceAttachedHorizontalDirectionalBlock.FACE), blockState.getValue(HorizontalDirectionalBlock.FACING), pPoseStack);
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.0F, 0.0F);
		VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(blockState.getValue(ChainTrapBlock.OPENED) ? TEXTURE_OPENED : TEXTURE));
		this.model.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		pPoseStack.popPose();
	}
	
	public void rotate(AttachFace face, Direction direction, PoseStack poseStack)
	{
		switch(face)
		{
		case CEILING:
			switch(direction)
			{
			case DOWN:
				break;
			case EAST:
				poseStack.mulPose(Axis.XP.rotationDegrees(180));
				poseStack.mulPose(Axis.YP.rotationDegrees(90));
				break;
			case NORTH:
				poseStack.mulPose(Axis.XP.rotationDegrees(180));
				break;
			case SOUTH:
				poseStack.mulPose(Axis.XP.rotationDegrees(180));
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
				break;
			case UP:
				break;
			case WEST:
				poseStack.mulPose(Axis.XP.rotationDegrees(180));
				poseStack.mulPose(Axis.YP.rotationDegrees(-90));
				break;
			default:
				break;
			}
			break;
		case FLOOR:
			switch(direction)
			{
			case DOWN:
				break;
			case EAST:
				poseStack.mulPose(Axis.YP.rotationDegrees(90));
				break;
			case NORTH:
				break;
			case SOUTH:
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
				break;
			case UP:
				break;
			case WEST:
				poseStack.mulPose(Axis.YP.rotationDegrees(270));
				break;
			default:
				break;
			}
			break;
		case WALL:
			switch(direction)
			{
			case DOWN:
				break;
			case EAST:
				poseStack.mulPose(Axis.XP.rotationDegrees(90));
				poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
				break;
			case NORTH:
				poseStack.mulPose(Axis.XP.rotationDegrees(-90));
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
				break;
			case SOUTH:
				poseStack.mulPose(Axis.XP.rotationDegrees(90));
				break;
			case UP:
				break;
			case WEST:
				poseStack.mulPose(Axis.XP.rotationDegrees(90));
				poseStack.mulPose(Axis.ZP.rotationDegrees(90));
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean shouldRender(ChainTrapBlockEntity pBlockEntity, Vec3 pCameraPos)
	{
		return true;
	}
	
	@Override
	public boolean shouldRenderOffScreen(ChainTrapBlockEntity pBlockEntity)
	{
		return true;
	}
}
