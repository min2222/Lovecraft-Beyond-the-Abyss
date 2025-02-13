package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.model.ModelSkeletalRailgunblade;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
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
	public static final ResourceLocation RAILGUNBLADE_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/skeletal_railgunblade.png");
	public static final ResourceLocation RAILGUNBLADE_LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/skeletal_railgunblade_layer.png");
	public final ModelSkeletalRailgunblade modelRailgunblade;
	public BTAItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) 
	{
		super(dispatcher, modelSet);
		this.modelRailgunblade = new ModelSkeletalRailgunblade(modelSet.bakeLayer(ModelSkeletalRailgunblade.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack p_108830_, TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_)
	{
		if(p_108830_.getItem() == BTAItems.SKELETAL_RAILGUNBLADE.get())
		{
	        p_108832_.pushPose();
	        p_108832_.translate(0.2F, -0.05F, 0.25F);
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        VertexConsumer eyeConsumer = p_108833_.getBuffer(BTARenderType.eyesFix(RAILGUNBLADE_LAYER));
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(RAILGUNBLADE_TEXTURE), false, p_108830_.hasFoil());
			this.modelRailgunblade.setupAnim(p_108830_, 0, 0, BTAClientUtil.MC.player.tickCount + BTAClientUtil.MC.getFrameTime(), 0, 0);
	        this.modelRailgunblade.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        this.modelRailgunblade.renderToBuffer(p_108832_, eyeConsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();
		}
	}
}
