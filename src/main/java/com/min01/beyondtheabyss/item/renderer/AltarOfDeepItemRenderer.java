package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityAltarOfDeep;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public class AltarOfDeepItemRenderer extends BlockEntityWithoutLevelRenderer
{
	private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
	   
	public AltarOfDeepItemRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_)
	{
		super(p_172550_, p_172551_);
		this.blockEntityRenderDispatcher = p_172550_;
	}
	
	@Override
	public void renderByItem(ItemStack p_108830_, TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_)
	{
		p_108832_.pushPose();
		this.blockEntityRenderDispatcher.renderItem(new BlockEntityAltarOfDeep(BlockPos.ZERO, BTABlocks.ALTAR_OF_DEEP.get().defaultBlockState()), p_108832_, p_108833_, LightTexture.FULL_BLOCK, OverlayTexture.NO_OVERLAY);
		p_108832_.popPose();
	}
}
