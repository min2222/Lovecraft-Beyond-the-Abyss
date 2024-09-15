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
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FlashlightRenderer extends BlockEntityWithoutLevelRenderer
{
	private final ModelFlashlight model;
	private static final ResourceLocation TEXTURE_OFF = new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/flashlight_off_in_hand.png");
	private static final ResourceLocation TEXTURE_ON = new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/flashlight_on_in_hand.png");
	private static final ResourceLocation TEXTURE_LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/flashlight_layer.png");
	
	public FlashlightRenderer() 
	{
		super(BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
		this.model = new ModelFlashlight(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelFlashlight.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack p_108830_, TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_)
	{
        p_108832_.pushPose();
        p_108832_.scale(-1.0F, -1.0F, 1.0F);
        p_108832_.translate(0.0F, -1.5F, 0.0F);
        p_108832_.translate(-0.5F, 0.0F, 0.5F);
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(this.getTexture(p_108830_)), false, p_108830_.hasFoil());
        this.model.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
        p_108832_.popPose();
        
        if(FlashlightItem.isOn(p_108830_))
        {
            p_108832_.pushPose();
            p_108832_.scale(-1.0F, -1.0F, 1.0F);
            p_108832_.translate(0.0F, -1.5F, 0.0F);
            p_108832_.translate(-0.5F, 0.0F, 0.5F);
            VertexConsumer consumer1 = ItemRenderer.getFoilBufferDirect(p_108833_, BTARenderType.eyesFix(TEXTURE_LAYER), false, p_108830_.hasFoil());
            this.model.renderToBuffer(p_108832_, consumer1, p_108834_, p_108835_, 0.5F, 0.5F, 0.5F, 1.0F);
            p_108832_.popPose();
        }
	}
	
	public ResourceLocation getTexture(ItemStack stack)
	{
		return FlashlightItem.isOn(stack) ? TEXTURE_ON : TEXTURE_OFF;
	}
}
