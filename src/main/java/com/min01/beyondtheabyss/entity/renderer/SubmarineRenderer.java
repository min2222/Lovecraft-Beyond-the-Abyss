package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IMultiModel;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.model.ModelSubmarine;
import com.min01.beyondtheabyss.event.ClientEventHandlerForge;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class SubmarineRenderer extends EntityRenderer<EntitySubmarine> implements IMultiModel<EntitySubmarine>
{
	public final ModelSubmarine model;
	
	public SubmarineRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.model = new ModelSubmarine(p_174008_.bakeLayer(ModelSubmarine.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntitySubmarine p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		p_114488_.pushPose();
		float yRot = Mth.rotLerp(p_114487_, p_114485_.yRotO, p_114485_.getYRot());
		float xRot = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0.0F, -1.5F, 0.0F);
		if(p_114485_.getFirstPassenger() != null)
		{
			boolean flag = p_114485_.getFirstPassenger() instanceof Player ? !BTAClientUtil.MC.options.getCameraType().isFirstPerson() : true;
			if(flag && p_114485_.isAlive())
			{
				p_114488_.pushPose();
				EntityRenderer<? super Entity> entityRenderer = BTAClientUtil.MC.getEntityRenderDispatcher().getRenderer(p_114485_.getFirstPassenger());
				ClientEventHandlerForge.RENDERER_LIST.remove(p_114485_.getFirstPassenger().getUUID());
				this.transform(p_114488_);
				p_114488_.mulPose(Axis.XN.rotationDegrees(180.0F));
	            p_114488_.mulPose(Axis.YN.rotationDegrees(360.0F - Mth.lerp(p_114487_, p_114485_.yRotO, p_114485_.getYRot())));
				entityRenderer.render(p_114485_.getFirstPassenger(), 0, p_114487_, p_114488_, p_114489_, p_114490_);
				ClientEventHandlerForge.RENDERER_LIST.add(p_114485_.getFirstPassenger().getUUID());
				p_114488_.popPose();
			}
		}
		this.model.setupAnim(p_114485_, 0, 0, p_114485_.tickCount + p_114487_, yRot + 180.0F, xRot);
		this.model.renderToBuffer(p_114488_, p_114489_.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(p_114485_))), p_114490_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		p_114488_.popPose();
	}
	
	public void transform(PoseStack stack)
	{
		this.model.root.translateAndRotate(stack);
		this.model.submarine.translateAndRotate(stack);
		this.model.controller.translateAndRotate(stack);
		stack.translate(0.0F, 0.25F, 0.0F);
	}
	
	@Override
	public HierarchicalModel<EntitySubmarine> getModel(EntitySubmarine entity)
	{
		return this.model;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySubmarine p_114482_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/submarine.png");
	}
}
