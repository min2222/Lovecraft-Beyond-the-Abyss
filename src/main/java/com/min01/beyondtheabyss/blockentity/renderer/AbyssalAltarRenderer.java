package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.model.ModelAbyssalAltar;
import com.min01.beyondtheabyss.blockentity.deepabyss.BlockEntityAbyssalAltar;
import com.min01.beyondtheabyss.misc.ClientEventHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AbyssalAltarRenderer implements BlockEntityRenderer<BlockEntityAbyssalAltar>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/abyssal_altar.png");
    private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/abyssal_altar_layer.png");
    
	private final ModelAbyssalAltar model;
	
	public AbyssalAltarRenderer(BlockEntityRendererProvider.Context p_172550_)
	{
		this.model = new ModelAbyssalAltar(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalAltar.LAYER_LOCATION));
	}

	@Override
	public void render(BlockEntityAbyssalAltar p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) 
	{
		p_112309_.pushPose();
		p_112309_.translate(0.5D, 0.5D, 0.5D);
		p_112309_.scale(-1, -1, 1);
		p_112309_.translate(0, -1, 0);
		VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
		this.model.renderToBuffer(p_112309_, consumer, LightTexture.FULL_BLOCK, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		p_112309_.popPose();

		p_112309_.pushPose();
		p_112309_.translate(0.5D, 0.5D, 0.5D);
		p_112309_.scale(-1, -1, 1);
		p_112309_.translate(0, -1, 0);
		VertexConsumer layerConsumer = p_112310_.getBuffer(RenderType.eyes(LAYER_TEXTURE));
		this.model.renderToBuffer(p_112309_, layerConsumer, LightTexture.FULL_BLOCK, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		p_112309_.popPose();
		
		if(!p_112307_.getItem().isEmpty())
		{
			float tick = ClientEventHandler.MC.player.tickCount + p_112308_;
			p_112309_.pushPose();
			p_112309_.scale(0.5F, 0.5F, 0.5F);
			p_112309_.translate(1, 3F, 1);
			p_112309_.translate(0, 0.05f * Mth.sin(tick / 15), 0);
			p_112309_.mulPose(Vector3f.YP.rotationDegrees(tick));
			Minecraft.getInstance().getItemRenderer().renderStatic(p_112307_.getItem(), ItemTransforms.TransformType.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, p_112309_, p_112310_, 0);
			p_112309_.popPose();
		}
	}
}
