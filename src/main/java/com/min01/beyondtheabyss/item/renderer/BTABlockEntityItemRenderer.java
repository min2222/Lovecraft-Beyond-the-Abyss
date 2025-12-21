package com.min01.beyondtheabyss.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BTABlockEntityItemRenderer extends BlockEntityWithoutLevelRenderer
{
	private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
	private final BlockEntity blockEntity;
	   
	public BTABlockEntityItemRenderer(BlockEntity blockEntity, BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet)
	{
		super(dispatcher, modelSet);
		this.blockEntityRenderDispatcher = dispatcher;
		this.blockEntity = blockEntity;
	}
	
	@Override
	public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
	{
		pPoseStack.pushPose();
		this.blockEntityRenderDispatcher.renderItem(this.blockEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		pPoseStack.popPose();
	}
}
