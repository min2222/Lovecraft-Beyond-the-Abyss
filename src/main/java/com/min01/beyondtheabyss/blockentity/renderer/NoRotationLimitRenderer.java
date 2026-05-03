package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.AbstractMultiPartSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.AbstractMultiPartSkeletonBlock.SkeletonPart;
import com.min01.beyondtheabyss.block.model.BoneLeverModel;
import com.min01.beyondtheabyss.block.model.BoneLeverOnModel;
import com.min01.beyondtheabyss.block.model.BonePilesModel;
import com.min01.beyondtheabyss.block.model.BoneTorchModel;
import com.min01.beyondtheabyss.block.model.BoneWallTorchModel;
import com.min01.beyondtheabyss.block.model.FallenSkeletonModel;
import com.min01.beyondtheabyss.block.model.FangSkullModel;
import com.min01.beyondtheabyss.block.model.LargeSkullModel;
import com.min01.beyondtheabyss.block.model.SittingSkeletonModel;
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
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class NoRotationLimitRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>
{
    private static final ResourceLocation FANG_SKULL_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/fang_skull.png");
    private static final ResourceLocation LARGE_SKULL_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/large_skull.png");
    private static final ResourceLocation BONE_PILES_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/bone_piles.png");
    private static final ResourceLocation SITTING_SKELETON_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/sitting_skeleton.png");
    private static final ResourceLocation FALLEN_SKELETON_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/fallen_skeleton.png");
    private static final ResourceLocation BONE_TORCH_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/bone_torch.png");
    private static final ResourceLocation BONE_LEVER_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/bone_lever.png");
    
    private final FangSkullModel fangSkullModel;
    private final LargeSkullModel largeSkullModel;
    private final BonePilesModel bonePilesModel;
    private final SittingSkeletonModel sittingSkeletonModel;
    private final FallenSkeletonModel fallenSkeletonModel;
    private final BoneTorchModel boneTorchModel;
    private final BoneWallTorchModel boneWallTorchModel;
    private final BoneLeverModel boneLeverModel;
    private final BoneLeverOnModel boneLeverOnModel;
    
	public NoRotationLimitRenderer(BlockEntityRendererProvider.Context pContext)
	{
		this.fangSkullModel = new FangSkullModel(pContext.bakeLayer(FangSkullModel.LAYER_LOCATION));
		this.largeSkullModel = new LargeSkullModel(pContext.bakeLayer(LargeSkullModel.LAYER_LOCATION));
		this.bonePilesModel = new BonePilesModel(pContext.bakeLayer(BonePilesModel.LAYER_LOCATION));
		this.sittingSkeletonModel = new SittingSkeletonModel(pContext.bakeLayer(SittingSkeletonModel.LAYER_LOCATION));
		this.fallenSkeletonModel = new FallenSkeletonModel(pContext.bakeLayer(FallenSkeletonModel.LAYER_LOCATION));
		this.boneTorchModel = new BoneTorchModel(pContext.bakeLayer(BoneTorchModel.LAYER_LOCATION));
		this.boneWallTorchModel = new BoneWallTorchModel(pContext.bakeLayer(BoneWallTorchModel.LAYER_LOCATION));
		this.boneLeverModel = new BoneLeverModel(pContext.bakeLayer(BoneLeverModel.LAYER_LOCATION));
		this.boneLeverOnModel = new BoneLeverOnModel(pContext.bakeLayer(BoneLeverOnModel.LAYER_LOCATION));
	}
	
	@Override
	public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		BlockState blockState = pBlockEntity.getBlockState();
		if(blockState.is(BTABlocks.FANG_SKULL.get()))
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.0F, 0.0F);
			this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), pPoseStack);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(FANG_SKULL_TEXTURE));
			this.fangSkullModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
		else if(blockState.is(BTABlocks.LARGE_SKULL.get()))
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.0F, 0.0F);
			this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), pPoseStack);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(LARGE_SKULL_TEXTURE));
			this.largeSkullModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
		else if(blockState.is(BTABlocks.BONE_PILES.get()))
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.0F, 0.0F);
			this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), pPoseStack);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(BONE_PILES_TEXTURE));
			this.bonePilesModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
		else if(blockState.is(BTABlocks.SITTING_SKELETON.get()))
		{
			if(blockState.getValue(AbstractMultiPartSkeletonBlock.SKELETON_PART) == SkeletonPart.LOWER)
			{
				pPoseStack.pushPose();
				pPoseStack.translate(0.5F, 0.5F, 0.5F);
				pPoseStack.scale(-1.0F, -1.0F, 1.0F);
				pPoseStack.translate(0.0F, -1.0F, 0.0F);
				this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), pPoseStack);
				VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(SITTING_SKELETON_TEXTURE));
				this.sittingSkeletonModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
				pPoseStack.popPose();
			}
		}
		else if(blockState.is(BTABlocks.FALLEN_SKELETON.get()))
		{
			if(blockState.getValue(AbstractMultiPartSkeletonBlock.SKELETON_PART) == SkeletonPart.LOWER)
			{
				pPoseStack.pushPose();
				pPoseStack.translate(0.5F, 0.5F, 0.5F);
				pPoseStack.scale(-1.0F, -1.0F, 1.0F);
				pPoseStack.translate(0.0F, -1.0F, 0.0F);
				this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), pPoseStack);
				VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(FALLEN_SKELETON_TEXTURE));
				this.fallenSkeletonModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
				pPoseStack.popPose();
			}
		}
		else if(blockState.is(BTABlocks.BONE_TORCH.get()))
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.0F, 0.0F);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(BONE_TORCH_TEXTURE));
			this.boneTorchModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
		else if(blockState.is(BTABlocks.BONE_WALL_TORCH.get()))
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.0F, 0.0F);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(BONE_TORCH_TEXTURE));
			this.rotate(blockState.getValue(WallTorchBlock.FACING), pPoseStack);
			this.boneWallTorchModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
		else if(blockState.is(BTABlocks.BONE_LEVER.get()))
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			this.rotate(blockState.getValue(LeverBlock.FACE), blockState.getValue(LeverBlock.FACING), pPoseStack);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.0F, 0.0F);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(BONE_LEVER_TEXTURE));
			if(blockState.getValue(LeverBlock.POWERED))
			{
				this.boneLeverOnModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			}
			else
			{
				this.boneLeverModel.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			}
			pPoseStack.popPose();
		}
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
	
	public void rotate(Direction direction, PoseStack poseStack)
	{
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
	}
}
