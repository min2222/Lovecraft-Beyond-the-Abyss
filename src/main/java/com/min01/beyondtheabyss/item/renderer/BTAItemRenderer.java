package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.model.ModelBlasterSkull;
import com.min01.beyondtheabyss.item.model.ModelSerpentHeart;
import com.min01.beyondtheabyss.item.model.ModelSlasherSkull;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class BTAItemRenderer extends BlockEntityWithoutLevelRenderer
{
	private final ModelSlasherSkull modelSlasherSkull;
	private final ModelBlasterSkull modelBlasterSkull;
	private final ModelSerpentHeart modelSerpentHeart;
	
	public BTAItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) 
	{
		super(dispatcher, modelSet);
		this.modelSlasherSkull = new ModelSlasherSkull(modelSet.bakeLayer(ModelSlasherSkull.LAYER_LOCATION));
		this.modelBlasterSkull = new ModelBlasterSkull(modelSet.bakeLayer(ModelBlasterSkull.LAYER_LOCATION));
		this.modelSerpentHeart = new ModelSerpentHeart(modelSet.bakeLayer(ModelSerpentHeart.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack p_108830_, TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_)
	{
		if(p_108830_.getItem() == BTAItems.SLASHER_SKULL.get())
		{
	        p_108832_.pushPose();
	        p_108832_.translate(0.5F, 0.5F, 0.5F);
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/slasher_skull.png")), false, p_108830_.hasFoil());
	        this.modelSlasherSkull.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();
		}
		if(p_108830_.getItem() == BTAItems.BLASTER_SKULL.get())
		{
	        p_108832_.pushPose();
	        p_108832_.translate(0.5F, 0.5F, 0.5F);
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/blaster_skull.png")), false, p_108830_.hasFoil());
	        this.modelBlasterSkull.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();
		}
		if(p_108830_.getItem() == BTAItems.SERPENT_HEART.get())
		{
	        p_108832_.pushPose();
	        p_108832_.translate(0.5F, 0.5F, 0.5F);
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/serpent_heart.png")), false, p_108830_.hasFoil());
	        this.modelSerpentHeart.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();
		}
	}
}
