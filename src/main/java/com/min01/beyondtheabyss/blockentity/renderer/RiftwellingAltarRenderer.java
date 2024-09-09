package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.model.ModelRiftwellingAltar;
import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.util.BTAClientUtil;
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
import net.minecraft.world.level.block.entity.BlockEntity;

public class RiftwellingAltarRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>
{
    private static final ResourceLocation ALTAR_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/riftwelling_altar.png");
    private static final ResourceLocation ALTAR_LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/riftwelling_altar_layer.png");
    
	private final ModelRiftwellingAltar altarModel;
	
	public RiftwellingAltarRenderer(BlockEntityRendererProvider.Context p_172550_)
	{
		this.altarModel = new ModelRiftwellingAltar(Minecraft.getInstance().getEntityModels().bakeLayer(ModelRiftwellingAltar.LAYER_LOCATION));
	}

	@Override
	public void render(T p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) 
	{
		if(p_112307_ instanceof RiftwellingAltarBlockEntity altar)
		{
			p_112309_.pushPose();
			p_112309_.translate(0.5D, 0.5D, 0.5D);
			p_112309_.scale(-1, -1, 1);
			p_112309_.translate(0, -1, 0);
			VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(ALTAR_TEXTURE));
			this.altarModel.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			p_112309_.popPose();

			p_112309_.pushPose();
			p_112309_.translate(0.5D, 0.5D, 0.5D);
			p_112309_.scale(-1, -1, 1);
			p_112309_.translate(0, -1, 0);
			VertexConsumer layerConsumer = p_112310_.getBuffer(RenderType.eyes(ALTAR_LAYER_TEXTURE));
			this.altarModel.renderToBuffer(p_112309_, layerConsumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			p_112309_.popPose();
			
			if(!altar.getItem().isEmpty())
			{
				float tick = BTAClientUtil.MC.player.tickCount + p_112308_;
				p_112309_.pushPose();
				p_112309_.scale(0.5F, 0.5F, 0.5F);
				p_112309_.translate(1.0F, 3.0F, 1.0F);
				p_112309_.translate(0, 0.05F * Mth.sin(tick / 15), 0);
				p_112309_.mulPose(Vector3f.YP.rotationDegrees(tick));
				BTAClientUtil.MC.getItemRenderer().renderStatic(altar.getItem(), ItemTransforms.TransformType.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, p_112309_, p_112310_, 0);
				p_112309_.popPose();
			}
		}
	}
}
