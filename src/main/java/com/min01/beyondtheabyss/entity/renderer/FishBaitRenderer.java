package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFishBait;
import com.min01.beyondtheabyss.entity.model.ModelFishBait;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FishBaitRenderer extends EntityRenderer<EntityFishBait>
{
	public final ModelFishBait model;
	public FishBaitRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.model = new ModelFishBait(p_174008_.bakeLayer(ModelFishBait.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityFishBait p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_)
	{
		p_114488_.pushPose();
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		this.model.setupAnim(p_114485_, 0, 0, 0, 0, 0);
		this.model.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(p_114485_))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		p_114488_.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityFishBait p_114482_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/fish_bait.png");
	}
}
