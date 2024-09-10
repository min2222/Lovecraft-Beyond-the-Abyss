package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.block.deepabyss.AbstractMultiPartSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.AbstractMultiPartSkeletonBlock.SkeletonPart;
import com.min01.beyondtheabyss.block.model.ModelBonePiles;
import com.min01.beyondtheabyss.block.model.ModelFallenSkeleton;
import com.min01.beyondtheabyss.block.model.ModelFangSkull;
import com.min01.beyondtheabyss.block.model.ModelLargeSkull;
import com.min01.beyondtheabyss.block.model.ModelSittingSkeleton;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class NoRotationLimitRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>
{
    private static final ResourceLocation FANG_SKULL_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/fang_skull.png");
    private static final ResourceLocation LARGE_SKULL_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/large_skull.png");
    private static final ResourceLocation BONE_PILES_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/bone_piles.png");
    private static final ResourceLocation SITTING_SKELETON_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/sitting_skeleton.png");
    private static final ResourceLocation FALLEN_SKELETON_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/fallen_skeleton.png");
    
    private final ModelFangSkull fangSkullModel;
    private final ModelLargeSkull largeSkullModel;
    private final ModelBonePiles bonePilesModel;
    private final ModelSittingSkeleton sittingSkeletonModel;
    private final ModelFallenSkeleton fallenSkeletonModel;
    
	public NoRotationLimitRenderer(BlockEntityRendererProvider.Context p_172550_)
	{
		this.fangSkullModel = new ModelFangSkull(p_172550_.bakeLayer(ModelFangSkull.LAYER_LOCATION));
		this.largeSkullModel = new ModelLargeSkull(p_172550_.bakeLayer(ModelLargeSkull.LAYER_LOCATION));
		this.bonePilesModel = new ModelBonePiles(p_172550_.bakeLayer(ModelBonePiles.LAYER_LOCATION));
		this.sittingSkeletonModel = new ModelSittingSkeleton(p_172550_.bakeLayer(ModelSittingSkeleton.LAYER_LOCATION));
		this.fallenSkeletonModel = new ModelFallenSkeleton(p_172550_.bakeLayer(ModelFallenSkeleton.LAYER_LOCATION));
	}
	
	@Override
	public void render(T p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) 
	{
		BlockState blockState = p_112307_.getBlockState();
		if(blockState.is(BTABlocks.FANG_SKULL.get()))
		{
			p_112309_.pushPose();
			p_112309_.translate(0.5D, 0.5D, 0.5D);
			p_112309_.scale(-1, -1, 1);
			p_112309_.translate(0, -1, 0);
			this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), p_112309_);
			VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(FANG_SKULL_TEXTURE));
			this.fangSkullModel.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			p_112309_.popPose();
		}
		else if(blockState.is(BTABlocks.LARGE_SKULL.get()))
		{
			p_112309_.pushPose();
			p_112309_.translate(0.5D, 0.5D, 0.5D);
			p_112309_.scale(-1, -1, 1);
			p_112309_.translate(0, -1, 0);
			this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), p_112309_);
			VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(LARGE_SKULL_TEXTURE));
			this.largeSkullModel.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			p_112309_.popPose();
		}
		else if(blockState.is(BTABlocks.BONE_PILES.get()))
		{
			p_112309_.pushPose();
			p_112309_.translate(0.5D, 0.5D, 0.5D);
			p_112309_.scale(-1, -1, 1);
			p_112309_.translate(0, -1, 0);
			this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), p_112309_);
			VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(BONE_PILES_TEXTURE));
			this.bonePilesModel.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			p_112309_.popPose();
		}
		else if(blockState.is(BTABlocks.SITTING_SKELETON.get()))
		{
			if(blockState.getValue(AbstractMultiPartSkeletonBlock.SKELETON_PART) == SkeletonPart.LOWER)
			{
				p_112309_.pushPose();
				p_112309_.translate(0.5D, 0.5D, 0.5D);
				p_112309_.scale(-1, -1, 1);
				p_112309_.translate(0, -1, 0);
				this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), p_112309_);
				VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(SITTING_SKELETON_TEXTURE));
				this.sittingSkeletonModel.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
				p_112309_.popPose();
			}
		}
		else if(blockState.is(BTABlocks.FALLEN_SKELETON.get()))
		{
			if(blockState.getValue(AbstractMultiPartSkeletonBlock.SKELETON_PART) == SkeletonPart.LOWER)
			{
				p_112309_.pushPose();
				p_112309_.translate(0.5D, 0.5D, 0.5D);
				p_112309_.scale(-1, -1, 1);
				p_112309_.translate(0, -1, 0);
				this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), p_112309_);
				VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(FALLEN_SKELETON_TEXTURE));
				this.fallenSkeletonModel.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
				p_112309_.popPose();
			}
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
