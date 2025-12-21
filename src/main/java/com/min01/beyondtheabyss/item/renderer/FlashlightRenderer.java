package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.deepabyss.FlashlightItem;
import com.min01.beyondtheabyss.item.model.ModelFlashlight;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FlashlightRenderer extends BlockEntityWithoutLevelRenderer
{
	private final ModelFlashlight model;
	private static final ResourceLocation TEXTURE_OFF = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/item/flashlight_off_in_hand.png");
	private static final ResourceLocation TEXTURE_ON = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/item/flashlight_on_in_hand.png");
	private static final ResourceLocation TEXTURE_LAYER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/item/flashlight_layer.png");
	
	public FlashlightRenderer() 
	{
		super(BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
		this.model = new ModelFlashlight(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelFlashlight.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
	{
        pPoseStack.pushPose();
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        pPoseStack.translate(0.0F, -1.5F, 0.0F);
        pPoseStack.translate(-0.5F, 0.0F, 0.5F);
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(pBuffer, RenderType.entityCutoutNoCull(this.getTexture(pStack)), false, pStack.hasFoil());
        this.model.renderToBuffer(pPoseStack, consumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        
        if(FlashlightItem.isOn(pStack))
        {
            pPoseStack.pushPose();
            pPoseStack.scale(-1.0F, -1.0F, 1.0F);
            pPoseStack.translate(0.0F, -1.5F, 0.0F);
            pPoseStack.translate(-0.5F, 0.0F, 0.5F);
            VertexConsumer consumer1 = ItemRenderer.getFoilBufferDirect(pBuffer, BTARenderType.eyesFix(TEXTURE_LAYER), false, pStack.hasFoil());
            this.model.renderToBuffer(pPoseStack, consumer1, pPackedLight, pPackedOverlay, 0.5F, 0.5F, 0.5F, 1.0F);
            pPoseStack.popPose();
        }
	}
	
	public ResourceLocation getTexture(ItemStack stack)
	{
		return FlashlightItem.isOn(stack) ? TEXTURE_ON : TEXTURE_OFF;
	}
}
