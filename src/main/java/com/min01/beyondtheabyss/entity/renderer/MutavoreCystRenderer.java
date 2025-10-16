package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.model.ModelCystShrapnel;
import com.min01.beyondtheabyss.entity.model.ModelMutavoreCyst;
import com.min01.beyondtheabyss.entity.projectile.EntityMutavoreCyst;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MutavoreCystRenderer extends EntityRenderer<EntityMutavoreCyst>
{
	public final ModelMutavoreCyst model;
	public final ModelCystShrapnel shrapnelModel;
	
	public MutavoreCystRenderer(Context p_174008_) 
	{
		super(p_174008_);
		this.model = new ModelMutavoreCyst(p_174008_.bakeLayer(ModelMutavoreCyst.LAYER_LOCATION));
		this.shrapnelModel = new ModelCystShrapnel(p_174008_.bakeLayer(ModelCystShrapnel.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityMutavoreCyst p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		if(p_114485_.isShrapnel())
		{
			p_114488_.pushPose();
			p_114488_.mulPose(Axis.YP.rotationDegrees(Mth.rotLerp(p_114487_, p_114485_.yRotO, p_114485_.getYRot())));
			p_114488_.mulPose(Axis.XP.rotationDegrees(Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot())));
			p_114488_.scale(-1.0F, -1.0F, 1.0F);
			p_114488_.scale(1.25F, 1.25F, 1.25F);
			p_114488_.translate(0.0F, -1.5F, 0.0F);
			this.shrapnelModel.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/cyst_shrapnel.png"))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			p_114488_.popPose();
		}
		else
		{
			p_114488_.pushPose();
			p_114488_.scale(-1.0F, -1.0F, 1.0F);
			p_114488_.translate(0.0F, -1.5F, 0.0F);
			this.model.setupAnim(p_114485_, 0, 0, 0, 0, 0);
			this.model.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			p_114488_.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMutavoreCyst p_114482_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
