package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.deepabyss.BiocrafterBlock;
import com.min01.beyondtheabyss.block.deepabyss.BiocrafterBlock.BiocrafterPart;
import com.min01.beyondtheabyss.block.model.ModelBiocrafter;
import com.min01.beyondtheabyss.blockentity.deepabyss.BiocrafterBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class BiocrafterRenderer implements BlockEntityRenderer<BiocrafterBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/block/biocrafter.png");
    
	private final ModelBiocrafter model;
	
	public BiocrafterRenderer(BlockEntityRendererProvider.Context pContext)
	{
		this.model = new ModelBiocrafter(pContext.bakeLayer(ModelBiocrafter.LAYER_LOCATION));
	}

	@Override
	public void render(BiocrafterBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		BlockState blockState = pBlockEntity.getBlockState();
		if(blockState.getValue(BiocrafterBlock.BIOCRAFTER_PART) == BiocrafterPart.LOWER)
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.01F, 0.0F);
			VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
			this.model.setupAnim(pBlockEntity, 0, 0, pBlockEntity.tickCount + pPartialTick, 0, 0);
			this.model.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			pPoseStack.popPose();
		}
	}
}
