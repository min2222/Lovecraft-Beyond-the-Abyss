package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.ModelToothBullet;
import com.min01.beyondtheabyss.entity.projectile.EntityToothBullet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ToothBulletRenderer extends EntityRenderer<EntityToothBullet>
{
	public final ModelToothBullet model;
	public ToothBulletRenderer(Context p_174008_) 
	{
		super(p_174008_);
		this.model = new ModelToothBullet(p_174008_.bakeLayer(ModelToothBullet.LAYER_LOCATION));
	}

	@Override
	public void render(EntityToothBullet p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		p_114488_.pushPose();
		p_114488_.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(p_114487_, p_114485_.yRotO, p_114485_.getYRot())));
		p_114488_.mulPose(Axis.XP.rotationDegrees(Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot())));
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0.0F, -1.5F, 0.0F);
		this.model.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		p_114488_.popPose();
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityToothBullet p_114482_)
	{
		if(p_114482_.isGolden())
		{
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/golden_tooth_bullet.png");
		}
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/tooth_bullet.png");
	}
}