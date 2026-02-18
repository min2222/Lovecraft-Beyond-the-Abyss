package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.event.ClientEventHandlerForge;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RiderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	public RiderLayer(RenderLayerParent<T, M> pRenderer) 
	{
		super(pRenderer);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
	{
		if(entity.getFirstPassenger() != null)
		{
			Entity passenger = entity.getFirstPassenger();
			boolean flag = passenger instanceof Player ? !BTAClientUtil.MC.options.getCameraType().isFirstPerson() : true;
			if(flag && entity.isAlive())
			{
				poseStack.pushPose();
				EntityRenderer<? super Entity> entityRenderer = BTAClientUtil.MC.getEntityRenderDispatcher().getRenderer(passenger);
				ClientEventHandlerForge.RENDERER_LIST.remove(passenger.getUUID());
				this.transform(poseStack);
	            poseStack.mulPose(Axis.XN.rotationDegrees(180.0F));
	            poseStack.mulPose(Axis.YN.rotationDegrees(360.0F - Mth.lerp(partialTicks, entity.yBodyRotO, entity.yBodyRot)));
				entityRenderer.render(passenger, 0, partialTicks, poseStack, bufferIn, packedLightIn);
				ClientEventHandlerForge.RENDERER_LIST.add(passenger.getUUID());
				poseStack.popPose();
			}
		}
	}
	
	public void transform(PoseStack stack)
	{
		
	}
}
