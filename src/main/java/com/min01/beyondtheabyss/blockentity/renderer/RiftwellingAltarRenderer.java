package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.model.ModelRiftwellingAltar;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RiftwellingAltarRenderer implements BlockEntityRenderer<RiftwellingAltarBlockEntity>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/riftwelling_altar.png");
    private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/riftwelling_altar_layer.png");
    
	private final ModelRiftwellingAltar model;
	
	public RiftwellingAltarRenderer(BlockEntityRendererProvider.Context p_172550_)
	{
		this.model = new ModelRiftwellingAltar(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelRiftwellingAltar.LAYER_LOCATION));
	}

	@Override
	public void render(RiftwellingAltarBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) 
	{
		p_112309_.pushPose();
		p_112309_.translate(0.5F, 0.5F, 0.5F);
		p_112309_.scale(-1.0F, -1.0F, 1.0F);
		p_112309_.translate(0.0F, -1.0F, 0.0F);
		VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
		this.model.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		p_112309_.popPose();

		p_112309_.pushPose();
		p_112309_.translate(0.5F, 0.5F, 0.5F);
		p_112309_.scale(-1.0F, -1.0F, 1.0F);
		p_112309_.translate(0.0F, -1.0F, 0.0F);
		VertexConsumer eyeConsumer = p_112310_.getBuffer(RenderType.eyes(LAYER_TEXTURE));
		this.model.renderToBuffer(p_112309_, eyeConsumer, p_112311_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		p_112309_.popPose();
		
		if(!p_112307_.getItem().isEmpty())
		{
			float tick = BTAClientUtil.MC.player.tickCount + p_112308_;
			p_112309_.pushPose();
			p_112309_.scale(0.5F, 0.5F, 0.5F);
			p_112309_.translate(1.0F, 3.0F, 1.0F);
			p_112309_.translate(0, 0.05F * Mth.sin(tick / 15), 0);
			p_112309_.mulPose(Vector3f.YP.rotationDegrees(tick));
			BTAClientUtil.MC.getItemRenderer().renderStatic(p_112307_.getItem(), ItemTransforms.TransformType.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, p_112309_, p_112310_, 0);
			p_112309_.popPose();
		}
	}
}
